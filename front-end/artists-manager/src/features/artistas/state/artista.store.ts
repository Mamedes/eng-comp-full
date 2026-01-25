import { BehaviorSubject } from "rxjs";
import { PaginatedResponse } from "@/core/types/api.types";
import { ArtistaDashboard } from "../types";

export interface ArtistaState {
  data: ArtistaDashboard[];
  pagination: {
    page: number;
    perPage: number;
    total: number;
    totalPages: number;
  };
  isLoading: boolean;
  search: string;
}

const initialState: ArtistaState = {
  data: [],
  pagination: {
    page: 0,
    perPage: 10,
    total: 0,
    totalPages: 0,
  },
  isLoading: false,
  search: "",
};

export const artistaState$ = new BehaviorSubject<ArtistaState>(initialState);

export const artistaActions = {
  setLoading: (isLoading: boolean) =>
    artistaState$.next({ ...artistaState$.value, isLoading }),

  setData: (response: any) => {
    const { items, total, perPage, currentPage } = response;
    const calculatedTotalPages = Math.ceil((total || 0) / (perPage || 10));

    artistaState$.next({
      ...artistaState$.value,
      data: items || [],
      pagination: {
        page: currentPage || 0,
        perPage: perPage || 10,
        total: total || 0,
        totalPages: calculatedTotalPages,
      },
      isLoading: false,
    });
  },
  setSearch: (search: string) =>
    artistaState$.next({
      ...artistaState$.value,
      search,
      pagination: { ...artistaState$.value.pagination, page: 0 },
    }),

  setPage: (page: number) =>
    artistaState$.next({
      ...artistaState$.value,
      pagination: { ...artistaState$.value.pagination, page },
    }),
};
