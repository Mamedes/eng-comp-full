import { describe, it, expect, vi, beforeEach } from 'vitest';
import { ArtistaService } from './artista.service';
import { httpClient } from '@/core/api/client';

vi.mock('@/core/api/client', () => ({
  httpClient: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
  },
}));

describe('ArtistaService', () => {
  it('deve chamar o dashboard com os parâmetros corretos', async () => {
    const mockData = { items: [], total: 0 };
    vi.mocked(httpClient.get).mockResolvedValue({ data: mockData });

    const params = { page: 0, perPage: 10, sort: 'nome', dir: 'asc' };
    const result = await ArtistaService.getDashboard(params);

    expect(httpClient.get).toHaveBeenCalledWith(expect.stringContaining('/dashboard'), {
      params: expect.objectContaining({ page: 0, sort: 'nome' })
    });
    expect(result).toEqual(mockData);
  });

  it('deve buscar detalhes do artista pelo ID', async () => {
    const mockDetails = { artistaId: '123', nome: 'Artista Teste', albuns: [] };
    vi.mocked(httpClient.get).mockResolvedValue({ data: mockDetails });

    const result = await ArtistaService.getDetails('123');

    expect(httpClient.get).toHaveBeenCalledWith(expect.stringContaining('/123/detalhe'));
    expect(result.nome).toBe('Artista Teste');
  });
});