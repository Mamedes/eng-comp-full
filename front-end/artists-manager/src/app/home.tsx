import { useEffect, useState, useCallback } from 'react'
import { useAuth } from '@/context/auth-context'
import { createArtist, deleteArtist, getArtistsDashboard, updateArtist } from '@/lib/api'
import { Artist, ArtistaDashboard, ArtistFormData, ArtistType } from '@/lib/types'
import {
  Users, Music, Search, Loader2, LogOut,
  ChevronLeft, ChevronRight, ArrowUpDown,
  Plus, Edit3, Trash2, X
} from 'lucide-react'
import { toast } from 'react-toastify'

interface ArtistModalProps {
  isOpen: boolean
  onClose: () => void
  onSubmit: (data: ArtistFormData) => void
  initialData?: ArtistaDashboard | null
}

function ArtistModal({ isOpen, onClose, onSubmit, initialData }: ArtistModalProps) {
  if (!isOpen) return null

  return (
    <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
      <div className="bg-zinc-900 border border-white/10 rounded-3xl w-full max-w-md p-8 shadow-2xl animate-in fade-in zoom-in duration-200">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold text-white">
            {initialData ? 'Editar Artista' : 'Novo Artista'}
          </h2>
          <button onClick={onClose} className="p-2 hover:bg-white/5 rounded-full text-zinc-400">
            <X size={20} />
          </button>
        </div>

        <form onSubmit={(e) => {
          e.preventDefault()
          const formData = new FormData(e.currentTarget)
          onSubmit({
            nome: formData.get('nome') as string,
            tipo: formData.get('tipo') as string,
          })
        }} className="space-y-5">
          <div>
            <label className="text-sm text-zinc-400 block mb-2">Nome do Artista</label>
            <input
              name="nome"
              defaultValue={initialData?.nome}
              required
              autoFocus
              className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 outline-none focus:ring-2 focus:ring-purple-500/50 transition-all"
            />
          </div>
          <div>
            <label className="text-sm text-zinc-400 block mb-2">Tipo</label>
            <select
              name="tipo"
              defaultValue={initialData?.tipo || 'SOLO'}
              className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 outline-none focus:ring-2 focus:ring-purple-500/50 appearance-none"
            >
              <option value="SOLO">Solo</option>
              <option value="BANDA">Banda</option>
              <option value="DUPLA">Dupla</option>
            </select>
          </div>
          <button type="submit" className="w-full py-4 bg-purple-600 hover:bg-purple-500 text-white rounded-2xl font-bold transition-all shadow-lg shadow-purple-500/20 active:scale-[0.98]">
            {initialData ? 'Salvar Alterações' : 'Cadastrar Artista'}
          </button>
        </form>
      </div>
    </div>
  )
}

export default function HomePage() {
  const { token, logout } = useAuth()
  const [artistaDashboard, setArtistaDashboard] = useState<ArtistaDashboard[]>([])
  const [isLoading, setIsLoading] = useState(true)
  const [searchTerm, setSearchTerm] = useState('')
  const [page, setPage] = useState(0)
  const [totalItems, setTotalItems] = useState(0)
  const [sortDir, setSortDir] = useState<'asc' | 'desc'>('desc')

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [selectedArtist, setSelectedArtist] = useState<ArtistaDashboard | null>(null)

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
      toast.error("Erro ao carregar artistas")
    } finally {
      setIsLoading(false)
    }
  }, [token, searchTerm, page, sortDir])

  useEffect(() => {
    const delayDebounce = setTimeout(() => loadArtists(), 400)
    return () => clearTimeout(delayDebounce)
  }, [loadArtists])

  const handleSaveArtist = async (formData: ArtistFormData) => {
    try {
      if (selectedArtist) {
        const artistUpdate: Artist = {
          artistaId: selectedArtist.artistaId,
          nome: formData.nome,
          tipo: formData.tipo as ArtistType
        };

        await updateArtist(selectedArtist.artistaId, artistUpdate, token);
        toast.success("Artista atualizado!");
      } else {
        await createArtist(formData, token);
        toast.success("Artista criado!");
      }

      setIsModalOpen(false);
      loadArtists();
    } catch (err) {
      toast.error("Erro ao salvar dados");
      console.error(err);
    }
  };

  const handleDeleteArtist = async (id: string) => {
    if (!confirm("Tem certeza que deseja remover este artista?")) return
    try {
      await deleteArtist(id, token)
      toast.info("Artista removido")
      loadArtists()
    } catch (err) {
      toast.error("Erro ao remover")
    }
  }

  const totalPages = Math.ceil(totalItems / perPage)

  return (
    <div className="min-h-screen bg-zinc-950 text-zinc-100">
      <nav className="sticky top-0 z-50 border-b border-white/5 bg-zinc-900/70 backdrop-blur-xl">
        <div className="container mx-auto px-6 py-4 flex justify-between items-center">
          <div className="flex items-center gap-2">
            <div className="bg-purple-600 p-1.5 rounded-lg"><Music className="h-5 w-5 text-white" /></div>
            <span className="font-bold text-xl tracking-tight">MusicManager</span>
          </div>
          <button onClick={logout} className="flex items-center gap-2 text-zinc-400 hover:text-red-400 transition-colors">
            <LogOut className="h-4 w-4" /> Sair
          </button>
        </div>
      </nav>

      <main className="container mx-auto px-6 py-10">
        <div className="flex flex-col lg:flex-row lg:items-end justify-between gap-6 mb-12">
          <div>
            <h1 className="text-4xl font-black tracking-tighter sm:text-5xl">Artistas</h1>
            <p className="text-zinc-500 font-medium">Gerencie sua biblioteca de talentos.</p>
          </div>

          <div className="flex flex-col sm:flex-row gap-3">
            <div className="relative flex-1 sm:w-80">
              <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
              <input
                type="text"
                placeholder="Buscar artista..."
                className="w-full bg-zinc-900 border border-white/10 rounded-xl py-2.5 pl-10 pr-4 focus:border-purple-500/50 outline-none"
                onChange={(e) => { setSearchTerm(e.target.value); setPage(0); }}
              />
            </div>

            <button
              onClick={() => setSortDir(prev => prev === 'asc' ? 'desc' : 'asc')}
              className="flex items-center justify-center gap-2 bg-zinc-900 border border-white/10 px-4 py-2.5 rounded-xl hover:bg-zinc-800"
            >
              <ArrowUpDown className="h-4 w-4" /> {sortDir === 'asc' ? 'A-Z' : 'Z-A'}
            </button>

            <button
              onClick={() => { setSelectedArtist(null); setIsModalOpen(true); }}
              className="flex items-center justify-center gap-2 bg-purple-600 hover:bg-purple-500 px-6 py-2.5 rounded-xl font-bold transition-all shadow-lg shadow-purple-600/20"
            >
              <Plus className="h-4 w-4" /> Novo Artista
            </button>
          </div>
        </div>

        {isLoading ? (
          <div className="flex flex-col items-center justify-center py-32 gap-4">
            <Loader2 className="h-10 w-10 animate-spin text-purple-500" />
          </div>
        ) : (
          <>
            <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6">
              {artistaDashboard.map((artista) => (
                <ArtistCard
                  key={artista.artistaId}
                  artista={artista}
                  onEdit={() => { setSelectedArtist(artista); setIsModalOpen(true); }}
                  onDelete={() => handleDeleteArtist(artista.artistaId)}
                />
              ))}
            </div>

            {/* Paginação */}
            <div className="mt-12 flex flex-col sm:flex-row items-center justify-between gap-4 border-t border-white/5 pt-8">
              <span className="text-sm text-zinc-500">
                Página <span className="text-zinc-200 font-bold">{page + 1}</span> de {totalPages}
              </span>
              <div className="flex items-center gap-2">
                <button
                  disabled={page === 0}
                  onClick={() => setPage(p => p - 1)}
                  className="p-2.5 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-20 hover:bg-zinc-800"
                >
                  <ChevronLeft className="h-5 w-5" />
                </button>
                <button
                  disabled={page >= totalPages - 1}
                  onClick={() => setPage(p => p + 1)}
                  className="p-2.5 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-20 hover:bg-zinc-800"
                >
                  <ChevronRight className="h-5 w-5" />
                </button>
              </div>
            </div>
          </>
        )}
      </main>

      <ArtistModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSubmit={handleSaveArtist}
        initialData={selectedArtist}
      />
    </div>
  )
}

function ArtistCard({
  artista,
  onEdit,
  onDelete
}: {
  artista: ArtistaDashboard,
  onEdit: () => void,
  onDelete: () => void
}) {
  return (
    <div className="group relative bg-zinc-900/40 border border-white/5 rounded-3xl p-6 hover:bg-zinc-900/80 transition-all hover:border-purple-500/30 hover:-translate-y-1 shadow-2xl">
      <div className="absolute top-4 right-4 flex gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
        <button onClick={onEdit} className="p-2 bg-zinc-800 hover:text-blue-400 rounded-lg border border-white/5"><Edit3 size={16} /></button>
        <button onClick={onDelete} className="p-2 bg-zinc-800 hover:text-red-400 rounded-lg border border-white/5"><Trash2 size={16} /></button>
      </div>

      <div className="flex items-start justify-between mb-6">
        <div className="h-14 w-14 rounded-2xl bg-gradient-to-br from-purple-500/20 to-blue-500/20 border border-white/5 flex items-center justify-center">
          <Users className="h-7 w-7 text-purple-400" />
        </div>
        <span className="text-[10px] uppercase tracking-widest bg-zinc-800 text-zinc-400 px-2.5 py-1 rounded-full font-bold">
          {artista.tipo}
        </span>
      </div>

      <h3 className="text-xl font-bold text-white mb-2 truncate group-hover:text-purple-400 transition-colors">
        {artista.nome}
      </h3>

      <div className="flex items-center gap-2 text-zinc-500 text-sm mb-6">
        <Music className="h-4 w-4" />
        <span>{artista.quantidadeAlbuns} {artista.quantidadeAlbuns === 1 ? 'Álbum' : 'Álbuns'}</span>
      </div>

      <button className="w-full py-3 rounded-2xl bg-zinc-800 border border-white/5 text-sm font-bold hover:bg-white hover:text-black transition-all">
        Ver Detalhes
      </button>
    </div>
  )
}