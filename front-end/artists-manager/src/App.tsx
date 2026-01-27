import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { authFacade } from '@/core/auth/auth.facade';
import { useObservable } from '@/shared/hooks/use-observable';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import LoginPage from './features/auth/pages/login-page';
import { FullScreenLoader } from './shared/components/full-screan-loader';
import ArtistPage from './features/artistas/page/artista-page';
import { initialAuthState } from './core/auth/auth.store';
import { Loader2 } from 'lucide-react';
import { AppLayout } from './features/layout/components/app-layout';
import AlbumsPage from './features/albums/pages/album-page';
import { initNotificationSocket, notification$ } from './core/services/notifacation.service';
import { useEffect } from 'react';
import StatusPage from './features/heath/page/status-page';

function PrivateRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated, isAppLoading } = useObservable(authFacade.state$, {
    token: null,
    isAuthenticated: false,
    isAuthLoading: false,
    isAppLoading: true,
  });

  if (isAppLoading) {
    return (
      <div className="h-screen w-screen bg-zinc-950 flex items-center justify-center">
        <Loader2 className="animate-spin text-white h-8 w-8" />
      </div>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return <>{children}</>;
}

function PublicRoute({ children }: { children: React.ReactNode }) {
  const auth = useObservable(authFacade.state$, initialAuthState);

  if (auth.isAuthenticated) {
    return <Navigate to="/app" replace />;
  }

  return <>{children}</>;
}

export default function App() {
  const auth = useObservable(authFacade.state$, initialAuthState);


  useEffect(() => {
    if (auth.isAuthenticated && auth.token) {

      const disconnect = initNotificationSocket(auth.token);

      const subscription = notification$.subscribe((message) => {
        toast.success(message, {
          theme: "dark"
        });
      });

      return () => {
        disconnect();
        subscription.unsubscribe();
      };
    }
  }, [auth.isAuthenticated, auth.token]);

  if (auth.isAppLoading) {
    return <FullScreenLoader />;
  }

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/app" replace />} />

        <Route
          path="/login"
          element={
            <PublicRoute>
              <LoginPage />
            </PublicRoute>
          }
        />

        <Route
          element={
            <PrivateRoute>
              <AppLayout />
            </PrivateRoute>
          }
        >
          <Route path="app" element={<ArtistPage />} />
          <Route path="albums" element={<AlbumsPage />} />
          <Route path="status" element={<StatusPage />} />
        </Route>

        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>

      <ToastContainer theme="dark" position="top-right" />
    </BrowserRouter>
  );
}