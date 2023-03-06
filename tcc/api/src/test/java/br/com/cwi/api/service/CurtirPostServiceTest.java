package br.com.cwi.api.service;

import br.com.cwi.api.domain.Curtida;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.factories.PostFactory;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.mapper.CurtidaMapper;
import br.com.cwi.api.repository.CurtidaRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CurtirPostServiceTest {
    @InjectMocks
    private CurtirPostService curtirPostService;
    @Mock
    private BuscarPostService buscarPostService;
    @Mock
    private CurtidaRepository curtidaRepository;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Test
    @DisplayName("Deve curtir post por id")
    void deveCurtirPost() {
        Usuario logado = UsuarioFactory.get();
        Post post = PostFactory.get();
        Curtida curtida = CurtidaMapper.toEntity(logado);

        Mockito.when(usuarioAutenticadoService.get()).thenReturn(logado);
        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);

        curtirPostService.curtir(post.getId());
        Mockito.verify(curtidaRepository).save(curtida);
        assertTrue(post.getCurtidas().contains(curtida));
    }
    @Test
    @DisplayName("Não deve curtir quando já estiver curtido")
    void naoDeveCurtirPost() {
        Usuario logado = UsuarioFactory.get();
        Post post = PostFactory.get();
        Curtida curtida = CurtidaMapper.toEntity(logado);
        post.adicionarCurtida(curtida);

        Mockito.when(usuarioAutenticadoService.get()).thenReturn(logado);
        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);

        assertThrows(ResponseStatusException.class, () -> curtirPostService.curtir(post.getId()));
        verify(curtidaRepository, never()).save(Mockito.any());
    }
    @Test
    @DisplayName("Deve descurtir post por id")
    void deveDescurtirPost() {
        Usuario logado = UsuarioFactory.get();
        Post post = PostFactory.get();
        Curtida curtida = CurtidaMapper.toEntity(logado);
        post.adicionarCurtida(curtida);

        Mockito.when(usuarioAutenticadoService.getId()).thenReturn(logado.getId());
        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);

        curtirPostService.descurtir(post.getId());
        Mockito.verify(curtidaRepository).delete(curtida);
        assertFalse(post.getCurtidas().contains(curtida));
    }
    @Test
    @DisplayName("Não deve curtir quando não estiver curtido")
    void naoDeveDescurtirPost() {
        Usuario logado = UsuarioFactory.get();
        Post post = PostFactory.get();

        Mockito.when(usuarioAutenticadoService.getId()).thenReturn(logado.getId());
        Mockito.when(buscarPostService.porId(post.getId())).thenReturn(post);
        assertThrows(ResponseStatusException.class, () -> curtirPostService.descurtir(post.getId()));
        verify(curtidaRepository, never()).delete(Mockito.any());
    }
}
