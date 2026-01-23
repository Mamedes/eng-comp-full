import { useEffect, useState, useCallback } from 'react'
import { useAuth } from '@/context/auth-context'
import { getArtistsDashboard } from '@/lib/api'
import { ArtistaDashboard } from '@/lib/types'
import { Users, Music, Search, Loader2, LogOut, ChevronLeft, ChevronRight, ArrowUpDown } from 'lucide-react'

export default function HomePage() {
  const { token, logout } = useAuth()
  const [artistaDashboard, setArtistaDashboard] = useState<ArtistaDashboard[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(0)
  const [totalItems, setTotalItems] = useState(0)
  const [sortDir, setSortDir] = useState<'asc' | 'desc'>('desc')

  const perPage = 8

  const loadArtists = useCallback(async () => {
    try {
      setIsLoading(true)
      const data = await getArtistsDashboard({
        search: searchTerm,
        page: page,
        perPage: perPage,
        sort: 'nome',
        dir: sortDir
      }, token)

      setArtistaDashboard(data.items)
      setTotalItems(data.total)
    } catch (err) {
      console.error(err)
    } finally {
      setIsLoading(false)
    }
  }, [token, searchTerm, page, sortDir])

  useEffect(() => {
    const delayDebounce = setTimeout(() => {
      loadArtists()
    }, 400)
    return () => clearTimeout(delayDebounce)
  }, [loadArtists])

  const totalPages = Math.ceil(totalItems / perPage)

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100 selection:bg-purple-500/30">
      <nav className="sticky top-0 z-50 border-b border-white/5 bg-zinc-900/70 backdrop-blur-xl">
        <div className="container mx-auto px-6 py-4 flex justify-between items-center">
          <div className="flex items-center gap-2 group">
            <div className="bg-purple-600 p-1.5 rounded-lg group-hover:rotate-12 transition-transform">
              <Music className="h-5 w-5 text-white" />
            </div>
            <span className="font-bold text-xl tracking-tight bg-gradient-to-r from-white to-zinc-400 bg-clip-text text-transparent">
              MusicManager
            </span>
          </div>
          <button
            onClick={logout}
            className="group flex items-center gap-2 text-zinc-400 hover:text-red-400 transition-colors text-sm font-medium"
          >
            <LogOut className="h-4 w-4 group-hover:-translate-x-1 transition-transform" />
            Sair
          </button>
        </div>
      </nav>

      <main className="container mx-auto px-6 py-10">
        <div className="flex flex-col lg:flex-row lg:items-end justify-between gap-6 mb-12">
          <div className="space-y-1">
            <h1 className="text-4xl font-black tracking-tighter sm:text-5xl">Artistas</h1>
            <p className="text-zinc-500 font-medium">Gerencie sua biblioteca de talentos e álbuns.</p>
          </div>

          <div className="flex flex-col sm:flex-row gap-3 w-full lg:w-auto">
            <div className="relative flex-1 sm:w-80">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
              <input
                type="text"
                placeholder="Buscar artista..."
                className="w-full bg-zinc-900 border border-white/10 rounded-xl py-2.5 pl-10 pr-4 focus:ring-2 focus:ring-purple-500/20 focus:border-purple-500/50 outline-none transition-all placeholder:text-zinc-600"
                onChange={(e) => { setSearchTerm(e.target.value); setPage(0); }}
              />
            </div>

            <button
              onClick={() => setSortDir(prev => prev === 'asc' ? 'desc' : 'asc')}
              className="flex items-center justify-center gap-2 bg-zinc-900 border border-white/10 px-4 py-2.5 rounded-xl hover:bg-zinc-800 transition-all text-sm font-medium"
            >
              <ArrowUpDown className="h-4 w-4" />
              {sortDir === 'asc' ? 'A-Z' : 'Z-A'}
            </button>
          </div>
        </div>

        {isLoading ? (
          <div className="flex flex-col items-center justify-center py-32 gap-4">
            <Loader2 className="h-10 w-10 animate-spin text-purple-500" />
            <p className="text-zinc-500 animate-pulse font-medium">Carregando dashboard...</p>
          </div>
        ) : (
          <>
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6">
              {artistaDashboard.map((artista) => (
                <ArtistCard key={artista.artistaId} artista={artista} />
              ))}
            </div>

            <div className="mt-12 flex flex-col sm:flex-row items-center justify-between gap-4 border-t border-white/5 pt-8">
              <span className="text-sm text-zinc-500">
                Mostrando <span className="text-zinc-200 font-bold">{artistaDashboard.length}</span> de <span className="text-zinc-200 font-bold">{totalItems}</span> artistas
              </span>

              <div className="flex items-center gap-2">
                <button
                  disabled={page === 0}
                  onClick={() => setPage(p => p - 1)}
                  className="p-2.5 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-20 hover:bg-zinc-800 transition-all"
                >
                  <ChevronLeft className="h-5 w-5" />
                </button>

                <div className="flex gap-1">
                  <span className="px-4 py-2 rounded-xl bg-purple-600/10 border border-purple-500/20 text-purple-400 font-bold text-sm">
                    {page + 1}
                  </span>
                </div>

                <button
                  disabled={page >= totalPages - 1}
                  onClick={() => setPage(p => p + 1)}
                  className="p-2.5 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-20 hover:bg-zinc-800 transition-all"
                >
                  <ChevronRight className="h-5 w-5" />
                </button>
              </div>
            </div>
          </>
        )}
      </main>
    </div>
  )
}

function ArtistCard({ artista }: { artista: ArtistaDashboard }) {
  return (
    <div className="group relative bg-zinc-900/40 border border-white/5 rounded-3xl p-6 hover:bg-zinc-900/80 transition-all hover:border-purple-500/30 hover:-translate-y-1 shadow-2xl shadow-black/50">
      <div className="flex items-start justify-between mb-6">
        <div className="h-14 w-14 rounded-2xl bg-gradient-to-br from-purple-500/20 to-blue-500/20 border border-white/5 flex items-center justify-center group-hover:scale-110 transition-transform duration-500">
          <Users className="h-7 w-7 text-purple-400" />
        </div>
        <span className="text-[10px] uppercase tracking-widest bg-zinc-800 text-zinc-400 px-2.5 py-1 rounded-full font-bold border border-white/5">
          {artista.tipo}
        </span>
      </div>

      <h3 className="text-xl font-bold text-white mb-2 truncate group-hover:text-purple-400 transition-colors">
        {artista.nome}
      </h3>

      <div className="flex items-center gap-2 text-zinc-500 text-sm mb-6">
        <div className="flex -space-x-2">
          <Music className="h-4 w-4 text-zinc-400" />
        </div>
        <span>{artista.quantidadeAlbuns} {artista.quantidadeAlbuns === 1 ? 'Álbum' : 'Álbuns'}</span>
      </div>

      <button className="w-full py-3 rounded-2xl bg-zinc-800 border border-white/5 text-sm font-bold hover:bg-white hover:text-black transition-all active:scale-95">
        Gerenciar Artista
      </button>
    </div>
  )
}