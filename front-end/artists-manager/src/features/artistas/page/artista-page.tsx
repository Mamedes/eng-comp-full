import { useEffect, useState } from 'react';
import { Users, Search, Plus, Loader2, Trash2, Edit3, ChevronLeft, ChevronRight } from 'lucide-react';
import { artistaFacade } from '../facades/artista.facade';
import { useObservable } from '@/shared/hooks/use-observable';
import { ArtistaDashboard } from '../types';
import { ArtistModal } from './componentes/artista-modal';
import { ConfirmModal } from '@/features/layout/components/confirm-modal';

const INITIAL_ARTISTA_STATE = {
    data: [],
    pagination: { page: 0, perPage: 8, total: 0, totalPages: 0 },
    isLoading: false,
    search: "",
};

export default function ArtistPage() {
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedArtista, setSelectedArtista] = useState<ArtistaDashboard | null>(null);
    const [artistaToDelete, setArtistaToDelete] = useState<string | null>(null);
    const { data, isLoading, pagination } = useObservable(
        artistaFacade.state$,
        INITIAL_ARTISTA_STATE
    );
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);

    const handleNew = () => {
        setSelectedArtista(null);
        setIsModalOpen(true);
    };

    const handleEdit = (artist: ArtistaDashboard) => {
        setSelectedArtista(artist);
        setIsModalOpen(true);
    };

    const handleDeleteClick = (id: string) => {
        setArtistaToDelete(id);
        setIsDeleteModalOpen(true);
    };

    const confirmDelete = async () => {
        if (!artistaToDelete) return;
        setIsDeleting(true);
        const success = await artistaFacade.deleteArtista(artistaToDelete);
        if (success) {
            setIsDeleteModalOpen(false);
            setArtistaToDelete(null);
        }
        setIsDeleting(false);
    };

    useEffect(() => {
        artistaFacade.loadArtistas();
    }, []);

    return (
        <div className="p-8 space-y-8 min-h-screen bg-zinc-950 text-zinc-100">
            <div className="flex flex-col md:flex-row md:items-center justify-between gap-4">
                <div>
                    <h1 className="text-3xl font-bold text-white">Artistas</h1>
                    <p className="text-zinc-500">Gerencie sua base de talentos</p>
                </div>

                <div className="flex gap-3">
                    <div className="relative">
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
                        <input
                            type="text"
                            placeholder="Buscar artista..."
                            className="bg-zinc-900 border border-white/10 rounded-xl pl-10 pr-4 py-2 text-sm text-white focus:ring-2 focus:ring-purple-500 outline-none w-64"
                            onChange={(e) => artistaFacade.updateSearch(e.target.value)}
                        />
                    </div>
                    <button
                        onClick={handleNew}
                        className="bg-purple-600 hover:bg-purple-700 text-white px-4 py-2 rounded-xl text-sm font-bold flex items-center gap-2 transition-all shadow-lg shadow-purple-900/20">
                        <Plus size={18} /> Novo Artista
                    </button>
                </div>
            </div>

            {isLoading ? (
                <div className="flex justify-center py-20">
                    <Loader2 className="animate-spin text-purple-500" size={40} />
                </div>
            ) : (
                <>
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                        {data.map((artista) => (
                            <div key={artista.artistaId} className="group bg-zinc-900/40 border border-white/5 p-6 rounded-3xl hover:bg-zinc-900/80 hover:border-purple-500/30 transition-all hover:-translate-y-1 shadow-xl">
                                <div className="flex justify-between items-start mb-4">
                                    <div className="p-3 bg-gradient-to-br from-purple-500/10 to-blue-500/10 rounded-2xl text-purple-400 border border-white/5">
                                        <Users size={24} />
                                    </div>
                                    <div className="flex gap-2 opacity-0 group-hover:opacity-100 transition-opacity">
                                        <button
                                            onClick={() => handleEdit(artista)}
                                            className="p-2 bg-zinc-800 hover:text-blue-400 rounded-lg border border-white/5 transition-colors">
                                            <Edit3 size={16} />
                                        </button>
                                        <button
                                            onClick={() => handleDeleteClick(artista.artistaId)}
                                            className="p-2 bg-zinc-800 hover:text-red-400 rounded-lg border border-white/5 transition-colors"
                                        >
                                            <Trash2 size={16} />
                                        </button>
                                    </div>
                                </div>
                                <h3 className="text-lg font-bold text-white truncate">{artista.nome}</h3>
                                <p className="text-[10px] uppercase tracking-widest bg-zinc-800 text-zinc-400 px-2 py-0.5 rounded-full inline-block mt-2 font-bold">{artista.tipo}</p>
                                <div className="mt-4 pt-4 border-t border-white/5 text-sm text-zinc-400 flex items-center gap-2">
                                    <span className="text-zinc-500">Álbuns:</span>
                                    <span className="text-white font-medium">{artista.quantidadeAlbuns}</span>
                                </div>
                            </div>
                        ))}
                    </div>

                    {data.length > 0 && (
                        <div className="flex flex-col sm:flex-row items-center justify-between gap-4 border-t border-white/5 pt-8 mt-8">
                            <span className="text-sm text-zinc-500">
                                Página <span className="text-white font-bold">{pagination.page + 1}</span> de {pagination.totalPages || 1}
                            </span>
                            <div className="flex items-center gap-2">
                                <button
                                    onClick={() => artistaFacade.changePage(pagination.page - 1)}
                                    disabled={pagination.page === 0}
                                    className="p-2 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-30 disabled:cursor-not-allowed hover:bg-zinc-800 text-white transition-colors"
                                >
                                    <ChevronLeft size={20} />
                                </button>
                                <button
                                    onClick={() => artistaFacade.changePage(pagination.page + 1)}
                                    disabled={pagination.page >= (pagination.totalPages - 1)}
                                    className="p-2 rounded-xl bg-zinc-900 border border-white/10 disabled:opacity-30 disabled:cursor-not-allowed hover:bg-zinc-800 text-white transition-colors"
                                >
                                    <ChevronRight size={20} />
                                </button>
                            </div>
                        </div>
                    )}

                    {data.length === 0 && !isLoading && (
                        <div className="text-center py-20 text-zinc-500">
                            Nenhum artista encontrado.
                        </div>
                    )}
                </>
            )}
            <ConfirmModal
                isOpen={isDeleteModalOpen}
                isLoading={isDeleting}
                title="Excluir Artista"
                description="Tem certeza que deseja remover este artista? Esta ação não pode ser desfeita."
                onClose={() => setIsDeleteModalOpen(false)}
                onConfirm={confirmDelete}
            />
            <ArtistModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                initialData={selectedArtista}
            />
        </div>
    );
}