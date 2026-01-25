export const API_BASE_URL =
  import.meta.env.VITE_API_URL || "http://localhost:8081";

const ARTISTA_BASE = "/artista";
const ALBUM_BASE = "/album";

export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: "/auth/login",
    REFRESH: "/auth/refresh",
  },
  ARTISTAS: {
    BASE: ARTISTA_BASE,
    DASHBOARD: `${ARTISTA_BASE}/dashboard`,
    BY_ID: (id: number | string) => `${ARTISTA_BASE}/${id}`,
    ALBUMS: (artistaId: number | string) =>
      `${ARTISTA_BASE}/${artistaId}/album`,
  },
  ALBUMS: {
    BASE: ALBUM_BASE,
    BY_ID: (id: number | string) => `${ALBUM_BASE}/${id}`,
    UPLOAD_COVER: (albumId: number | string) => `${ALBUM_BASE}/${albumId}/capa`,
  },
  SOCKET: {
    ENDPOINT: `${API_BASE_URL}/ws-seletivo`,
  },
} as const;

export type ApiEndpoints = typeof API_ENDPOINTS;
