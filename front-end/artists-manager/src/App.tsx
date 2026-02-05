import { authFacade } from '@/core/auth/auth.facade';
import { useObservable } from '@/shared/hooks/use-observable';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { useEffect } from 'react';
import { authState$, initialAuthState } from './core/auth/auth.store';
import { initNotificationSocket, notification$ } from './core/services/notifacation.service';
import AlbumsPage from './features/albums/pages/album-page';
import ArtistDetailPage from './features/artistas/page/artista-detalhe-page';
import ArtistPage from './features/artistas/page/artista-page';
import LoginPage from './features/auth/pages/login-page';
import StatusPage from './features/heath/page/status-page';
import { AppLayout } from './features/layout/components/app-layout';
import { FullScreenLoader } from './shared/components/full-screan-loader';

function PrivateRoute({ children }: { children: React.ReactNode }) {
  const { isAuthenticated, isAppLoading } = useObservable(authFacade.state$, authState$.value);

  if (isAppLoading) {
    return <FullScreenLoader />;
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
          <Route path="app/:id" element={<ArtistDetailPage />} />
        </Route>

        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>

      <ToastContainer theme="dark" position="top-right" />
    </BrowserRouter>
  );
}