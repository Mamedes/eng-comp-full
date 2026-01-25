import { httpClient } from "@/core/api/client";
import {
  AlbumDashboardItem,
  AlbumFilters,
  AlbumImage,
  CreateAlbumDTO,
} from "../types";
import { PaginatedResponse } from "@/core/types/api.types";

export const AlbumService = {
  listAll: async (params: {
    page: number;
    perPage: number;
    search?: string;
  }) => {
    const { data } = await httpClient.get<
      PaginatedResponse<AlbumDashboardItem>
    >("/album", {
      params: {
        page: params.page,
        perPage: params.perPage,
        search: params.search,
        sort: "titulo",
        dir: "asc",
      },
    });
    return data;
  },
  getDashboard: async (params: {
    page: number;
    perPage: number;
    search?: string;
  }) => {
    const { data } = await httpClient.get<
      PaginatedResponse<AlbumDashboardItem>
    >("/album/details", {
      params: {
        page: params.page,
        perPage: params.perPage,
        search: params.search,
        sort: "titulo",
        dir: "asc",
      },
    });
    return data;
  },

  create: async (payload: CreateAlbumDTO) => {
    const { data } = await httpClient.post("/album", payload);
    return data;
  },

  getImages: async (albumId: string) => {
    const { data } = await httpClient.get<AlbumImage[]>(
      `/album-imagem/album/${albumId}`,
    );
    return data;
  },
};
