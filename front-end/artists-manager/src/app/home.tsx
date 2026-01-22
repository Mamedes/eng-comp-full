import { useEffect, useState } from 'react'
import { useAuth } from '@/context/auth-context'
import { getArtists } from '@/lib/api' 
import { Artist } from '@/lib/types'
import { Users, Music, Search, Loader2, LogOut } from 'lucide-react'

export default function HomePage() {
  const { token, logout } = useAuth()
  const [artists, setArtists] = useState<Artist[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    async function loadArtists() {
      try {
        setIsLoading(true)
        const data = await getArtists({
          page: 0,
          perPage: 30,
          sort: 'nome',
          dir: 'desc'
        }, token)

        setArtists(data.items)
      } catch (err) {
        setError('Não foi possível carregar os artistas.')
      } finally {
        setIsLoading(false)
      }
    }

    if (token) loadArtists()
  }, [token])

  return (
    <div className="min-h-screen bg-zinc-950 text-white">
      <nav className="border-b border-white/5 bg-zinc-900/50 backdrop-blur-md sticky top-0 z-50">
        <div className="container mx-auto px-6 py-4 flex justify-between items-center">
          <div className="flex items-center gap-2">
            <div className="bg-white p-1.5 rounded-lg">
              <Music className="h-5 w-5 text-black" />
            </div>
            <span className="font-bold text-xl tracking-tight">MusicManager</span>
          </div>
          <button
            onClick={logout}
            className="flex items-center gap-2 text-zinc-400 hover:text-white transition-colors text-sm font-medium"
          >
            <LogOut className="h-4 w-4" /> Sair
          </button>
        </div>
      </nav>

      <main className="container mx-auto px-6 py-10">
        <div className="flex flex-col md:flex-row md:items-center justify-between gap-6 mb-12">
          <div>
            <h1 className="text-4xl font-extrabold tracking-tight mb-2">Artistas</h1>
            <p className="text-zinc-400">Explore e gerencie sua biblioteca de talentos.</p>
          </div>

          <div className="relative w-full md:w-96">
            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
            <input
              type="text"
              placeholder="Buscar artista..."
              className="w-full bg-zinc-900 border border-white/10 rounded-xl py-2.5 pl-10 pr-4 focus:ring-2 focus:ring-purple-500/20 focus:border-purple-500/50 outline-none transition-all"
            />
          </div>
        </div>

        {isLoading ? (
          <div className="flex flex-col items-center justify-center py-20 gap-4">
            <Loader2 className="h-8 w-8 animate-spin text-purple-500" />
            <p className="text-zinc-500 animate-pulse">Sincronizando biblioteca...</p>
          </div>
        ) : error ? (
          <div className="bg-red-500/10 border border-red-500/20 p-6 rounded-2xl text-center">
            <p className="text-red-400">{error}</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {artists.map((artist) => (
              <div
                key={artist.secure_id}
                className="group relative bg-zinc-900/40 border border-white/5 rounded-2xl p-5 hover:bg-zinc-800/60 transition-all hover:border-white/20 hover:-translate-y-1"
              >
                <div className="flex items-start justify-between mb-4">
                  <div className="h-14 w-14 rounded-2xl bg-gradient-to-br from-purple-600 to-blue-600 flex items-center justify-center shadow-lg">
                    <Users className="h-7 w-7 text-white" />
                  </div>
                  <span className="text-[10px] uppercase tracking-widest bg-white/5 px-2 py-1 rounded-md text-zinc-400 font-bold">
                    {artist.tipo}
                  </span>
                </div>

                <h3 className="text-lg font-bold text-white mb-1 group-hover:text-purple-400 transition-colors">
                  {artist.nome}
                </h3>
                <p className="text-zinc-500 text-sm">ID: {artist.secure_id.substring(0, 8)}...</p>

                <button className="mt-6 w-full py-2 rounded-xl bg-white/5 border border-white/10 text-sm font-semibold hover:bg-white hover:text-black transition-all">
                  Ver Detalhes
                </button>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  )
}