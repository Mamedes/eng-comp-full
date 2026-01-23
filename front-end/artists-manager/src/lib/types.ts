export type ArtistType = "SOLO" | "BANDA" | "DUPLA";
export interface Artist {
  artistaId: string;
  nome: string;
  tipo: ArtistType;
}
export interface ArtistFormData {
  nome: string;
  tipo: ArtistType;
}

export interface Album {
  id: number;
  nome: string;
  artistaId: number;
  capas?: AlbumCover[];
}

export interface AlbumCover {
  id: number;
  url: string;
  albumId: number;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
  first: boolean;
  last: boolean;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  expiresIn?: number;
}

export interface LoginCredentials {
  username: string;
  password: string;
}

export interface ArtistFormData {
  nome: string;
}

export interface AlbumFormData {
  nome: string;
  artistaId: number;
}
export interface PaginatedResponse<T> {
  currentPage: number;
  perPage: number;
  total: number;
  items: T[];
}

export interface ArtistaDashboard {
  artistaId: string;
  nome: string;
  tipo: string;
  quantidadeAlbuns: number;
}

export interface ArtistFormData {
  nome: string;
  tipo: string;
}
export interface GetArtistsParams {
  search?: string;
  page?: number;
  perPage?: number;
  sort?: string;
  dir?: "asc" | "desc";
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
