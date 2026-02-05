import { Link, useLocation, useNavigate } from 'react-router-dom';
import { LogOut } from 'lucide-react';
import { authFacade } from '@/core/auth/auth.facade';
import { NAV_ITEMS } from './navigation.config';

export function MobileNav() {
    const location = useLocation();
    const navigate = useNavigate();

    const handleLogout = () => {
        try {
            authFacade.logout();
            navigate('/login', { replace: true });
        } catch (error) {
            console.error("Erro ao deslogar:", error);
            window.location.href = '/login';
        }
    };
    return (
        <nav className="lg:hidden fixed bottom-0 left-0 right-0 z-50 bg-zinc-900/80 backdrop-blur-xl border-t border-white/5 px-6 py-3">
            <div className="flex items-center justify-between max-w-md mx-auto">
                {NAV_ITEMS.map((item) => {
                    const isActive = location.pathname === item.href;
                    return (
                        <Link
                            key={item.label}
                            to={item.href}
                            className={`flex flex-col items-center gap-1 transition-colors ${isActive ? 'text-white' : 'text-zinc-500 hover:text-zinc-300'
                                }`}
                        >
                            <item.icon size={22} strokeWidth={isActive ? 2.5 : 2} />
                            <span className="text-[10px] font-bold uppercase tracking-tighter">
                                {item.label}
                            </span>
                        </Link>
                    );
                })}

                <button
                    onClick={handleLogout}
                    className="flex flex-col items-center gap-1 text-zinc-500 hover:text-red-400 transition-colors"
                >
                    <LogOut size={22} />
                    <span className="text-[10px] font-bold uppercase tracking-tighter">Sair</span>
                </button>
            </div>
        </nav>
    );
}