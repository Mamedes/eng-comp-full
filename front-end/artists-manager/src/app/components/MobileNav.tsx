import { Link, useLocation } from 'react-router-dom'
import { NAV_ITEMS } from '@/config/navigation'
import { LogOut } from 'lucide-react'
import { useAuth } from '@/context/auth-context'

export function MobileNav() {
    const { logout } = useAuth()
    const location = useLocation()

    return (
        <nav className="lg:hidden fixed bottom-0 left-0 right-0 z-50 bg-zinc-900/80 backdrop-blur-xl border-t border-white/5 px-6 py-3">
            <div className="flex items-center justify-between max-w-md mx-auto">
                {NAV_ITEMS.map((item) => {
                    const isActive = location.pathname === item.href
                    return (
                        <Link
                            key={item.href}
                            to={item.href}
                            className={`flex flex-col items-center gap-1 transition-colors ${isActive ? 'text-purple-400' : 'text-zinc-500 hover:text-zinc-300'
                                }`}
                        >
                            <item.icon size={22} strokeWidth={isActive ? 2.5 : 2} />
                            <span className="text-[10px] font-bold uppercase tracking-tighter">
                                {item.label}
                            </span>
                        </Link>
                    )
                })}

                <button
                    onClick={logout}
                    className="flex flex-col items-center gap-1 text-zinc-500 hover:text-red-400 transition-colors"
                >
                    <LogOut size={22} />
                    <span className="text-[10px] font-bold uppercase tracking-tighter">Sair</span>
                </button>
            </div>
        </nav>
    )
}