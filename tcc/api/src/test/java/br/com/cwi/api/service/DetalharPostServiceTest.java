package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.factories.PostFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DetalharPostServiceTest {
    @InjectMocks
    private DetalharPostService detalharPostService;
    @Mock
    private BuscarPostService buscarPostService;

    @Test
    @DisplayName("Deve detalhar post")
    void deveDetalharPost() {
        Post post = PostFactory.get();

        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);

        PostResponse response = detalharPostService.detalhar(post.getId());

        assertEquals(response.getId(),post.getId());
        assertEquals(response.getTexto(),post.getTexto());
        assertEquals(response.getUsuarioId(),post.getUsuario().getId());
    }
}
