import {
  debounceTime,
  distinctUntilChanged,
} from "rxjs/operators";
import { Subject } from "rxjs";
import { artistaActions, artistaState$ } from "../state/artista.store";
import { ArtistaService } from "../services/artista.service";
import { toast } from "react-toastify";
import { ArtistaFormData } from "../types";

const searchSubject = new Subject<string>();

searchSubject
  .pipe(debounceTime(500), distinctUntilChanged())
  .subscribe((searchTerm) => {
    artistaActions.setSearch(searchTerm);
    artistaFacade.loadArtistas();
  });

export const artistaFacade = {
  state$: artistaState$.asObservable(),

  updateSearch(term: string) {
    searchSubject.next(term);
  },

  changePage(page: number) {
    const currentState = artistaState$.value;
    if (
      page < 0 ||
      (currentState.pagination.total > 0 &&
        page >= currentState.pagination.totalPages)
    )
      return;

    artistaActions.setPage(page);
    this.loadArtistas();
  },

  async loadArtistas() {
    const state = artistaState$.value;
    artistaActions.setLoading(true);

    try {
      const { page, perPage } = state.pagination;
      const { search } = state;

      const response = await ArtistaService.getDashboard({
        page,
        perPage,
        search: search,
      });

      artistaActions.setData(response);
    } catch (error) {
      console.error(error);
      toast.error("Erro ao carregar artistas");
      artistaActions.setLoading(false);
    }
  },

  async deleteArtista(id: string): Promise<boolean> {
    try {
      await ArtistaService.delete(id);
      toast.success("Artista removido!");
      this.loadArtistas();
      return true;
    } catch (error) {
      console.error(error);
      toast.error("Erro ao excluir artista");
      return false;
    }
  },

  async saveArtista(data: ArtistaFormData, id?: string) {
    artistaActions.setLoading(true);
    try {
      if (id) {
        await ArtistaService.update(id, data);
        toast.success("Artista atualizado com sucesso!");
      } else {
        await ArtistaService.create(data);
        toast.success("Artista criado com sucesso!");
      }

      this.loadArtistas();
      return true;
    } catch (error) {
      console.error(error);
      toast.error("Erro ao salvar artista");
      return false;
    } finally {
      artistaActions.setLoading(false);
    }
  },
};
