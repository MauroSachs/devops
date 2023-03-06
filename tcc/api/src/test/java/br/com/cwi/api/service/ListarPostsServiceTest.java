package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.factories.PostFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.repository.PostRepository;
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
class ListarPostsServiceTest {
    @InjectMocks
    private ListarPostsService listarPostsService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("Deve listar todos posts de forma paginada")
    void deveRetornarPostsPaginados(){
        Long logado = UsuarioFactory.get().getId();
        Pageable pageable = PageRequest.of(0, 5);
        List<Post> posts = List.of(
                PostFactory.get(),
                PostFactory.get(),
                PostFactory.get()
        );
        Page<Post> postsPaginados = new PageImpl<>(posts);

        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(postRepository.findAllAllowedPosts(logado, pageable ))
                .thenReturn(postsPaginados);

        Page<PostResponse> response = listarPostsService.listarTodos(pageable);

        verify(usuarioAutenticadoService).getId();
        verify(postRepository).findAllAllowedPosts(logado, pageable);
        assertEquals(posts.size(), response.getSize());
        assertEquals(posts.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(posts.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(posts.get(2).getId(), response.getContent().get(2).getId());
    }
    @Test
    @DisplayName("Deve listar todos posts do usuario forma paginada")
    void deveRetornarPostsDoUsuarioPaginados(){
        Long usuarioId = UsuarioFactory.get().getId();
        Long logado = UsuarioFactory.get().getId();
        Pageable pageable = PageRequest.of(0, 5);
        List<Post> posts = List.of(
                PostFactory.get(),
                PostFactory.get(),
                PostFactory.get()
        );
        Page<Post> postsPaginados = new PageImpl<>(posts);

        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(postRepository.findAllowedPostsByUser(logado,usuarioId,pageable ))
                .thenReturn(postsPaginados);

        Page<PostResponse> response = listarPostsService.listarPorUsuario(usuarioId,pageable);

        verify(usuarioAutenticadoService).getId();
        verify(postRepository).findAllowedPostsByUser(logado,usuarioId, pageable);
        assertEquals(posts.size(), response.getSize());
        assertEquals(posts.get(0).getId(), response.getContent().get(0).getId());
        assertEquals(posts.get(1).getId(), response.getContent().get(1).getId());
        assertEquals(posts.get(2).getId(), response.getContent().get(2).getId());
    }
    @Test
    @DisplayName("Deve retornar lista vazia quando não encontrar posts")
    void deveRetornarListaVazia() {
        Long logado = UsuarioFactory.get().getId();

        Pageable pageable = PageRequest.of(0, 5);


        Page<Post> postsPaginados = new PageImpl<>(new ArrayList<>());


        when(usuarioAutenticadoService.getId()).thenReturn(logado);
        when(postRepository.findAllAllowedPosts(logado, pageable ))
                .thenReturn(postsPaginados);


        Page<PostResponse> response = listarPostsService.listarTodos(pageable);

        Assertions.assertNotNull(response);
        assertEquals(0, response.getSize());
    }
}
