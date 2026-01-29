import { X } from 'lucide-react';
import { useEffect } from 'react';

interface ArtistaImageBoxProps {
    src: string | null;
    onClose: () => void;
}

export function ArtistaImageBox({ src, onClose }: ArtistaImageBoxProps) {
    useEffect(() => {
        if (src) document.body.style.overflow = 'hidden';
        const handleEsc = (e: KeyboardEvent) => e.key === 'Escape' && onClose();
        window.addEventListener('keydown', handleEsc);

        return () => {
            document.body.style.overflow = 'unset';
            window.removeEventListener('keydown', handleEsc);
        };
    }, [src, onClose]);

    if (!src) return null;

    return (
        <div
            className="fixed inset-0 z-[200] flex items-center justify-center bg-black/95 backdrop-blur-sm animate-in fade-in duration-300"
            onClick={onClose}
        >
            <button
                className="absolute top-6 right-6 p-3 bg-white/10 hover:bg-white/20 rounded-full text-white transition-all"
                onClick={onClose}
            >
                <X size={24} />
            </button>

            <img
                src={src}
                className="max-w-[90vw] max-h-[90vh] object-contain rounded-lg shadow-2xl animate-in zoom-in-95 duration-300"
                onClick={(e) => e.stopPropagation()}
            />
        </div>
    );
}