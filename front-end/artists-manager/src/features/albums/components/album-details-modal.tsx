import { useEffect, useState } from 'react';
import { X, Image as ImageIcon, Loader2, Trash2 } from 'lucide-react';
import { Album, AlbumImage } from '../types';
import { albumFacade } from '../facades/album.facade';
import { ImageUploadSection } from './image-upload-section';
import { ConfirmModal } from '@/features/layout/components/confirm-modal';

interface Props {
    album: Album | null;
    onClose: () => void;
}

export function AlbumDetailsModal({ album, onClose }: Props) {
    const [images, setImages] = useState<AlbumImage[]>([]);
    const [loading, setLoading] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);
    const [imageToDelete, setImageToDelete] = useState<string | null>(null);

    const loadImages = async () => {
        if (!album) return;
        setLoading(true);
        try {
            const data = await albumFacade.getAlbumImages(album.albumId);
            setImages(data);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        if (album) {
            loadImages();
        } else {
            setImages([]);
        }
    }, [album]);

    const confirmDelete = async () => {
        if (!imageToDelete) return;

        setIsDeleting(true);
        try {
            const success = await albumFacade.deleteAlbumImage(imageToDelete);
            if (success) {
                setImages(prev => prev.filter(img => img.albumImagemId !== imageToDelete));
                setIsDeleteModalOpen(false);
                setImageToDelete(null);
            }
        } finally {
            setIsDeleting(false);
        }
    };

    const handleDeleteClick = (imageId: string) => {
        setImageToDelete(imageId);
        setIsDeleteModalOpen(true);
    };

    if (!album) return null;

    return (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/90 backdrop-blur-md animate-in fade-in duration-200">
            <div className="bg-zinc-950 border border-white/10 rounded-3xl w-full max-w-5xl max-h-[90vh] flex flex-col shadow-2xl overflow-hidden">

                <div className="p-6 border-b border-white/5 flex justify-between items-center bg-zinc-900/50">
                    <div>
                        <h2 className="text-2xl sm:text-3xl font-black text-white leading-none flex items-center gap-3">
                            {album.albumTitulo}
                            <span className="text-xs font-medium bg-zinc-800 text-zinc-400 px-2 py-1 rounded-lg border border-white/5">
                                {images.length} fotos
                            </span>
                        </h2>
                    </div>

                    <button
                        onClick={onClose}
                        className="p-2 bg-zinc-800 text-zinc-400 rounded-full hover:bg-white/10 hover:text-white transition-colors"
                    >
                        <X size={20} />
                    </button>
                </div>

                <div className="flex-1 overflow-y-auto p-6 custom-scrollbar bg-black/20">

                    <div className="mb-10 bg-zinc-900/30 p-6 rounded-3xl border border-white/5">
                        <h3 className="text-sm font-bold text-zinc-500 uppercase tracking-widest mb-4">Adicionar novas fotos</h3>
                        <ImageUploadSection
                            albumId={album.albumId}
                            onSuccess={loadImages}
                        />
                    </div>

                    <div className="space-y-4">
                        <h3 className="text-sm font-bold text-zinc-500 uppercase tracking-widest">Galeria do Álbum</h3>

                        {loading && images.length === 0 ? (
                            <div className="flex flex-col items-center justify-center py-20 gap-4">
                                <Loader2 className="animate-spin text-purple-500 h-10 w-10" />
                                <span className="text-zinc-500 font-medium">Carregando galeria...</span>
                            </div>
                        ) : images.length > 0 ? (
                            <div className="grid grid-cols-2 sm:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4">
                                {images.map((img) => (
                                    <div key={img.albumImagemId} className="group relative aspect-square rounded-2xl overflow-hidden bg-zinc-900 border border-white/5 shadow-lg">
                                        <img
                                            src={img.linkTemporario}
                                            alt={img.fileName}
                                            className="w-full h-full object-cover transition-transform duration-700 group-hover:scale-110"
                                        />

                                        <div className="absolute inset-0 bg-black/60 opacity-0 group-hover:opacity-100 transition-opacity flex flex-col justify-between p-3">
                                            <div className="flex justify-end">
                                                <button
                                                    onClick={() => handleDeleteClick(img.albumImagemId)}
                                                    className="p-2 bg-red-500/80 hover:bg-red-500 text-white rounded-lg transition-colors shadow-lg backdrop-blur-sm"
                                                >
                                                    <Trash2 size={16} />
                                                </button>
                                            </div>
                                            <span className="text-[10px] font-bold text-zinc-300 truncate text-center bg-black/50 p-1 rounded-lg backdrop-blur-md">
                                                {img.fileName}
                                            </span>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <div className="flex flex-col items-center justify-center py-20 text-zinc-500 gap-4 border-2 border-dashed border-zinc-800/50 rounded-3xl bg-zinc-900/20">
                                <ImageIcon className="h-12 w-12 opacity-10" />
                                <p className="text-sm">Nenhuma imagem neste álbum ainda.</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
            <ConfirmModal
                isOpen={isDeleteModalOpen}
                isLoading={isDeleting}
                title="Excluir Imagem"
                description="Tem certeza que deseja remover este artista? Esta ação não pode ser desfeita."
                onClose={() => {
                    setIsDeleteModalOpen(false);
                    setImageToDelete(null);
                }}
                onConfirm={confirmDelete}
            />
        </div>
    );
}