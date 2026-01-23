import { Link, useLocation } from 'react-router-dom'
import { NAV_ITEMS } from '@/config/navigation'
import { Music, LogOut } from 'lucide-react'
import { useAuth } from '@/context/auth-context'

export function Sidebar() {
    const { logout } = useAuth()
    const location = useLocation()

    return (
        <aside className="fixed left-0 top-0 h-screen w-64 bg-zinc-950 border-r border-white/5 flex flex-col hidden lg:flex">
            <div className="p-8 flex items-center gap-3">
                <div className="bg-purple-600 p-2 rounded-xl">
                    <Music className="text-white h-6 w-6" />
                </div>
                <span className="text-white font-bold text-xl tracking-tight">MusicManager</span>
            </div>

            <nav className="flex-1 px-4 space-y-2">
                {NAV_ITEMS.map((item) => {
                    const isActive = location.pathname === item.href
                    return (
                        <Link
                            key={item.href}
                            to={item.href}
                            className={`flex items-center gap-3 px-4 py-3 rounded-xl font-medium transition-all ${isActive
                                    ? 'bg-purple-600/10 text-purple-400 border border-purple-500/20'
                                    : 'text-zinc-500 hover:text-white hover:bg-white/5'
                                }`}
                        >
                            <item.icon size={20} />
                            {item.label}
                        </Link>
                    )
                })}
            </nav>

            <div className="p-4 border-t border-white/5">
                <button
                    onClick={logout}
                    className="w-full flex items-center gap-3 px-4 py-3 text-zinc-500 hover:text-red-400 transition-colors"
                >
                    <LogOut size={20} />
                    Sair da Conta
                </button>
            </div>
        </aside>
    )
}