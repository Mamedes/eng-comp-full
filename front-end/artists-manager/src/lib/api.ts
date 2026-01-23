import { API_ENDPOINTS, getApiUrl } from "./api-config";
import type {
  Artist,
  ArtistaDashboard,
  Album,
  PaginatedResponse,
  ArtistFormData,
  AlbumFormData,
} from "./types";

// Generic fetch wrapper
async function fetchApi<T>(
  endpoint: string,
  options: RequestInit = {},
  token?: string | null,
): Promise<T> {
  const headers: HeadersInit = {
    "Content-Type": "application/json",
    ...options.headers,
  };

  if (token) {
    (headers as Record<string, string>)["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch(getApiUrl(endpoint), {
    ...options,
    headers,
  });

  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || `HTTP error! status: ${response.status}`);
  }

  // Handle empty responses
  const text = await response.text();
  return text ? JSON.parse(text) : null;
}

// Artists API
export interface GetArtistsParams {
  search?: string;
  page?: number;
  perPage?: number;
  sort?: string;
  dir?: "asc" | "desc";
}

export async function getArtists(
  params: GetArtistsParams = {},
  token?: string | null,
): Promise<PaginatedResponse<Artist>> {
  const searchParams = new URLSearchParams();
  if (params.search) searchParams.set("search", params.search);
  searchParams.set("page", (params.page || 0).toString());
  searchParams.set("perPage", (params.perPage || 10).toString());
  searchParams.set("sort", params.sort || "quantidadeAlbuns");
  searchParams.set("dir", params.dir || "desc");

  return fetchApi<PaginatedResponse<Artist>>(
    `${API_ENDPOINTS.artistsDashboard}?${searchParams.toString()}`,
    {},
    token,
  );
}

export async function getArtistsDashboard(
  params: GetArtistsParams = {},
  token?: string | null,
): Promise<PaginatedResponse<ArtistaDashboard>> {
  const searchParams = new URLSearchParams();
  searchParams.set("search", params.search || "");
  searchParams.set("page", (params.page || 0).toString());
  searchParams.set("perPage", (params.perPage || 10).toString());
  searchParams.set("sort", params.sort || "quantidadeAlbuns");
  searchParams.set("dir", params.dir || "desc");

  return fetchApi<PaginatedResponse<Artist>>(
    `/artista/dashboard?${searchParams.toString()}`,
    {},
    token,
  );
}

export async function getArtistById(
  id: number,
  token?: string | null,
): Promise<Artist> {
  return fetchApi<Artist>(API_ENDPOINTS.artistById(id), {}, token);
}

export async function createArtist(
  data: ArtistFormData,
  token?: string | null,
): Promise<Artist> {
  return fetchApi<Artist>(
    API_ENDPOINTS.artists,
    {
      method: "POST",
      body: JSON.stringify(data),
    },
    token,
  );
}

export async function updateArtist(
  id: number,
  data: ArtistFormData,
  token?: string | null,
): Promise<Artist> {
  return fetchApi<Artist>(
    API_ENDPOINTS.artistById(id),
    {
      method: "PUT",
      body: JSON.stringify(data),
    },
    token,
  );
}

// Albums API
export async function getAlbumsByArtist(
  artistId: number,
  params: { page?: number; perPage?: number } = {},
  token?: string | null,
): Promise<PaginatedResponse<Album>> {
  const searchParams = new URLSearchParams();

  if (params.page !== undefined)
    searchParams.set("page", params.page.toString());
  if (params.perPage !== undefined)
    searchParams.set("perPage", params.perPage.toString());

  const queryString = searchParams.toString();
  const endpoint = `${API_ENDPOINTS.albumsByArtist(artistId)}${queryString ? `?${queryString}` : ""}`;

  return fetchApi<PaginatedResponse<Album>>(endpoint, {}, token);
}

export async function getAlbumById(
  id: number,
  token?: string | null,
): Promise<Album> {
  return fetchApi<Album>(API_ENDPOINTS.albumById(id), {}, token);
}

export async function createAlbum(
  data: AlbumFormData,
  token?: string | null,
): Promise<Album> {
  return fetchApi<Album>(
    API_ENDPOINTS.albums,
    {
      method: "POST",
      body: JSON.stringify(data),
    },
    token,
  );
}

export async function updateAlbum(
  id: number,
  data: Partial<AlbumFormData>,
  token?: string | null,
): Promise<Album> {
  return fetchApi<Album>(
    API_ENDPOINTS.albumById(id),
    {
      method: "PUT",
      body: JSON.stringify(data),
    },
    token,
  );
}

// Album Cover Upload
export async function uploadAlbumCover(
  albumId: number,
  files: File[],
  token?: string | null,
): Promise<Album> {
  const formData = new FormData();
  files.forEach((file) => {
    formData.append("files", file);
  });

  const headers: HeadersInit = {};
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  const response = await fetch(getApiUrl(API_ENDPOINTS.uploadCover(albumId)), {
    method: "POST",
    headers,
    body: formData,
  });

  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || `Upload failed! status: ${response.status}`);
  }

  return response.json();
}
