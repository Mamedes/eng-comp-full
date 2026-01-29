import { toast } from "react-toastify";
import { ArtistaService } from "../services/artista.service";
import {
  artistaDetalheActions,
  artistaDetalheState$,
} from "../state/artista-detalhe.store";

export const artistaDetalheFacade = {
  state$: artistaDetalheState$.asObservable(),

  async loadArtistaDetails(id: string) {
    artistaDetalheActions.setLoading(true);
    try {
      const data = await ArtistaService.getDetails(id);
      artistaDetalheActions.setArtista(data);
    } catch (error) {
      console.error(error);
      artistaDetalheActions.setLoading(false);
      toast.error("Erro ao carregar detalhes do artista");
    }
  },

  clear() {
    artistaDetalheActions.clear();
  },
};
