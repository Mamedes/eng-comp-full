import { useCallback, useEffect, useState } from 'react'
import { useAuth } from '@/context/auth-context'
import { getAlbumsDashboard } from '@/lib/api'
import { AlbumDashboardItem } from '@/lib/types'
import { Disc, Search, Plus, Loader2, ChevronLeft, ChevronRight, Eye } from 'lucide-react'
import { CreateAlbumModal } from './CreateAlbumModal'
import { AlbumDetailsModal } from '../components/AlbumDetailsModal'

export default function AlbumsPage() {
    const { token } = useAuth()
    const [albums, setAlbums] = useState<AlbumDashboardItem[]>([])
    const [loading, setLoading] = useState(true)
    const [total, setTotal] = useState(0)

    const [search, setSearch] = useState('')
    const [page, setPage] = useState(0)
    const perPage = 8

    const [isCreateOpen, setIsCreateOpen] = useState(false)
    const [selectedAlbum, setSelectedAlbum] = useState<AlbumDashboardItem | null>(null)

    const loadAlbums = useCallback(async () => {
        try {
            setLoading(true)
            const data = await getAlbumsDashboard({
                page,
                perPage,
                sort: 'titulo', 
                dir: 'asc',
                search
            }, token)

            setAlbums(data.items)
            setTotal(data.total)
        } catch (error) {
            console.error(error)
        } finally {
            setLoading(false)
        }
    }, [token, page, search])

    useEffect(() => {
        const timer = setTimeout(() => loadAlbums(), 400)
        return () => clearTimeout(timer)
    }, [loadAlbums])

    const totalPages = Math.ceil(total / perPage)

    return (
        <div className="container mx-auto px-6 py-10 animate-in fade-in duration-500">

            <div className="flex flex-col lg:flex-row lg:items-end justify-between gap-6 mb-12">
                <div>
                    <h1 className="text-4xl font-black tracking-tighter sm:text-5xl flex items-center gap-3">
                        <Disc className="text-purple-500" /> Álbuns
                    </h1>
                    <p className="text-zinc-500 font-medium mt-2">Acervo discográfico e galerias.</p>
                </div>

                <div className="flex gap-3 w-full lg:w-auto">
                    <div className="relative flex-1 lg:w-80">
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
                        <input
                            onChange={e => { setSearch(e.target.value); setPage(0) }}
                            placeholder="Buscar álbum..."
                            className="w-full bg-zinc-900 border border-white/10 rounded-xl py-2.5 pl-10 pr-4 outline-none focus:ring-2 focus:ring-purple-500/30"
                        />
                    </div>
                    <button
                        onClick={() => setIsCreateOpen(true)}
                        className="bg-purple-600 hover:bg-purple-500 px-6 py-2.5 rounded-xl font-bold flex items-center gap-2 transition-all shadow-lg shadow-purple-900/20"
                    >
                        <Plus size={18} /> Novo Álbum
                    </button>
                </div>
            </div>

            {loading ? (
                <div className="flex justify-center py-32"><Loader2 className="animate-spin h-8 w-8 text-purple-500" /></div>
            ) : (
                <div className="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-4 gap-6">
                    {albums.map(album => (
                        <div key={album.albumId} className="group bg-zinc-900/40 border border-white/5 p-6 rounded-3xl hover:bg-zinc-900 transition-all hover:-translate-y-1 hover:border-purple-500/30 shadow-xl">
                            <div className="flex justify-between items-start mb-4">
                                <div className="h-12 w-12 rounded-full bg-zinc-800 flex items-center justify-center border border-white/5">
                                    <Disc className="text-zinc-400" />
                                </div>
                                <span className="text-[10px] font-bold bg-zinc-800 text-zinc-400 px-2 py-1 rounded-md uppercase tracking-wider">
                                    {album.artistaTipo}
                                </span>
                            </div>

                            <h3 className="text-xl font-bold text-white mb-1 truncate" title={album.albumTitulo}>{album.albumTitulo}</h3>
                            <p className="text-sm text-purple-400 mb-6 font-medium">{album.artistaNome}</p>

                            <button
                                onClick={() => setSelectedAlbum(album)}
                                className="w-full py-3 bg-zinc-800 hover:bg-white hover:text-black rounded-xl text-sm font-bold transition-colors flex items-center justify-center gap-2"
                            >
                                <Eye size={16} /> Ver Galeria
                            </button>
                        </div>
                    ))}
                </div>
            )}

            <div className="mt-12 flex justify-center gap-2">
                <button disabled={page === 0} onClick={() => setPage(p => p - 1)} className="p-2 bg-zinc-900 rounded-lg disabled:opacity-30"><ChevronLeft /></button>
                <span className="px-4 py-2 bg-zinc-900 rounded-lg font-bold">{page + 1} / {totalPages || 1}</span>
                <button disabled={page >= totalPages - 1} onClick={() => setPage(p => p + 1)} className="p-2 bg-zinc-900 rounded-lg disabled:opacity-30"><ChevronRight /></button>
            </div>

            <CreateAlbumModal
                isOpen={isCreateOpen}
                onClose={() => setIsCreateOpen(false)}
                onSuccess={loadAlbums}
            />

            <AlbumDetailsModal
                album={selectedAlbum}
                onClose={() => setSelectedAlbum(null)}
            />
        </div>
    )
}