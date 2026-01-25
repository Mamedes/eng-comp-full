import { X, Loader2 } from 'lucide-react';
import { FormEvent, useEffect, useState } from 'react';
import { ArtistaDashboard, ArtistaType } from '../../types';
import { artistaFacade } from '../../facades/artista.facade';

interface Props {
    isOpen: boolean;
    onClose: () => void;
    initialData?: ArtistaDashboard | null;
}

export function ArtistModal({ isOpen, onClose, initialData }: Props) {
    const [isSubmitting, setIsSubmitting] = useState(false);

    if (!isOpen) return null;

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setIsSubmitting(true);

        const formData = new FormData(e.currentTarget);
        const data = {
            nome: formData.get('nome') as string,
            tipo: formData.get('tipo') as ArtistaType,
        };

        const success = await artistaFacade.saveArtista(
            data,
            initialData?.artistaId
        );

        setIsSubmitting(false);

        if (success) {
            onClose();
        }
    };

    return (
        <div className="fixed inset-0 z-[100] flex items-center justify-center p-4 bg-black/60 backdrop-blur-sm animate-in fade-in duration-200">
            <div className="bg-zinc-900 border border-white/10 rounded-3xl w-full max-w-md p-8 shadow-2xl relative">

                <div className="flex justify-between items-center mb-6">
                    <h2 className="text-2xl font-bold text-white">
                        {initialData ? 'Editar Artista' : 'Novo Artista'}
                    </h2>
                    <button
                        onClick={onClose}
                        className="p-2 hover:bg-white/5 rounded-full text-zinc-400 hover:text-white transition-colors"
                    >
                        <X size={20} />
                    </button>
                </div>

                <form onSubmit={handleSubmit} className="space-y-5">
                    <div>
                        <label className="text-sm text-zinc-400 block mb-2 font-medium">Nome do Artista</label>
                        <input
                            name="nome"
                            defaultValue={initialData?.nome}
                            required
                            autoFocus
                            placeholder="Ex: Legião Urbana"
                            className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 text-white outline-none focus:ring-2 focus:ring-purple-500 transition-all placeholder:text-zinc-600"
                        />
                    </div>

                    <div>
                        <label className="text-sm text-zinc-400 block mb-2 font-medium">Tipo</label>
                        <select
                            name="tipo"
                            defaultValue={initialData?.tipo || "SOLO"}
                            className="w-full bg-zinc-800 border border-white/5 rounded-xl p-3 text-white outline-none focus:ring-2 focus:ring-purple-500 appearance-none"
                        >
                            <option value="SOLO">Solo</option>
                            <option value="BANDA">Banda</option>
                            <option value="DUPLA">Dupla</option>
                        </select>
                    </div>

                    <button
                        type="submit"
                        disabled={isSubmitting}
                        className="w-full py-3.5 bg-purple-600 hover:bg-purple-500 disabled:bg-purple-600/50 text-white rounded-2xl font-bold transition-all shadow-lg shadow-purple-900/20 flex items-center justify-center gap-2"
                    >
                        {isSubmitting ? (
                            <><Loader2 className="animate-spin" size={20} /> Salvando...</>
                        ) : (
                            initialData ? 'Salvar Alterações' : 'Cadastrar Artista'
                        )}
                    </button>
                </form>
            </div>
        </div>
    );
}