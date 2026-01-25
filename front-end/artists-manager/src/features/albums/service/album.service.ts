import { httpClient } from "@/core/api/client";
import { PaginatedResponse } from "@/core/types/api.types";
import {
  Album,
  AlbumDashboardItem,
  AlbumImage,
  CreateAlbumDTO,
} from "../types";

export const AlbumService = {
  listAll: async (params: {
    page: number;
    perPage: number;
    search?: string;
  }) => {
    const { data } = await httpClient.get<PaginatedResponse<Album>>("/album", {
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

  update: async (id: string, payload: CreateAlbumDTO) => {
    const { data } = await httpClient.put(`/album/${id}`, payload);
    return data;
  },

  delete: async (id: string) => {
    const { data } = await httpClient.delete(`/album/${id}`);
    return data;
  },
  uploadImages: async (albumId: string, files: File[]) => {
    const formData = new FormData();
    formData.append("albumId", albumId);

    files.forEach((file) => {
      formData.append("files", file);
    });

    const { data } = await httpClient.post("/album-imagem", formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
    return data;
  },
  deleteImage: async (albumImageId: string) => {
    await httpClient.delete(`/album-imagem/${albumImageId}`);
  },
};
