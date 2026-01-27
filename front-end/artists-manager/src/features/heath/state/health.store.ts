import { BehaviorSubject } from "rxjs";
import { HealthStatus } from "../types/health.types";

export interface HealthState {
  data: HealthStatus | null;
  isLoading: boolean;
}

const initialState: HealthState = {
  data: null,
  isLoading: false,
};

export const healthState$ = new BehaviorSubject<HealthState>(initialState);

export const healthActions = {
  setLoading: (isLoading: boolean) =>
    healthState$.next({
      ...healthState$.value,
      isLoading,
    }),

  setData: (data: HealthStatus) =>
    healthState$.next({
      ...healthState$.value,
      data,
      isLoading: false,
    }),

  reset: () => healthState$.next(initialState),
};
