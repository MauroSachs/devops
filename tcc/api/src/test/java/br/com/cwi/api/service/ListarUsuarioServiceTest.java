package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.repository.UsuarioRepository;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ListarUsuarioServiceTest {
    @InjectMocks
    private ListarUsuariosService listarUsuariosService;
    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve buscar todos os usuarios e retornar reponse paginado")
    void deveRetornarTodosUsuarios() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Usuario> usuarios = List.of(UsuarioFactory.get(), UsuarioFactory.get(), UsuarioFactory.get());
        Page<Usuario> usuariosPaginado = new PageImpl(usuarios);
        when(this.usuarioRepository.findByNomeContainsIgnoreCaseOrEmailContainsIgnoreCase("","", pageable)).thenReturn(usuariosPaginado);
        Page<UsuarioResponse> response = this.listarUsuariosService.listarPaginado("",pageable);
        verify(this.usuarioRepository).findByNomeContainsIgnoreCaseOrEmailContainsIgnoreCase("","",pageable);
        Assertions.assertEquals(usuarios.size(), response.getSize());
        Assertions.assertEquals((usuarios.get(0)).getId(), (response.getContent().get(0)).getId());
        Assertions.assertEquals((usuarios.get(1)).getId(), (response.getContent().get(1)).getId());
        Assertions.assertEquals((usuarios.get(2)).getId(), (response.getContent().get(2)).getId());
    }
    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar usuarios")
    void deveRetornarListaVazia() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Usuario> usuariosPaginado = new PageImpl(new ArrayList());
        when(this.usuarioRepository.findByNomeContainsIgnoreCaseOrEmailContainsIgnoreCase("","", pageable)).thenReturn(usuariosPaginado);
        Page<UsuarioResponse> response = this.listarUsuariosService.listarPaginado("",pageable);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getSize());
    }
}
