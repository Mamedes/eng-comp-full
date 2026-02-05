import { useObservable } from '@/shared/hooks/use-observable';
import { ChevronLeft, ChevronRight, Disc, Edit3, Eye, Loader2, Plus, Search, Trash2 } from 'lucide-react';
import { useEffect, useState } from 'react';
import { albumFacade } from '../facades/album.facade';
import { Album } from '../types';
import { ConfirmModal } from '@/features/layout/components/confirm-modal';
import { AlbumFormModal } from '../components/album-form-modal';
import { AlbumDetailsModal } from '../components/album-details-modal';
import { AlbumEmptyState } from '../components/album-empty-state';

const INITIAL_STATE = {
    data: [] as Album[],
    total: 0,
    isLoading: false,
    search: "",
    page: 0,
};

export default function AlbumsPage() {
    const { data: albums, isLoading, total, page } = useObservable(albumFacade.state$, INITIAL_STATE);
    const [albumToEdit, setAlbumToEdit] = useState<Album | null>(null);
    const [albumToDelete, setAlbumToDelete] = useState<string | null>(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [isCreateOpen, setIsCreateOpen] = useState(false);
    const [selectedAlbum, setSelectedAlbum] = useState<Album | null>(null);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            albumFacade.updateSearch(searchTerm);
        }, 400);
        return () => clearTimeout(timer);
    }, [searchTerm]);

    useEffect(() => {
        albumFacade.loadAlbums();
    }, [page]);

    const perPage = 8;
    const totalPages = Math.ceil(total / perPage);

    const handleDelete = async (id: string) => {
        setAlbumToDelete(id);
        setIsDeleteModalOpen(true);
    };

    const confirmDelete = async () => {
        if (!albumToDelete) return;
        setIsDeleting(true);
        const success = await albumFacade.deleteAlbum(albumToDelete);
        if (success) {
            setIsDeleteModalOpen(false);
            setAlbumToDelete(null);
        }
        setIsDeleting(false);
    };

    return (
        <div className="h-screen flex flex-col bg-zinc-950 text-zinc-100 overflow-hidden">

            <header className="px-8 pt-8 pb-4 flex-shrink-0">
                <div className="max-w-[1600px] mx-auto flex flex-col lg:flex-row lg:items-end justify-between gap-6">
                    <div>
                        <h1 className="text-4xl font-black tracking-tighter sm:text-5xl flex items-center gap-3">
                            <Disc className="text-purple-500" /> Álbuns
                        </h1>
                        <p className="text-zinc-500 font-medium mt-2">Acervo discográfico e galerias.</p>
                    </div>

                    <div className="flex gap-3 w-full lg:w-auto">
                        <div className="relative flex-1 lg:w-64">
                            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
                            <input
                                type="text"
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                placeholder="Buscar álbum..."
                                className="w-full bg-zinc-900 border border-white/10 rounded-xl pl-10 pr-4 py-2.5 text-sm text-white focus:ring-2 focus:ring-purple-500 outline-none"
                            />
                        </div>
                        <button
                            onClick={() => setIsCreateOpen(true)}
                            className="bg-purple-600 hover:bg-purple-500 px-6 py-2.5 rounded-xl font-bold flex items-center gap-2 transition-all shadow-lg shadow-purple-900/20 active:scale-95 whitespace-nowrap"
                        >
                            <Plus size={18} /> Novo Álbum
                        </button>
                    </div>
                </div>
            </header>

            <main className="flex-1 overflow-y-auto px-8 custom-scrollbar">
                <div className="max-w-[1600px] mx-auto py-6">
                    {isLoading ? (
                        <div className="flex flex-col items-center justify-center py-32 gap-4">
                            <Loader2 className="animate-spin h-10 w-10 text-purple-500" />
                            <span className="text-zinc-500 font-medium animate-pulse">Sincronizando acervo...</span>
                        </div>
                    ) : albums.length > 0 ? (
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6 pb-10">
                            {albums.map(album => (
                                <div key={album.albumId} className="group relative bg-zinc-900/40 border border-white/5 p-6 rounded-3xl hover:bg-zinc-900/80 transition-all hover:-translate-y-1 hover:border-purple-500/30 shadow-xl">
                                    <div className="absolute top-4 right-4 flex gap-2 opacity-0 group-hover:opacity-100 transition-opacity z-10">
                                        <button
                                            onClick={() => { setAlbumToEdit(album); setIsCreateOpen(true); }}
                                            className="p-2 bg-zinc-800 hover:text-blue-400 rounded-lg border border-white/5 transition-colors"
                                        >
                                            <Edit3 size={16} />
                                        </button>
                                        <button
                                            onClick={() => handleDelete(album.albumId)}
                                            className="p-2 bg-zinc-800 hover:text-red-400 rounded-lg border border-white/5 transition-colors"
                                        >
                                            <Trash2 size={16} />
                                        </button>
                                    </div>

                                    <div className="flex justify-between items-start mb-4">
                                        <div className="h-14 w-14 rounded-2xl bg-zinc-800 flex items-center justify-center border border-white/5 shadow-inner group-hover:bg-purple-500/10 transition-colors">
                                            <Disc className="text-zinc-400 group-hover:text-purple-500 transition-colors" size={28} />
                                        </div>
                                    </div>

                                    <h3 className="text-xl font-bold text-white mb-4 truncate pr-12" title={album.albumTitulo}>
                                        {album.albumTitulo}
                                    </h3>

                                    <button
                                        onClick={() => setSelectedAlbum(album)}
                                        className="w-full py-3 bg-zinc-800 hover:bg-white hover:text-black rounded-xl text-sm font-bold transition-all flex items-center justify-center gap-2"
                                    >
                                        <Eye size={16} /> Ver Galeria
                                    </button>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <AlbumEmptyState
                            searchTerm={searchTerm}
                            onClearSearch={() => setSearchTerm('')}
                            onCreateClick={() => setIsCreateOpen(true)}
                        />
                    )}
                </div>
            </main>

            {albums.length > 0 && (
                <footer className="px-8 py-6 bg-zinc-950/80 backdrop-blur-md border-t border-white/5 flex-shrink-0">
                    <div className="max-w-[1600px] mx-auto flex flex-col sm:flex-row items-center justify-between gap-4">
                        <span className="text-sm text-zinc-500">
                            Mostrando <span className="text-white font-bold">{albums.length}</span> de <span className="text-white font-bold">{total}</span> álbuns
                            <span className="mx-2">•</span>
                            Página <span className="text-white font-bold">{page + 1}</span> de {totalPages || 1}
                        </span>

                        <div className="flex items-center gap-2">
                            <button
                                onClick={() => albumFacade.setPage(page - 1)}
                                disabled={page === 0}
                                className="p-2.5 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-20 disabled:cursor-not-allowed hover:bg-zinc-800 text-white transition-colors"
                            >
                                <ChevronLeft size={20} />
                            </button>
                            <button
                                onClick={() => albumFacade.setPage(page + 1)}
                                disabled={page >= totalPages - 1}
                                className="p-2.5 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-20 disabled:cursor-not-allowed hover:bg-zinc-800 text-white transition-colors"
                            >
                                <ChevronRight size={20} />
                            </button>
                        </div>
                    </div>
                </footer>
            )}

            <AlbumFormModal
                isOpen={isCreateOpen}
                initialData={albumToEdit}
                onClose={() => {
                    setIsCreateOpen(false);
                    setAlbumToEdit(null);
                }}
                onSuccess={() => albumFacade.loadAlbums()}
            />
            <AlbumDetailsModal album={selectedAlbum} onClose={() => setSelectedAlbum(null)} />
            <ConfirmModal
                isOpen={isDeleteModalOpen}
                isLoading={isDeleting}
                title="Excluir Álbum"
                description="Tem certeza que deseja remover este álbum? Todas as fotos vinculadas serão perdidas."
                onClose={() => setIsDeleteModalOpen(false)}
                onConfirm={confirmDelete}
            />
        </div>
    );
}