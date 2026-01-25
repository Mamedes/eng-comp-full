import axios from "axios";
import { AuthError } from "./auth.error";

export function mapAuthError(error: unknown): AuthError {
  if (axios.isAxiosError(error)) {
    const status = error.response?.status;
    const message =
      error.response?.data?.message ?? "Erro ao autenticar usuário";

    return new AuthError(message, status);
  }

  return new AuthError("Erro inesperado ao autenticar");
}
