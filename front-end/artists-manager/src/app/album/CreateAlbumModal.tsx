import { useState, useEffect } from 'react'
import { X, Save, Loader2 } from 'lucide-react'
import { getArtistsDashboard, createAlbum } from '@/lib/api' 
import { useAuth } from '@/context/auth-context'
import { toast } from 'react-toastify'

interface Props {
    isOpen: boolean
    onClose: () => void
    onSuccess: () => void
}

export function CreateAlbumModal({ isOpen, onClose, onSuccess }: Props) {
    const { token } = useAuth()
    const [artists, setArtists] = useState<{ id: string, nome: string }[]>([])
    const [isLoadingArtists, setIsLoadingArtists] = useState(false)
    const [isSubmitting, setIsSubmitting] = useState(false)

    // Carrega artistas simplificados para o Select
    useEffect(() => {
        if (isOpen) {
            setIsLoadingArtists(true)
            getArtistsDashboard({ page: 0, perPage: 100 }, token) 
                .then(data => setArtists(data.items.map(a => ({ id: a.artistaId, nome: a.nome }))))
                .catch(() => toast.error("Erro ao carregar artistas"))
                .finally(() => setIsLoadingArtists(false))
        }
    }, [isOpen, token])

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault()
        setIsSubmitting(true)
        const formData = new FormData(e.currentTarget)

        try {
            await createAlbum({
                titulo: formData.get('titulo') as string,
                artistas_ids: [formData.get('artistId') as string] 
            }, token)

            toast.success("Álbum criado com sucesso!")
            onSuccess()
            onClose()
        } catch (err) {
            toast.error("Erro ao criar álbum")
        } finally {
            setIsSubmitting(false)
        }
    }

    if (!isOpen) return null

    return (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm">
            <div className="bg-zinc-900 border border-white/10 rounded-3xl w-full max-w-md p-6 shadow-2xl animate-in fade-in zoom-in duration-200">
                <div className="flex justify-between items-center mb-6">
                    <h2 className="text-xl font-bold text-white">Novo Álbum</h2>
                    <button onClick={onClose}><X className="text-zinc-400 hover:text-white" /></button>
                </div>

                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <label className="text-sm text-zinc-400 block mb-2">Título do Álbum</label>
                        <input name="titulo" required className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 outline-none focus:ring-2 focus:ring-purple-500" />
                    </div>

                    <div>
                        <label className="text-sm text-zinc-400 block mb-2">Artista</label>
                        <select name="artistId" required className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 outline-none focus:ring-2 focus:ring-purple-500">
                            <option value="">Selecione um artista...</option>
                            {artists.map(a => (
                                <option key={a.id} value={a.id}>{a.nome}</option>
                            ))}
                        </select>
                        {isLoadingArtists && <span className="text-xs text-purple-400 mt-1">Carregando artistas...</span>}
                    </div>

                    <button disabled={isSubmitting} className="w-full py-3 bg-purple-600 hover:bg-purple-500 disabled:opacity-50 rounded-xl font-bold flex items-center justify-center gap-2 transition-all">
                        {isSubmitting ? <Loader2 className="animate-spin" /> : <><Save size={18} /> Salvar Álbum</>}
                    </button>
                </form>
            </div>
        </div>
    )
}