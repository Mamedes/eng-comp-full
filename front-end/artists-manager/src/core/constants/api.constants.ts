export const API_BASE_URL =
  import.meta.env.VITE_API_URL || "http://localhost:8081";

const ARTISTA_BASE = "/v1/artista";
const ALBUM_BASE = "/v1/album";
const AUTH_BASE = "/v1/auth";

export const API_ENDPOINTS = {
  AUTH: {
    LOGIN: `${AUTH_BASE}/login`,
    REFRESH: `${AUTH_BASE}/refresh-token`,
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
    ENDPOINT: `${API_BASE_URL}/v1/ws-seletivo`,
  },
  HEALTH: {
    STATUS: "/actuator/health",
  },
} as const;

export type ApiEndpoints = typeof API_ENDPOINTS;
