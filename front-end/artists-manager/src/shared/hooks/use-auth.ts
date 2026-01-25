import { authFacade } from "@/core/auth/auth.facade";
import { useObservable } from "./use-observable";
import { initialAuthState } from "@/core/auth/auth.store";

export function useAuth() {
  const state = useObservable(authFacade.state$, initialAuthState);
  return {
    isAuthenticated: state.isAuthenticated,
    isAppLoading: state.isAppLoading,
    isAuthLoading: state.isAuthLoading,
  };
}
