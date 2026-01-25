import { authActions, authState$ } from "./auth.store";
import { httpClient } from "../api/client";
import { API_ENDPOINTS } from "../constants/api.constants";
import { LoginCredentials, AuthResponse } from "../types/api.types";
import { mapAuthError } from "./auth.mapper";

export const authFacade = {
  state$: authState$.asObservable(),

  async login(credentials: LoginCredentials): Promise<void> {
    authActions.update({ isAuthLoading: true });

    try {
      const { data } = await httpClient.post<AuthResponse>(
        API_ENDPOINTS.AUTH.LOGIN,
        credentials,
      );

      localStorage.setItem("auth_token", data.accessToken);

      authActions.update({
        token: data.accessToken,
        isAuthenticated: true,
        isAuthLoading: false,
      });
    } catch (error) {
      authActions.update({ isAuthLoading: false });
      throw mapAuthError(error);
    }
  },

  logout() {
    authActions.logout();
  },
};
