
import { Disc, Plus, SearchX } from 'lucide-react';

interface AlbumEmptyStateProps {
    searchTerm: string;
    onClearSearch: () => void;
    onCreateClick: () => void;
}

export function AlbumEmptyState({ searchTerm, onClearSearch, onCreateClick }: AlbumEmptyStateProps) {
    const isSearching = searchTerm.length > 0;

    return (
        <div className="flex flex-col items-center justify-center py-24 px-4 bg-zinc-900/20 border-2 border-dashed border-zinc-800/50 rounded-[2.5rem] animate-in fade-in zoom-in-95 duration-500">
            <div className="relative mb-8">
                <div className="absolute inset-0 bg-purple-500/20 blur-3xl rounded-full" />

                <div className="relative h-28 w-28 bg-zinc-900 border border-white/10 rounded-3xl flex items-center justify-center shadow-2xl">
                    {isSearching ? (
                        <SearchX className="h-12 w-12 text-zinc-600" />
                    ) : (
                        <Disc className="h-12 w-12 text-zinc-700" />
                    )}

                    {!isSearching && (
                        <div className="absolute -bottom-2 -right-2 bg-purple-600 rounded-full p-1.5 border-4 border-zinc-950 shadow-xl">
                            <Plus size={20} className="text-white" />
                        </div>
                    )}
                </div>
            </div>

            <div className="text-center max-w-sm">
                <h3 className="text-2xl font-bold text-white mb-2">
                    {isSearching ? 'Nenhum resultado' : 'Sua biblioteca está vazia'}
                </h3>
                <p className="text-zinc-500 font-medium mb-8 leading-relaxed">
                    {isSearching
                        ? `Não encontramos nenhum álbum que corresponda à busca "${searchTerm}".`
                        : 'Você ainda não possui álbuns cadastrados. Comece criando seu primeiro acervo agora.'}
                </p>

                {isSearching ? (
                    <button
                        onClick={onClearSearch}
                        className="px-6 py-2 text-purple-400 hover:text-purple-300 font-bold transition-colors border border-purple-500/20 hover:border-purple-500/40 rounded-xl bg-purple-500/5"
                    >
                        Limpar filtros de busca
                    </button>
                ) : (
                    <button
                        onClick={onCreateClick}
                        className="bg-purple-600 hover:bg-purple-500 px-8 py-4 rounded-2xl font-bold flex items-center gap-3 transition-all shadow-xl shadow-purple-900/40 hover:-translate-y-1 active:scale-95"
                    >
                        <Plus size={22} /> Cadastrar Meu Primeiro Álbum
                    </button>
                )}
            </div>
        </div>
    );
}