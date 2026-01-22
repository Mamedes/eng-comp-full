import React, { createContext, useContext, useState, useEffect, useCallback, useRef } from 'react'
import { API_BASE_URL, API_ENDPOINTS, getApiUrl } from '@/lib/api-config'
import type { AuthResponse, LoginCredentials } from '@/lib/types'

interface AuthContextType {
  token: string | null
  isAuthenticated: boolean
  isLoading: boolean
  login: (credentials: LoginCredentials) => Promise<void>
  logout: () => void
  getAuthHeader: () => { Authorization: string } | Record<string, never>
}

const AuthContext = createContext<AuthContextType | undefined>(undefined)

const TOKEN_KEY = 'auth_token'
const TOKEN_EXPIRY_KEY = 'auth_token_expiry'
const REFRESH_BUFFER = 60 * 1000 

export function AuthProvider({ children }: { children: React.ReactNode }) {
  const [token, setToken] = useState<string | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const refreshTimeoutRef = useRef<ReturnType<typeof setTimeout> | null>(null)


  const clearAuthData = useCallback(() => {
    localStorage.removeItem(TOKEN_KEY)
    localStorage.removeItem(TOKEN_EXPIRY_KEY)
    setToken(null)
    if (refreshTimeoutRef.current) {
      clearTimeout(refreshTimeoutRef.current)
      refreshTimeoutRef.current = null
    }
  }, [])

  const scheduleTokenRefresh = useCallback((expiresInMs: number) => {
    if (refreshTimeoutRef.current) {
      clearTimeout(refreshTimeoutRef.current)
    }

    const refreshTime = Math.max(expiresInMs - REFRESH_BUFFER, 0)

    refreshTimeoutRef.current = setTimeout(async () => {
      try {
        const currentToken = localStorage.getItem(TOKEN_KEY)
        if (!currentToken) return

        const response = await fetch(getApiUrl(API_ENDPOINTS.refresh), {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${currentToken}`,
          },
        })

        if (response.ok) {
          const data: AuthResponse = await response.json()
          const newExpiresIn = data.expiresIn || 5 * 60 * 1000 // Default 5 minutes
          const expiryTime = Date.now() + newExpiresIn
          const tokenParaSalvar = data.accessToken

          if (!tokenParaSalvar) {
            console.error("Erro: O backend não retornou accessToken", data);
            throw new Error("Token não recebido");
          }

          const expiresIn = data.expiresIn || 5 * 60 * 1000

          // Salva o valor correto
          localStorage.setItem(TOKEN_KEY, tokenParaSalvar)
          localStorage.setItem(TOKEN_EXPIRY_KEY, expiryTime.toString())
          setToken(tokenParaSalvar)
          setIsLoading(false)
          scheduleTokenRefresh(newExpiresIn)



        } else {
          clearAuthData()
        }
      } catch (error) {
        console.error('[v0] Token refresh failed:', error)
        clearAuthData()
      }
    }, refreshTime)
  }, [clearAuthData])

  useEffect(() => {
    const storedToken = localStorage.getItem(TOKEN_KEY)
    const storedExpiry = localStorage.getItem(TOKEN_EXPIRY_KEY)

    if (storedToken && storedExpiry) {
      const expiryTime = parseInt(storedExpiry, 10)
      const now = Date.now()

      if (expiryTime > now) {
        setToken(storedToken)
        scheduleTokenRefresh(expiryTime - now)
      } else {
        clearAuthData()
      }
    }

    setIsLoading(false)
  }, [scheduleTokenRefresh, clearAuthData])

  const login = useCallback(async (credentials: LoginCredentials) => {
    const response = await fetch(getApiUrl(API_ENDPOINTS.login), {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(credentials),
    })

    if (!response.ok) {
      const error = await response.text()
      throw new Error(error || 'Login failed')
    }

    const data = await response.json()

    const tokenRecebido = data.accessToken

    if (!tokenRecebido) {
      console.error("Token não encontrado na resposta!", data)
      return
    }

    const expiresIn = 5 * 60 * 1000 // 5 minutos padrão
    const expiryTime = Date.now() + expiresIn

    localStorage.setItem(TOKEN_KEY, tokenRecebido)
    localStorage.setItem(TOKEN_EXPIRY_KEY, expiryTime.toString())

    setToken(tokenRecebido)
    setIsLoading(false)

    console.log("Contexto: Token atualizado com sucesso!")
  }, [scheduleTokenRefresh])

  const logout = useCallback(() => {
    clearAuthData()
  }, [clearAuthData])

  const getAuthHeader = useCallback(() => {
    return token ? { Authorization: `Bearer ${token}` } : {}
  }, [token])

  return (
    <AuthContext.Provider
      value={{
        token,
        isAuthenticated: !!token,
        isLoading,
        login,
        logout,
        getAuthHeader,
      }}
    >
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const context = useContext(AuthContext)
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider')
  }
  return context
}
