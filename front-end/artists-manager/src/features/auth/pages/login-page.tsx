import React, { useEffect, useState } from "react";
import { Music2, Loader2, Eye, EyeOff, User, Lock } from 'lucide-react';
import { authFacade } from '@/core/auth/auth.facade';
import { useObservable } from '@/shared/hooks/use-observable';
import { toast } from 'react-toastify';
import { AuthError } from '@/core/auth/auth.error';
import { useNavigate } from 'react-router-dom';
import { initialAuthState } from '@/core/auth/auth.store';

export default function LoginPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', password: '' });
  const [showPassword, setShowPassword] = useState(false);
  const { isAuthLoading } = useObservable(authFacade.state$, {
    token: null,
    isAuthenticated: false,
    isAuthLoading: false,
    isAppLoading: false,
  });


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await authFacade.login(form);
      toast.success('Bem-vindo!');
      navigate('/app', { replace: true });
    } catch (error) {
      if (error instanceof AuthError) {
        toast.error(error.message);
      } else {
        toast.error('Erro inesperado.');
      }
    }
  };
  return (
    <div className="flex min-h-screen items-center justify-center bg-zinc-950 px-4">
      <div className="w-full max-w-[400px] space-y-8">
        <div className="rounded-3xl border border-white/10 bg-zinc-900/50 p-8 backdrop-blur-md">
          <div className="flex flex-col items-center mb-8">
            <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-xl bg-white">
              <Music2 className="h-7 w-7 text-black" />
            </div>
            <h1 className="text-2xl font-bold text-white">Music Manager</h1>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            {/* Input de Usuário */}
            <div className="space-y-2">
              <label className="text-xs font-semibold text-zinc-500 uppercase ml-1">Usuário</label>
              <div className="relative">
                <User className="absolute left-3 top-3 h-4 w-4 text-zinc-500" />
                <input
                  type="text"
                  required
                  className="w-full rounded-xl border border-white/5 bg-white/5 py-2.5 pl-10 text-white focus:ring-2 focus:ring-white/20 outline-none transition-all"
                  value={form.username}
                  onChange={e => setForm({ ...form, username: e.target.value })}
                />
              </div>
            </div>

            {/* Input de Senha */}
            <div className="space-y-2">
              <label className="text-xs font-semibold text-zinc-500 uppercase ml-1">Senha</label>
              <div className="relative">
                <Lock className="absolute left-3 top-3 h-4 w-4 text-zinc-500" />
                <input
                  type={showPassword ? "text" : "password"}
                  required
                  className="w-full rounded-xl border border-white/5 bg-white/5 py-2.5 pl-10 text-white focus:ring-2 focus:ring-white/20 outline-none"
                  value={form.password}
                  onChange={e => setForm({ ...form, password: e.target.value })}
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3 top-3 text-zinc-500 hover:text-white"
                >
                  {showPassword ? <EyeOff size={16} /> : <Eye size={16} />}
                </button>
              </div>
            </div>
            <button
              type="submit"
              disabled={isAuthLoading}
              className="w-full rounded-xl bg-white py-3 text-sm font-bold text-black hover:bg-zinc-200 disabled:opacity-50 transition-all active:scale-[0.98]"
            >
              {isAuthLoading ? <Loader2 /> : 'Acessar Painel'}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}