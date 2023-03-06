package br.com.cwi.api.service;

import br.com.cwi.api.domain.Post;
import br.com.cwi.api.factories.PostFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.repository.ComentarioRepository;
import br.com.cwi.api.repository.CurtidaRepository;
import br.com.cwi.api.repository.PostRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class DeletarPostServiceTest {
    @InjectMocks
    private DeletarPostService deletarPostService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private BuscarPostService buscarPostService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private ValidaEditarPostService validaEditarPostService;
    @Mock
    private ComentarioRepository comentarioRepository;
    @Mock
    private CurtidaRepository curtidaRepository;

    @Test
    @DisplayName("Deve deletar post por id")
    void deveDeletarPost() {
        Post post = PostFactory.get();
        Usuario logado = UsuarioFactory.get();
        logado.adicionarPost(post);

        Mockito.when(usuarioAutenticadoService.get()).thenReturn(logado);
        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);

        deletarPostService.deletar(post.getId());

        Mockito.verify(postRepository).delete(post);
        assertFalse(logado.getPosts().contains(post));
    }
}
