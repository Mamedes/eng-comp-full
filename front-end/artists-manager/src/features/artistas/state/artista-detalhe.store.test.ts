import { describe, it, expect } from 'vitest';
import { artistaDetalheActions, artistaDetalheState$ } from './artista-detalhe.store';

describe('ArtistaDetalheStore', () => {
  it('deve limpar o estado ao chamar clear', () => {
    artistaDetalheActions.setArtista({ nome: 'Maluco Beleza' } as any);
    expect(artistaDetalheState$.value.artista).not.toBeNull();

    artistaDetalheActions.clear();
    expect(artistaDetalheState$.value.artista).toBeNull();
    expect(artistaDetalheState$.value.isLoading).toBe(false);
  });
});