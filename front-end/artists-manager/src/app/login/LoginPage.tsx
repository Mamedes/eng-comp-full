import React, { useEffect, useState } from "react";
import { Music2, Loader2, Eye, EyeOff, User, Lock } from 'lucide-react';
import { useAuth } from '@/context/auth-context';
import { useNavigate } from 'react-router-dom';

export default function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);

  const { login, isAuthenticated } = useAuth(); 
  const navigate = useNavigate();


  useEffect(() => {
    console.log("Estado de Autenticação mudou:", isAuthenticated);
    if (isAuthenticated) {
      navigate('/', { replace: true });
    }
  }, [isAuthenticated, navigate]);


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);
    try {
      await login({ username, password });

      console.log("Login realizado, tentando navegar...");
      navigate('/', { replace: true });

    } catch (err: any) {
      setError('Falha ao entrar. Verifique suas credenciais.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex min-h-screen w-full items-center justify-center bg-zinc-950 px-4">

      <div className="w-full max-w-[400px] space-y-8">
        <div className="rounded-3xl border border-white/10 bg-zinc-900/50 p-8 shadow-2xl backdrop-blur-md">

          <div className="flex flex-col items-center mb-8">
            <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-white">
              <Music2 className="h-7 w-7 text-black" />
            </div>
            <h1 className="text-2xl font-bold text-white tracking-tight">Music Manager</h1>
            <p className="text-zinc-400 text-sm">Entre com suas credenciais</p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            <div className="space-y-2">
              <label className="text-xs uppercase tracking-widest font-semibold text-zinc-500 ml-1">Usuário</label>
              <div className="relative">
                <User className="absolute left-3 top-3 h-4 w-4 text-zinc-500" />
                <input
                  type="text"
                  className="w-full rounded-xl border border-white/5 bg-white/5 py-2.5 pl-10 pr-4 text-white focus:outline-none focus:ring-2 focus:ring-white/20 transition-all"
                  placeholder="Seu usuário"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
                />
              </div>
            </div>

            <div className="space-y-2">
              <label className="text-xs uppercase tracking-widest font-semibold text-zinc-500 ml-1">Senha</label>
              <div className="relative">
                <Lock className="absolute left-3 top-3 h-4 w-4 text-zinc-500" />
                <input
                  type={showPassword ? "text" : "password"}
                  className="w-full rounded-xl border border-white/5 bg-white/5 py-2.5 pl-10 pr-12 text-white focus:outline-none focus:ring-2 focus:ring-white/20 transition-all"
                  placeholder="••••••••"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-3 text-zinc-500 hover:text-white"
                >
                  {showPassword ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                </button>
              </div>
            </div>

            {error && <p className="text-red-400 text-xs text-center font-medium bg-red-400/10 py-2 rounded-lg border border-red-400/20">{error}</p>}

            <button
              type="submit"
              disabled={isLoading}
              className="w-full rounded-xl bg-white py-3 text-sm font-bold text-black hover:bg-zinc-200 transition-all active:scale-[0.98] disabled:opacity-50"
            >
              {isLoading ? <Loader2 className="mx-auto h-5 w-5 animate-spin" /> : 'Acessar Painel'}
            </button>
          </form>
        </div>

        <p className="text-center text-[10px] text-zinc-600 uppercase tracking-widest font-medium">
          Sistema de Gerenciamento de Artistas © 2026
        </p>
      </div>
    </div>
  );
}