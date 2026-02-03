import { describe, it, expect, beforeEach, afterAll, afterEach, vi } from "vitest";
import { setupServer } from "msw/node";
import { http, HttpResponse } from "msw";
import { httpClient } from "./client";
import { API_BASE_URL, API_ENDPOINTS } from "../constants/api.constants";

const server = setupServer();

beforeEach(() => {
  server.listen();
  localStorage.clear();
  vi.clearAllMocks();
  vi.unstubAllGlobals();
});

afterEach(() => server.resetHandlers());
afterAll(() => server.close());

describe("httpClient Interceptors", () => {
  it("deve renovar o token automaticamente em caso de 401 e repetir a requisição", async () => {
    server.use(
      http.get(`${API_BASE_URL}${API_ENDPOINTS.ARTISTAS.DASHBOARD}`, ({ request }) => {
        if (request.headers.get("Authorization") === "Bearer old-token") {
          return new HttpResponse(null, { status: 401 });
        }
        return HttpResponse.json({ items: [{ nome: "Aerosmith" }] });
      }),
      http.post(`${API_BASE_URL}${API_ENDPOINTS.AUTH.REFRESH}`, () => {
        return HttpResponse.json({
          accessToken: "new-token",
          refreshToken: "new-refresh",
        });
      })
    );

    localStorage.setItem("auth_token", "old-token");
    localStorage.setItem("refresh_token", "valid-refresh");

    const response = await httpClient.get(API_ENDPOINTS.ARTISTAS.DASHBOARD);

    expect(response.status).toBe(200);
    expect(localStorage.getItem("auth_token")).toBe("new-token");
  });

  
});