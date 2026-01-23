export interface Artist {
  id: number;
  nome: string;
  totalAlbuns?: number;
  albuns?: Album[];
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

export interface Artist {
  artistaId: string;
  nome: string;
  tipo: string;
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
