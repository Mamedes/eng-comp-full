import { AlertTriangle, X } from 'lucide-react';

interface ConfirmModalProps {
    isOpen: boolean;
    onClose: () => void;
    onConfirm: () => void;
    title: string;
    description: string;
    isLoading?: boolean;
}

export function ConfirmModal({ isOpen, onClose, onConfirm, title, description, isLoading }: ConfirmModalProps) {
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 z-[110] flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm animate-in fade-in duration-200">
            <div className="bg-zinc-900 border border-white/10 rounded-3xl w-full max-w-sm p-6 shadow-2xl">
                <div className="flex justify-center mb-4">
                    <div className="p-3 bg-red-500/10 rounded-2xl text-red-500">
                        <AlertTriangle size={32} />
                    </div>
                </div>

                <div className="text-center space-y-2 mb-6">
                    <h3 className="text-xl font-bold text-white">{title}</h3>
                    <p className="text-zinc-400 text-sm">{description}</p>
                </div>

                <div className="flex gap-3">
                    <button
                        onClick={onClose}
                        className="flex-1 py-3 bg-zinc-800 hover:bg-zinc-700 text-white rounded-xl font-semibold transition-all"
                    >
                        Cancelar
                    </button>
                    <button
                        onClick={onConfirm}
                        disabled={isLoading}
                        className="flex-1 py-3 bg-red-600 hover:bg-red-500 disabled:opacity-50 text-white rounded-xl font-semibold transition-all shadow-lg shadow-red-900/20"
                    >
                        {isLoading ? 'Excluindo...' : 'Confirmar'}
                    </button>
                </div>
            </div>
        </div>
    );
}