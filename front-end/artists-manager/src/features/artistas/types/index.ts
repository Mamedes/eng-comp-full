export type ArtistaType = "SOLO" | "BANDA" | "DUPLA";

export interface Artista {
  artistaId: string;
  nome: string;
  tipo: ArtistaType;
}

export interface ArtistaDashboard {
  artistaId: string;
  nome: string;
  tipo: string;
  quantidadeAlbuns: number;
}

export interface ArtistaFormData {
  nome: string;
  tipo: ArtistaType;
}
