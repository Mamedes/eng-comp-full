import '@testing-library/jest-dom/vitest';
import { act, render, screen } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import { BehaviorSubject } from 'rxjs';
import { beforeEach, describe, expect, it, vi } from 'vitest';


vi.mock('../facades/artista-detalhe.facade', () => {
  const subject = new BehaviorSubject({
    artista: null,
    isLoading: true
  });

  return {
    artistaDetalheFacade: {
      loadArtistaDetails: vi.fn(),
      clear: vi.fn(),
      state$: subject.asObservable(),
      _subject: subject
    }
  };
});

import { artistaDetalheFacade } from '../facades/artista-detalhe.facade';
import ArtistDetailPage from './artista-detalhe-page';

describe('ArtistDetailPage', () => {
  const mockSubject = (artistaDetalheFacade as any)._subject;

  beforeEach(() => {
    vi.clearAllMocks();
    mockSubject.next({ artista: null, isLoading: true });
  });


  it('deve exibir mensagem de vazio se nenhum artista for retornado', async () => {
    render(
      <MemoryRouter>
        <ArtistDetailPage />
      </MemoryRouter>
    );

    await act(async () => {
      mockSubject.next({ artista: null, isLoading: false });
    });

    expect(screen.getByText(/Artista não encontrado/i)).toBeInTheDocument();
  });
});