export interface PaginatedResponse<T> {
  items: T[];
  total: number;
  perPage: number;
  currentPage: number;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
}

export interface LoginCredentials {
  username: string;
  password: string;
}
