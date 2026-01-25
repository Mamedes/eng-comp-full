import { BehaviorSubject } from "rxjs";
import { toast } from "react-toastify";
import { AlbumService } from "../service/album.service";
import { AlbumDashboardItem } from "../types";

interface AlbumState {
  data: AlbumDashboardItem[];
  total: number;
  isLoading: boolean;
  search: string;
  page: number;
}

const albumState$ = new BehaviorSubject<AlbumState>({
  data: [],
  total: 0,
  search: "",
  isLoading: false,
  page: 0,
});

export const albumFacade = {
  state$: albumState$.asObservable(),
  updateSearch(search: string) {
    albumState$.next({ ...albumState$.value, search, page: 0 });
    this.loadAlbums();
  },

  async loadAlbums() {
    const state = albumState$.value;
    albumState$.next({ ...state, isLoading: true });

    try {
      const response = await AlbumService.listAll({
        page: state.page,
        perPage: 8,
        search: state.search,
      });

      albumState$.next({
        ...albumState$.value,
        data: response.items,
        total: response.total,
        isLoading: false,
      });
    } catch (error) {
      toast.error("Erro ao carregar álbuns");
      albumState$.next({ ...albumState$.value, isLoading: false });
    }
  },

  setPage(page: number) {
    albumState$.next({ ...albumState$.value, page });
  },
  async getAlbumImages(albumId: string) {
    try {
      return await AlbumService.getImages(albumId);
    } catch (error) {
      toast.error("Erro ao carregar imagens do álbum");
      return [];
    }
  },
};
