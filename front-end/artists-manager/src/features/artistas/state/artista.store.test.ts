import { describe, it, expect } from "vitest";
import { artistaActions, artistaState$ } from "./artista.store";

describe("ArtistaStore", () => {
  it("deve atualizar o status de loading", () => {
    artistaActions.setLoading(true);
    expect(artistaState$.value.isLoading).toBe(true);

    artistaActions.setLoading(false);
    expect(artistaState$.value.isLoading).toBe(false);
  });

  it("deve calcular corretamente o total de páginas ao setar dados", () => {
    const mockData = {
      items: [{}, {}],
      total: 20,
      perPage: 5,
      currentPage: 0,
    };

    artistaActions.setData(mockData);

    expect(artistaState$.value.pagination.totalPages).toBe(4); 
    expect(artistaState$.value.data).toHaveLength(2);
  });
});
