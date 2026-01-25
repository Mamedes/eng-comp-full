import { useState, ChangeEvent } from 'react';
import { ImagePlus, X, UploadCloud, Loader2 } from 'lucide-react';
import { albumFacade } from '../facades/album.facade';

interface Props {
    albumId: string;
    onSuccess?: () => void;
}

export function ImageUploadSection({ albumId, onSuccess }: Props) {
    const [selectedFiles, setSelectedFiles] = useState<File[]>([]);
    const [previews, setPreviews] = useState<string[]>([]);
    const [isUploading, setIsUploading] = useState(false);

    const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            const filesArray = Array.from(e.target.files);
            setSelectedFiles(prev => [...prev, ...filesArray]);

            const newPreviews = filesArray.map(file => URL.createObjectURL(file));
            setPreviews(prev => [...prev, ...newPreviews]);
        }
    };

    const removeImage = (index: number) => {
        URL.revokeObjectURL(previews[index]);

        setSelectedFiles(prev => prev.filter((_, i) => i !== index));
        setPreviews(prev => prev.filter((_, i) => i !== index));
    };

    const handleSaveImages = async () => {
        if (selectedFiles.length === 0) return;
        setIsUploading(true);

        const success = await albumFacade.uploadImagens({
            albumId,
            files: selectedFiles
        });

        if (success) {
            previews.forEach(url => URL.revokeObjectURL(url));
            setSelectedFiles([]);
            setPreviews([]);
            onSuccess?.();
        }
        setIsUploading(false);
    };

    return (
        <div className="space-y-6">
            <label className="flex flex-col items-center justify-center w-full h-40 border-2 border-dashed border-zinc-800 rounded-2xl cursor-pointer hover:bg-zinc-900/50 hover:border-purple-500/50 transition-all group">
                <div className="flex flex-col items-center justify-center pt-5 pb-6">
                    <div className="p-3 bg-zinc-900 rounded-full mb-3 group-hover:scale-110 transition-transform">
                        <ImagePlus className="w-6 h-6 text-zinc-500 group-hover:text-purple-500 transition-colors" />
                    </div>
                    <p className="text-sm text-zinc-400 font-medium">Clique para selecionar fotos</p>
                    <p className="text-xs text-zinc-600 mt-1">Suporta seleção múltipla</p>
                </div>
                <input
                    type="file"
                    multiple
                    accept="image/*"
                    className="hidden"
                    onChange={handleFileChange}
                />
            </label>

            {previews.length > 0 && (
                <div className="grid grid-cols-3 sm:grid-cols-4 gap-4 animate-in fade-in slide-in-from-bottom-2">
                    {previews.map((url, index) => (
                        <div key={index} className="group relative aspect-square rounded-xl overflow-hidden bg-zinc-800 border border-white/5 shadow-lg">
                            <img src={url} className="w-full h-full object-cover" alt="Preview" />
                            <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
                                <button
                                    onClick={() => removeImage(index)}
                                    className="p-2 bg-red-500/80 hover:bg-red-500 rounded-full text-white transition-transform hover:scale-110"
                                >
                                    <X size={16} />
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            )}

            {selectedFiles.length > 0 && (
                <button
                    onClick={handleSaveImages}
                    disabled={isUploading}
                    className="w-full py-3 bg-purple-600 hover:bg-purple-500 disabled:opacity-50 text-white rounded-xl font-bold flex items-center justify-center gap-2 shadow-lg shadow-purple-900/20 transition-all"
                >
                    {isUploading ? (
                        <>
                            <Loader2 className="animate-spin" /> Enviando...
                        </>
                    ) : (
                        <><UploadCloud size={20} /> Salvar {selectedFiles.length} Imagens</>
                    )}
                </button>
            )}
        </div>
    );
}