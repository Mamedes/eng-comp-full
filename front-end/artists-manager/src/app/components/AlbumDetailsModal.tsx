import { useEffect, useState } from 'react'
import { X, Image as ImageIcon, Loader2 } from 'lucide-react'
import { getAlbumImages } from '@/lib/api'
import { AlbumDashboardItem, AlbumImage } from '@/lib/types'
import { useAuth } from '@/context/auth-context'

interface Props {
    album: AlbumDashboardItem | null
    onClose: () => void
}

export function AlbumDetailsModal({ album, onClose }: Props) {
    const { token } = useAuth()
    const [images, setImages] = useState<AlbumImage[]>([])
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        if (album) {
            setLoading(true)
            getAlbumImages(album.albumId, token)
                .then(setImages)
                .catch(console.error)
                .finally(() => setLoading(false))
        }
    }, [album, token])

    if (!album) return null

    return (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/90 backdrop-blur-md">
            <div className="bg-zinc-950 border border-white/10 rounded-3xl w-full max-w-4xl max-h-[90vh] flex flex-col shadow-2xl overflow-hidden">

                {/* Header */}
                <div className="p-6 border-b border-white/5 flex justify-between items-start bg-zinc-900/50">
                    <div>
                        <h2 className="text-3xl font-black text-white">{album.albumTitulo}</h2>
                        <p className="text-purple-400 font-medium mt-1">{album.artistaNome} • <span className="text-zinc-500 text-sm">{album.artistaTipo}</span></p>
                    </div>
                    <button onClick={onClose} className="p-2 bg-zinc-800 rounded-full hover:bg-white/10 transition-colors"><X /></button>
                </div>

                <div className="flex-1 overflow-y-auto p-6">
                    {loading ? (
                        <div className="flex justify-center py-20"><Loader2 className="animate-spin text-purple-500 h-10 w-10" /></div>
                    ) : images.length > 0 ? (
                        <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                            {images.map((img) => (
                                <div key={img.secureId} className="group relative aspect-square rounded-xl overflow-hidden bg-zinc-900 border border-white/5">
                                    <img
                                        src={img.linkTemporario}
                                        alt={img.fileName}
                                        className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-500"
                                    />
                                    <div className="absolute inset-0 bg-gradient-to-t from-black/80 to-transparent opacity-0 group-hover:opacity-100 transition-opacity flex items-end p-3">
                                        <span className="text-xs text-white truncate">{img.fileName}</span>
                                    </div>
                                </div>
                            ))}
                        </div>
                    ) : (
                        <div className="flex flex-col items-center justify-center py-20 text-zinc-500 gap-4 border-2 border-dashed border-zinc-800 rounded-2xl">
                            <ImageIcon className="h-12 w-12 opacity-20" />
                            <p>Nenhuma imagem encontrada para este álbum.</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}