import { toast } from "react-toastify";
import { BehaviorSubject } from "rxjs";
import { AlbumService } from "../service/album.service";
import { Album, CreateAlbumImagemDTO } from "../types";

interface AlbumState {
  data: Album[];
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
  async deleteAlbum(id: string): Promise<boolean> {
    try {
      await AlbumService.delete(id);
      toast.success("Album removido!");
      this.loadAlbums();
      return true;
    } catch (error) {
      console.error(error);
      toast.error("Erro ao excluir album");
      return false;
    }
  },
  async getAlbumImages(albumId: string) {
    try {
      return await AlbumService.getImages(albumId);
    } catch (error) {
      console.error(error);
      return [];
    }
  },
  async uploadImagens(data: CreateAlbumImagemDTO): Promise<boolean> {
    try {
      await AlbumService.uploadImages(data.albumId, Array.from(data.files));
      toast.success(`Imagens enviadas com sucesso!`);
      this.loadAlbums();
      return true;
    } catch (error) {
      toast.error("Erro ao enviar imagens");
      return false;
    }
  },
  async deleteAlbumImage(imageId: string): Promise<boolean> {
    try {
      await AlbumService.deleteImage(imageId);
      toast.success("Imagem removida.");
      return true;
    } catch (error) {
      console.error(error);
      toast.error("Erro ao remover imagem.");
      return false;
    }
  },
};
