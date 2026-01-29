import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { ChevronLeft, Music, Calendar, Disc, ZoomIn } from 'lucide-react';
import { artistaDetalheFacade } from '../facades/artista-detalhe.facade';
import { useObservable } from '@/shared/hooks/use-observable';
import { ArtistaImageBox } from './componentes/artista-image-box';

export default function ArtistDetailPage() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { artista, isLoading } = useObservable(artistaDetalheFacade.state$, {
    artista: null,
    isLoading: true
  });
  const [selectedImage, setSelectedImage] = useState<string | null>(null);

  useEffect(() => {
    if (id) artistaDetalheFacade.loadArtistaDetails(id);
    return () => artistaDetalheFacade.clear();
  }, [id]);

  if (isLoading) {
    return <div className="flex justify-center py-20"><Disc className="animate-spin text-purple-500" size={40} /></div>;
  }

  if (!artista) return <div className="text-center py-20 text-zinc-500">Artista não encontrado.</div>;

  return (
    <div className="p-8 space-y-8 min-h-screen bg-zinc-950 text-zinc-100">
      <ArtistaImageBox
        src={selectedImage}
        onClose={() => setSelectedImage(null)}
      />

      <button
        onClick={() => navigate(-1)}
        className="flex items-center gap-2 text-zinc-400 hover:text-white transition-colors mb-4"
      >
        <ChevronLeft size={20} /> Voltar
      </button>


      <section>
        <h2 className="text-2xl font-bold text-white mb-6 flex items-center gap-2">
          Álbuns <span className="text-zinc-600 text-lg">({artista.albuns.length})</span>
        </h2>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          {artista.albuns.map((album) => (
            <div key={album.albumId} className="bg-zinc-900/40 border border-white/5 rounded-3xl p-6 space-y-4">
              <div className="flex justify-between items-center">
                <h3 className="text-xl font-bold text-white">{album.titulo}</h3>
                <span className="text-xs text-zinc-500 bg-zinc-800 px-2 py-1 rounded">
                  {album.imagens.length} foto(s)
                </span>
              </div>

              {album.imagens.length === 0 ? (
                <div className="aspect-video bg-zinc-800/50 rounded-2xl flex items-center justify-center text-zinc-600 border border-dashed border-white/10">
                  <Disc size={40} className="mr-2" /> Sem capa
                </div>
              ) : (
                <div className="grid grid-cols-2 sm:grid-cols-3 gap-3">
                  {album.imagens.map((img) => (
                    <div
                      key={img.albumImagemId}
                      onClick={() => setSelectedImage(img.linkTemporario)} 
                      className="group relative aspect-square overflow-hidden rounded-xl bg-zinc-800 border border-white/5 cursor-zoom-in"
                    >
                      <img
                        src={img.linkTemporario}
                        alt={img.fileName}
                        className="w-full h-full object-cover transition-transform duration-500 group-hover:scale-110"
                      />

                      <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center">
                        <ZoomIn className="text-white opacity-70" size={32} />
                      </div>

                      <div className="absolute bottom-0 left-0 right-0 p-2 bg-gradient-to-t from-black/80 to-transparent">
                        <p className="text-[10px] text-white truncate">{img.fileName}</p>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}