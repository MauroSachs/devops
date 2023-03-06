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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListarAmigosServiceTest {
    @InjectMocks
    private ListarAmigosService listarAmigosService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private AmizadeRepository amizadeRepository;

    @Test
    @DisplayName("Deve listar todos amigos  de forma paginada")
    void deveRetornarAmigosPaginados(){
        Long logado = UsuarioFactory.get().getId();
        String text = "";
        Pageable pageable = PageRequest.of(0, 5);
        List<Usuario> amigos = List.of(
                UsuarioFactory.get(),
                UsuarioFactory.get(),
                UsuarioFactory.get()
        );
        Page<Usuario> amigosPaginados = new PageImpl<>(amigos);

        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(amizadeRepository.findAllFriendsPageable(logado, text, pageable ))
                .thenReturn(amigosPaginados);

        Page<UsuarioResponse> response = listarAmigosService.listarPaginado(text,pageable);
        verify(usuarioAutenticadoService).getId();
        verify(amizadeRepository).findAllFriendsPageable(logado, text, pageable);
        assertEquals(amigos.size(), response.getSize());
        assertEquals(amigos.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(amigos.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(amigos.get(2).getId(), response.getContent().get(2).getId());
    }
    @Test
    @DisplayName("Deve listar todos amigos do Usuario")
    void deveRetornarAmigos(){
        Long usuarioId = UsuarioFactory.get().getId();
        String text = "";

        List<Usuario> amigos = List.of(
                UsuarioFactory.get(),
                UsuarioFactory.get(),
                UsuarioFactory.get()
        );



        when(amizadeRepository.findAllFriends(usuarioId))
                .thenReturn(amigos);

        List<UsuarioResponse> response = listarAmigosService.listar(usuarioId);
        verify(amizadeRepository).findAllFriends(usuarioId);
        assertEquals(amigos.size(), response.size());
        assertEquals(amigos.get(0).getId(), response.get(0).getId());
        assertEquals(amigos.get(1).getId(), response.get(1).getId());
        assertEquals(amigos.get(2).getId(), response.get(2).getId());
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar amigos")
    void deveRetornarListaVazia() {
        Long logado = UsuarioFactory.get().getId();
        String text = "";
        Pageable pageable = PageRequest.of(0, 5);


        Page<Usuario> amigosPaginados = new PageImpl<>(new ArrayList<>());


        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(amizadeRepository.findAllFriendsPageable(logado, text, pageable ))
                .thenReturn(amigosPaginados);


        Page<UsuarioResponse> response = listarAmigosService.listarPaginado(text, pageable);

        Assertions.assertNotNull(response);
        assertEquals(0, response.getSize());
    }
}
