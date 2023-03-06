package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.ComentarioRequest;
import br.com.cwi.api.domain.Comentario;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.factories.PostFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.mapper.ComentarioMapper;
import br.com.cwi.api.repository.ComentarioRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CriarComentarioServiceTest {
    @InjectMocks
    private CriarComentarioService criarComentarioService;
    @Mock
    private ComentarioRepository comentarioRepository;
    @Mock
    private BuscarPostService buscarPostService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;

    @Test
    @DisplayName("Deve criar comentario")
    void deveCriarComentario() {
        Usuario logado = UsuarioFactory.get();
        ComentarioRequest request = new ComentarioRequest();
        request.setTexto("teste");
        Post post = PostFactory.get();
        Comentario comentario = ComentarioMapper.toEntity(request, logado);

        Mockito.when(usuarioAutenticadoService.get()).thenReturn(logado);
        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);


        criarComentarioService.comentar(request, post.getId());

        Mockito.verify(comentarioRepository).save(comentario);
        assertTrue(post.getComentarios().contains(comentario));

    }

}
