import { healthState$, healthActions } from "../state/health.store";
import { HealthService } from "../services/health.service";
import { toast } from "react-toastify/unstyled";

export const healthFacade = {
  state$: healthState$.asObservable(),

  async checkStatus() {
    healthActions.setLoading(true);
    try {
      const status = await HealthService.getSystemStatus();
      healthActions.setData(status);
    } catch (error) {
      healthActions.setLoading(false);
      toast.error("Erro ao verificar o status do sistema.");
    }
  },
};
