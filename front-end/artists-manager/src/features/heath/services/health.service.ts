import { httpClient } from "@/core/api/client";
import { API_ENDPOINTS } from "@/core/constants/api.constants";
import { HealthStatus } from "../types/health.types";

export const HealthService = {
  getSystemStatus: async (): Promise<HealthStatus> => {
    const { data } = await httpClient.get<HealthStatus>(
      API_ENDPOINTS.HEALTH.STATUS,
    );
    return data;
  },
};
