import { useEffect, useState } from 'react';
import { X, Image as ImageIcon, Loader2 } from 'lucide-react';
import { AlbumDashboardItem, AlbumImage } from '../types';
import { albumFacade } from '../facades/album.facade';

interface Props {
    album: AlbumDashboardItem | null;
    onClose: () => void;
}

export function AlbumDetailsModal({ album, onClose }: Props) {
    const [images, setImages] = useState<AlbumImage[]>([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        if (album) {
            setLoading(true);
            albumFacade.getAlbumImages(album.albumId)
                .then(setImages)
                .finally(() => setLoading(false));
        }
    }, [album]);

    if (!album) return null;

    return (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/90 backdrop-blur-md animate-in fade-in duration-200">
            <div className="bg-zinc-950 border border-white/10 rounded-3xl w-full max-w-4xl max-h-[90vh] flex flex-col shadow-2xl overflow-hidden">

                <div className="p-6 border-b border-white/5 flex justify-between items-start bg-zinc-900/50">
                    <div>
                        <h2 className="text-3xl font-black text-white leading-none">{album.albumTitulo}</h2>
                        <p className="text-purple-400 font-medium mt-2">
                            {album.artistaNome} • <span className="text-zinc-500 text-sm uppercase">{album.artistaTipo}</span>
                        </p>
                    </div>
                    <button
                        onClick={onClose}
                        className="p-2 bg-zinc-800 text-zinc-400 rounded-full hover:bg-white/10 hover:text-white transition-colors"
                    >
                        <X size={20} />
                    </button>
                </div>

                <div className="flex-1 overflow-y-auto p-6 custom-scrollbar">
                    {loading ? (
                        <div className="flex flex-col items-center justify-center py-20 gap-4">
                            <Loader2 className="animate-spin text-purple-500 h-10 w-10" />
                            <span className="text-zinc-500 font-medium">Carregando galeria...</span>
                        </div>
                    ) : images.length > 0 ? (
                        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                            {images.map((img) => (
                                <div key={img.secureId} className="group relative aspect-square rounded-2xl overflow-hidden bg-zinc-900 border border-white/5">
                                    <img
                                        src={img.linkTemporario}
                                        alt={img.fileName}
                                        className="w-full h-full object-cover group-hover:scale-110 transition-transform duration-700"
                                    />
                                    <div className="absolute inset-0 bg-gradient-to-t from-black/90 via-black/20 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-4">
                                        <span className="text-[10px] font-bold text-white truncate uppercase tracking-tighter">
                                            {img.fileName}
                                        </span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center py-20 text-zinc-500 gap-4 border-2 border-dashed border-zinc-800 rounded-3xl">
                            <ImageIcon className="h-12 w-12 opacity-10" />
                            <p className="font-medium">Este álbum ainda não possui fotos.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}