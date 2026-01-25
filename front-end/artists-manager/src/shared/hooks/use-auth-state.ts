import { useObservable } from "@/shared/hooks/use-observable";
import { authFacade } from "@/core/auth/auth.facade";
import { initialAuthState } from "@/core/auth/auth.store";

export function useAuth() {
  const state = useObservable(authFacade.state$, initialAuthState);

  return {
    token: state.token,
    isAuthenticated: state.isAuthenticated,
    login: authFacade.login,
    logout: authFacade.logout,
  };
}
