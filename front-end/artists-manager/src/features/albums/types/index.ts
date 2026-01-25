export interface Album {
  secure_id: string;
  titulo: string;
  artistas_ids: string[];
}

export interface AlbumDashboardItem {
  albumId: string;
  albumTitulo: string;
  artistaNome: string;
  artistaTipo: string;
}

export interface AlbumImage {
  secureId: string;
  fileName: string;
  linkTemporario: string;
  createdAt: string;
}

export interface CreateAlbumDTO {
  titulo: string;
  artistas_ids: string[];
}

export interface AlbumFilters {
  page: number;
  perPage: number;
  sort: string;
  dir: "asc" | "desc";
  search?: string;
}
