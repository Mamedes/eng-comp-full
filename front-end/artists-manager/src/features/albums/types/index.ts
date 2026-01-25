export interface Album {
  albumId: string;
  albumTitulo: string;
  artistaIds: string[];
}

export interface AlbumDashboardItem {
  albumId: string;
  albumTitulo: string;
  artistaNome: string;
  artistaTipo: string;
}

export interface AlbumImage {
  albumImagemId: string;
  fileName: string;
  linkTemporario: string;
  createdAt: string;
}

export interface CreateAlbumDTO {
  albumTitulo: string;
  artistaIds: string[];
}

export interface AlbumFilters {
  page: number;
  perPage: number;
  sort: string;
  dir: "asc" | "desc";
  search?: string;
}

export interface CreateAlbumImagemDTO {
  albumId: string;
  files: File[];
}
