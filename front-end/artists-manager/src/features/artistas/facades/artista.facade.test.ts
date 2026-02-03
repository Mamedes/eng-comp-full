import { describe, it, expect, vi, beforeEach } from "vitest";
import { artistaFacade } from "./artista.facade";
import { ArtistaService } from "../services/artista.service";
import { artistaState$ } from "../state/artista.store";

vi.mock("../services/artista.service");

describe("ArtistaFacade", () => {
  beforeEach(() => {
    vi.clearAllMocks();

    artistaState$.next({
      data: [],
      pagination: { page: 0, perPage: 10, total: 0, totalPages: 0 },
      isLoading: false,
      search: "",
      sort: "nome",
      dir: "asc",
    });

    vi.mocked(ArtistaService.getDashboard).mockResolvedValue({
      items: [],
      total: 0,
      perPage: 10,
      currentPage: 0,
    });
  });

  it("deve alternar a direção da ordenação (ASC/DESC)", async () => {
    await artistaFacade.updateSorting("nome");
    expect(artistaState$.value.dir).toBe("desc");

    await artistaFacade.updateSorting("nome");
    expect(artistaState$.value.dir).toBe("asc");
  });
});
