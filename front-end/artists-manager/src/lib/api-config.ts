export const API_BASE_URL =
  import.meta.env.VITE_API_URL || "http://localhost:8081";


export const API_ENDPOINTS = {
  // Auth
  login: "/auth/login",
  refresh: "/auth/refresh",

  // Artists
  artists: "/artista",
  artistById: (id: number) => `/artista/${id}`,

  // Albums
  albums: "/album",
  albumById: (id: number) => `/album/${id}`,
  albumsByArtist: (artistId: number) => `/artista/${artistId}/album`,

  // Album covers
  uploadCover: (albumId: number) => `/album/${albumId}/capa`,

  // WebSocket
  websocket: "/ws",
};

export const getApiUrl = (endpoint: string) => `${API_BASE_URL}${endpoint}`;
