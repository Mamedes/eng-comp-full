import { Artist, ArtistFormData } from '@/lib/types'
import { X } from 'lucide-react'
import { useForm } from 'react-hook-form'

interface Props {
  isOpen: boolean
  onClose: () => void
  onSubmit: (data: any) => void
  initialData?: Artist | null
}

export function ArtistFormModal({ isOpen, onClose, onSubmit, initialData }: Props) {
  if (!isOpen) return null

  return (
    <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm">
      <div className="bg-zinc-900 border border-white/10 rounded-3xl w-full max-auto max-w-md p-8 shadow-2xl">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold">{initialData ? 'Editar Artista' : 'Novo Artista'}</h2>
          <button onClick={onClose} className="p-2 hover:bg-white/5 rounded-full"><X /></button>
        </div>

        <form action={(formData: any) => {
          const data = {
            nome: formData.get('nome') as string,
            tipo: formData.get('tipo') as string,
            ...(initialData && { artistaId: initialData.artistaId })
          }
          onSubmit(data)
        }} className="space-y-4">
          <div>
            <label className="text-sm text-zinc-400 block mb-1">Nome do Artista</label>
            <input name="nome" defaultValue={initialData?.nome} required className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 outline-none focus:ring-2 focus:ring-purple-500" />
          </div>
          <div>
            <label className="text-sm text-zinc-400 block mb-1">Tipo</label>
            <select name="tipo" defaultValue={initialData?.tipo} className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 outline-none focus:ring-2 focus:ring-purple-500">
              <option value="SOLO">Solo</option>
              <option value="BANDA">Banda</option>
              <option value="DUPLA">Dupla</option>
            </select>
          </div>
          <button type="submit" className="w-full py-4 bg-purple-600 hover:bg-purple-500 rounded-2xl font-bold transition-all">
            Salvar Alterações
          </button>
        </form>
      </div>
    </div>
  )
}