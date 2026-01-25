import { useState, useEffect, useMemo } from 'react';
import { X, Loader2, Check, Search } from 'lucide-react';
import { Artista } from '@/features/artistas/types';
import { ArtistaService } from '@/features/artistas/services/artista.service';
import { AlbumService } from '../service/album.service';
import { toast } from 'react-toastify';
import { Album } from '../types';

interface Props {
    isOpen: boolean;
    onClose: () => void;
    onSuccess: () => void;
    initialData?: Album | null;
}

export function AlbumFormModal({ isOpen, onClose, onSuccess, initialData }: Props) {
    const [titulo, setTitulo] = useState('');
    const [selectedArtistas, setSelectedArtistas] = useState<string[]>([]);
    const [artistasDisponiveis, setArtistasDisponiveis] = useState<Artista[]>([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [isLoadingArtistas, setIsLoadingArtistas] = useState(false);

    useEffect(() => {
        if (isOpen) {
            loadArtistas();

            if (initialData) {
                setTitulo(initialData.albumTitulo);
                setSelectedArtistas(initialData.artistaIds || []);
            } else {
                setTitulo('');
                setSelectedArtistas([]);
            }
        }
    }, [isOpen, initialData])

    useEffect(() => {
        if (isOpen) {
            loadArtistas();
        }
    }, [isOpen]);

    async function loadArtistas() {
        setIsLoadingArtistas(true);
        try {
            const response = await ArtistaService.listAll({ perPage: 100 });
            setArtistasDisponiveis(response.items);
        } catch (error) {
            console.error("Erro ao carregar artistas", error);
        } finally {
            setIsLoadingArtistas(false);
        }
    }

    const filteredArtistas = useMemo(() => {
        return artistasDisponiveis.filter(a =>
            a.nome.toLowerCase().includes(searchTerm.toLowerCase())
        );
    }, [searchTerm, artistasDisponiveis]);

    const toggleArtista = (id: string) => {
        setSelectedArtistas(prev =>
            prev.includes(id) ? prev.filter(a => a !== id) : [...prev, id]
        );
    };
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!titulo || selectedArtistas.length === 0) {
            toast.warn("Preencha o título e selecione ao menos um artista.");
            return;
        }

        setIsSubmitting(true);
        try {
            const payload = {
                albumTitulo: titulo,
                artistaIds: selectedArtistas
            };

            if (initialData) {
                await AlbumService.update(initialData.albumId, payload);
                toast.success("Álbum atualizado com sucesso!");
            } else {
                await AlbumService.create(payload);
                toast.success("Álbum cadastrado com sucesso!");
            }

            onSuccess();
            handleClose();
        } catch (error: any) {
            const backendMessage = error.response?.data?.message;
            toast.error(backendMessage || "Erro ao processar requisição.");
        } finally {
            setIsSubmitting(false);
        }
    };
    const handleClose = () => {
        setTitulo('');
        setSelectedArtistas([]);
        setSearchTerm('');
        onClose();
    };

    if (!isOpen) return null;
    const modalTitle = initialData ? "Editar Álbum" : "Novo Álbum";
    return (
        <div className="fixed inset-0 z-50 flex items-end sm:items-center justify-center p-0 sm:p-4 bg-black/80 backdrop-blur-sm">
            <div className="bg-zinc-900 border-t sm:border border-white/10 w-full max-w-md rounded-t-3xl sm:rounded-3xl p-6 sm:p-8 shadow-2xl max-h-[90vh] flex flex-col">

                <div className="flex justify-between items-center mb-6">
                    <h2 className="text-xl sm:text-2xl font-bold text-white">{modalTitle}</h2>
                    <button onClick={handleClose} className="p-2 text-zinc-500 hover:text-white transition-colors">
                        <X size={24} />
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="space-y-6 overflow-y-auto pr-1">
                    <div>
                        <label className="block text-sm font-medium text-zinc-400 mb-2">Título do Álbum</label>
                        <input
                            required
                            value={titulo}
                            onChange={e => setTitulo(e.target.value)}
                            className="w-full bg-zinc-800 border border-white/5 rounded-xl px-4 py-3 text-white outline-none focus:ring-2 focus:ring-purple-500 transition-all"
                            placeholder="Ex: Thriller"
                        />
                    </div>

                    <div>
                        <label className="block text-sm font-medium text-zinc-400 mb-2">
                            Vincular Artistas ({selectedArtistas.length})
                        </label>

                        <div className="relative mb-3">
                            <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-zinc-500" />
                            <input
                                type="text"
                                value={searchTerm}
                                onChange={e => setSearchTerm(e.target.value)}
                                placeholder="Filtrar artistas..."
                                className="w-full bg-zinc-800/50 border border-white/5 rounded-lg pl-10 pr-4 py-2 text-sm text-white outline-none focus:border-purple-500/50"
                            />
                        </div>

                        <div className="bg-zinc-800/30 border border-white/5 rounded-xl max-h-40 overflow-y-auto p-2 space-y-1 custom-scrollbar">
                            {isLoadingArtistas ? (
                                <div className="flex justify-center p-4"><Loader2 className="animate-spin text-purple-500" /></div>
                            ) : filteredArtistas.length > 0 ? (
                                filteredArtistas.map(artista => (
                                    <button
                                        key={artista.artistaId}
                                        type="button"
                                        onClick={() => toggleArtista(artista.artistaId)}
                                        className={`w-full flex items-center justify-between px-3 py-2.5 rounded-lg text-sm transition-all ${selectedArtistas.includes(artista.artistaId)
                                            ? 'bg-purple-600 text-white'
                                            : 'hover:bg-white/5 text-zinc-300'
                                            }`}
                                    >
                                        <span className="truncate mr-2">{artista.nome}</span>
                                        {selectedArtistas.includes(artista.artistaId) && <Check size={14} className="shrink-0" />}
                                    </button>
                                ))
                            ) : (
                                <div className="text-center py-4 text-xs text-zinc-500">Nenhum artista encontrado.</div>
                            )}
                        </div>
                    </div>

                    <div className="flex flex-col-reverse sm:flex-row gap-3 pt-4 border-t border-white/5">
                        <button
                            type="button"
                            onClick={handleClose}
                            className="w-full sm:flex-1 px-6 py-3 rounded-xl font-bold bg-zinc-800 hover:bg-zinc-700 text-white transition-colors"
                        >
                            Cancelar
                        </button>
                        <button
                            type="submit"
                            disabled={isSubmitting || !titulo || selectedArtistas.length === 0}
                            className="w-full sm:flex-[2] px-6 py-3 rounded-xl font-bold bg-purple-600 hover:bg-purple-500 text-white transition-all disabled:opacity-50 disabled:cursor-not-allowed flex items-center justify-center gap-2"
                        >
                            {isSubmitting ? <Loader2 className="animate-spin" size={18} /> : 'Salvar Álbum'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}