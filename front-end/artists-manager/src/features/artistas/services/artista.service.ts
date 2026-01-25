import { httpClient } from "@/core/api/client";
import { API_ENDPOINTS } from "@/core/constants/api.constants";
import { PaginatedResponse } from "@/core/types/api.types";
import { Artista, ArtistaFormData } from "../types";

export const ArtistaService = {
  listAll: async (params?: any) => {
    const { data } = await httpClient.get<PaginatedResponse<Artista>>(
      API_ENDPOINTS.ARTISTAS.BASE,
      { params },
    );
    return data;
  },

  delete: async (id: string) => {
    await httpClient.delete(API_ENDPOINTS.ARTISTAS.BY_ID(id));
  },

  getDashboard: async (params: {
    page: number;
    perPage: number;
    search?: string;
  }) => {
    const { data } = await httpClient.get(API_ENDPOINTS.ARTISTAS.DASHBOARD, {
      params: {
        page: params.page,
        perPage: params.perPage,
        search: params.search,
        sort: "nome",
        dir: "asc",
      },
    });
    return data;
  },
  create: async (data: ArtistaFormData) => {
    const response = await httpClient.post<Artista>(
      API_ENDPOINTS.ARTISTAS.BASE,
      data,
    );
    return response.data;
  },
  update: async (id: string, data: ArtistaFormData) => {
    const response = await httpClient.put<Artista>(
      API_ENDPOINTS.ARTISTAS.BY_ID(id),
      data,
    );
    return response.data;
  },
};
