import { BehaviorSubject } from "rxjs";

export interface AuthState {
  token: string | null;
  isAuthenticated: boolean;
  isAuthLoading: boolean;
  isAppLoading: boolean;
}

export const initialAuthState: AuthState = {
  token: localStorage.getItem("auth_token"),
  isAuthenticated: !!localStorage.getItem("auth_token"),
  isAuthLoading: false,
  isAppLoading: false,
};
export const authState$ = new BehaviorSubject<AuthState>(initialAuthState);

export const authActions = {
  update(state: Partial<AuthState>) {
    const current = authState$.value;
    authState$.next({ ...current, ...state });
  },
  logout() {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("refresh_token");
    authState$.next({
      token: null,
      isAuthenticated: false,
      isAuthLoading: false,
      isAppLoading: false,
    });
  },
};
