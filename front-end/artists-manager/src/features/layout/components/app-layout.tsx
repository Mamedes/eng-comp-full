import { Sidebar } from './sidebar';
import { MobileNav } from './mobile-nav';
import { Outlet } from 'react-router-dom';

export function AppLayout() {
    return (
        <div className="min-h-screen bg-zinc-950 flex flex-col lg:flex-row">
            <Sidebar />
            <MobileNav />

            <main className="flex-1 lg:pl-64 pb-20 lg:pb-0">
                <Outlet />
            </main>
        </div>
    );
}