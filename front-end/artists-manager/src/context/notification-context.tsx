import React, { createContext, useContext, useState, useEffect, useCallback, useRef } from 'react'
import { API_BASE_URL } from '@/lib/api-config'
import { useAuth } from './auth-context'

export interface Notification {
  id: string
  type: 'album_created' | 'artist_created' | 'info'
  message: string
  timestamp: Date
}

interface NotificationContextType {
  notifications: Notification[]
  unreadCount: number
  isConnected: boolean
  markAsRead: (id: string) => void
  clearAll: () => void
}

const NotificationContext = createContext<NotificationContextType | undefined>(undefined)

export function NotificationProvider({ children }: { children: React.ReactNode }) {
  const [notifications, setNotifications] = useState<Notification[]>([])
  const [isConnected, setIsConnected] = useState(false)
  const wsRef = useRef<WebSocket | null>(null)

  const { token, isAuthenticated, isLoading } = useAuth()

  const addNotification = useCallback((notification: Omit<Notification, 'id' | 'timestamp'>) => {
    const newNotification: Notification = {
      ...notification,
      id: crypto.randomUUID(),
      timestamp: new Date(),
    }
    setNotifications((prev) => [newNotification, ...prev].slice(0, 50))
  }, [])

  useEffect(() => {
    if (isLoading || !isAuthenticated || !token) {
      if (wsRef.current) {
        wsRef.current.close()
        wsRef.current = null
      }
      setIsConnected(false)
      return
    }

    const wsProtocol = API_BASE_URL.startsWith('https') ? 'wss' : 'ws'
    const wsHost = API_BASE_URL.replace(/^https?:\/\//, '')
    const wsUrl = `${wsProtocol}://${wsHost}/ws?token=${token}`

    try {
      const ws = new WebSocket(wsUrl)
      wsRef.current = ws

      ws.onopen = () => {
        console.log('[System] WebSocket conectado')
        setIsConnected(true)
      }

      ws.onmessage = (event) => {
        try {
          const data = JSON.parse(event.data)
          if (data.type === 'album_created') {
            addNotification({
              type: 'album_created',
              message: `Novo álbum "${data.albumName}" de ${data.artistName}`,
            })
          } else if (data.type === 'artist_created') {
            addNotification({
              type: 'artist_created',
              message: `Novo artista "${data.artistName}" cadastrado`,
            })
          }
        } catch (e) {
          console.error('Erro ao ler mensagem WS:', e)
        }
      }

      ws.onerror = () => setIsConnected(false)
      ws.onclose = () => setIsConnected(false)

      return () => ws.close()
    } catch (error) {
      console.error('Erro na conexão WS:', error)
      setIsConnected(false)
    }
  }, [isAuthenticated, token, isLoading, addNotification])

  const markAsRead = useCallback((id: string) => {
    setNotifications((prev) => prev.filter((n) => n.id !== id))
  }, [])

  const clearAll = useCallback(() => setNotifications([]), [])

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        unreadCount: notifications.length,
        isConnected,
        markAsRead,
        clearAll,
      }}
    >
      {children}
    </NotificationContext.Provider>
  )
}

export function useNotifications() {
  const context = useContext(NotificationContext)
  if (context === undefined) {
    throw new Error('useNotifications deve ser usado dentro de um NotificationProvider')
  }
  return context
}