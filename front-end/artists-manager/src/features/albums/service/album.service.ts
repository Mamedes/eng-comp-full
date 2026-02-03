import { httpClient } from "@/core/api/client";
import { PaginatedResponse } from "@/core/types/api.types";
import {
  Album,
  AlbumDashboardItem,
  AlbumImage,
  CreateAlbumDTO,
} from "../types";
import { API_ENDPOINTS } from "@/core/constants/api.constants";

export const AlbumService = {
  listAll: async (params: {
    page: number;
    perPage: number;
    search?: string;
  }) => {
    const { data } = await httpClient.get<PaginatedResponse<Album>>(
      API_ENDPOINTS.ALBUMS.BASE,
      {
        params: {
          page: params.page,
          perPage: params.perPage,
          search: params.search,
          sort: "titulo",
          dir: "asc",
        },
      },
    );
    return data;
  },
  getDashboard: async (params: {
    page: number;
    perPage: number;
    search?: string;
  }) => {
    const { data } = await httpClient.get<
      PaginatedResponse<AlbumDashboardItem>
    >(`${API_ENDPOINTS.ALBUMS.DETAILS()}`, {
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
    const { data } = await httpClient.post(API_ENDPOINTS.ALBUMS.BASE, payload);
    return data;
  },

  getImages: async (albumId: string) => {
    const { data } = await httpClient.get<AlbumImage[]>(
      `${API_ENDPOINTS.ALBUMS_IMAGE.BASE}/album/${albumId}`,
    );
    return data;
  },

  update: async (id: string, payload: CreateAlbumDTO) => {
    const { data } = await httpClient.put(
      `${API_ENDPOINTS.ALBUMS.BASE}/${id}`,
      payload,
    );
    return data;
  },

  delete: async (id: string) => {
    const { data } = await httpClient.delete(
      `${API_ENDPOINTS.ALBUMS.BASE}/${id}`,
    );
    return data;
  },
  uploadImages: async (albumId: string, files: File[]) => {
    const formData = new FormData();
    formData.append("albumId", albumId);

    files.forEach((file) => {
      formData.append("files", file);
    });

    const { data } = await httpClient.post(API_ENDPOINTS.ALBUMS_IMAGE.BASE, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return data;
  },
  deleteImage: async (albumImageId: string) => {
    await httpClient.delete(
      `${API_ENDPOINTS.ALBUMS_IMAGE.BASE}/${albumImageId}`,
    );
  },
};
