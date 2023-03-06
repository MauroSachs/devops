package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.PostRequest;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.domain.Privacidade;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.mapper.PostMapper;
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

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class CriarPostagemServiceTest {
    @InjectMocks
    private CriarPostagemService criarPostagemService;
    @Mock
    private ValidaNotaService validaNotaService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private PostRepository postRepository;

    @Test
    @DisplayName("Deve crirr postagem")
    void deveCriarPostagem() {
        Usuario logado = UsuarioFactory.get();
        PostRequest request = new PostRequest();
        request.setNotaFilme("10");
        request.setTexto("teste");
        request.setPrivacidade(Privacidade.PUBLICO);
        request.setTituloFilme("filme teste");
        Post post = PostMapper.toEntity(request, logado);

        Mockito.when(usuarioAutenticadoService.get()).thenReturn(logado);

        criarPostagemService.postar(request);

        Mockito.verify(postRepository).save(post);
        assertTrue(logado.getPosts().contains(post));

    }

}
