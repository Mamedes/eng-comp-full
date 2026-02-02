import axios, { AxiosError, InternalAxiosRequestConfig } from "axios";
import { API_BASE_URL, API_ENDPOINTS } from "../constants/api.constants";
import { authActions } from "../auth/auth.store";

let isRefreshing = false;
let failedQueue: any[] = [];

const processQueue = (error: any, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) prom.reject(error);
    else prom.resolve(token);
  });
  failedQueue = [];
};

export const httpClient = axios.create({
  baseURL: API_BASE_URL,
  headers: { "Content-Type": "application/json" },
});

httpClient.interceptors.request.use((config) => {
  const token = localStorage.getItem("auth_token");
  if (token && config.headers) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

httpClient.interceptors.response.use(
  (response) => response,
  async (error: AxiosError) => {
    const originalRequest = error.config as InternalAxiosRequestConfig & {
      _retry?: boolean;
    };

    if (error.response?.status === 401 && !originalRequest._retry) {

      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            originalRequest.headers.Authorization = `Bearer ${token}`;
            return httpClient(originalRequest);
          })
          .catch((err) => Promise.reject(err));
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = localStorage.getItem("refresh_token");
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${API_BASE_URL}/auth/refresh-token`,
            {},
            {
              headers: {
                "refresh-token": refreshToken,
                "Content-Type": "application/json",
              },
            },
          )
          .then(({ data }) => {
            localStorage.setItem("auth_token", data.accessToken);
            localStorage.setItem("refresh_token", data.refreshToken);

            authActions.update({
              token: data.accessToken,
              isAuthenticated: true,
            });

            if (originalRequest.headers) {
              originalRequest.headers.Authorization = `Bearer ${data.accessToken}`;
            }

            processQueue(null, data.accessToken);
            resolve(httpClient(originalRequest));
          })
          .catch((err) => {
            processQueue(err, null);

            authActions.logout();

            if (typeof window !== "undefined") {
              window.location.href = "/login";
            }

            reject(err);
          })
          .finally(() => {
            isRefreshing = false;
          });
      });
    }

    return Promise.reject(error);
  },
);
