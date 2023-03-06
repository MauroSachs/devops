package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.repository.AmizadeRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarSolicitacoesServiceTest {
    @InjectMocks
    private ListarSolicitacoesService listarSolicitacoesService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private AmizadeRepository amizadeRepository;

    @Test
    @DisplayName("Deve listar todos usuarios que solicitaram amizade")
    void deveRetornarSolicitacoes() {
        Long logado = UsuarioFactory.get().getId();

        List<Usuario> solicitacoes = List.of(
                UsuarioFactory.get(),
                UsuarioFactory.get(),
                UsuarioFactory.get()
        );

        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(amizadeRepository.findAllRequests(logado))
                .thenReturn(solicitacoes);

        List<UsuarioResponse> response = listarSolicitacoesService.listar();
        verify(usuarioAutenticadoService).getId();
        verify(amizadeRepository).findAllRequests(logado);
        assertEquals(solicitacoes.size(), response.size());
        assertEquals(solicitacoes.get(0).getId(), response.get(0).getId());
        assertEquals(solicitacoes.get(1).getId(), response.get(1).getId());
        assertEquals(solicitacoes.get(2).getId(), response.get(2).getId());
    }
    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar solicitacoes")
    void deveRetornarListaVazia() {
        Long logado = UsuarioFactory.get().getId();

        List<Usuario> requests = new ArrayList<>();


        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(amizadeRepository.findAllRequests(logado ))
                .thenReturn(requests);


        List<UsuarioResponse> response = listarSolicitacoesService.listar();

        Assertions.assertNotNull(response);
        assertEquals(0, response.size());
    }
}