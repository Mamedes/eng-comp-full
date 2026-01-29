import { BehaviorSubject } from "rxjs";
import { ArtistaDetalhe } from "../types";

export interface ArtistaDetalheState {
  artista: ArtistaDetalhe | null;
  isLoading: boolean;
}

const initialState: ArtistaDetalheState = {
  artista: null,
  isLoading: false,
};

export const artistaDetalheState$ = new BehaviorSubject<ArtistaDetalheState>(
  initialState,
);

export const artistaDetalheActions = {
  setLoading: (isLoading: boolean) =>
    artistaDetalheState$.next({ ...artistaDetalheState$.value, isLoading }),

  setArtista: (artista: ArtistaDetalhe) =>
    artistaDetalheState$.next({ artista, isLoading: false }),

  clear: () => artistaDetalheState$.next(initialState),
};
