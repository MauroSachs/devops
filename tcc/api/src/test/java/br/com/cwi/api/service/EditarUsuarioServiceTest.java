package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.EditarUsuarioRequest;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.repository.UsuarioRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class  EditarUsuarioServiceTest {
    @InjectMocks
    private EditarUsuarioService editarUsuarioService;
    @Mock
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Test
    @DisplayName("Deve editar o usuario logado")
    void deveEditarUsuario() {
        Usuario logado = UsuarioFactory.get();

        EditarUsuarioRequest request = new EditarUsuarioRequest();
        request.setApelido("teste");
        request.setNome("nome teste");
        request.setImagem_perfil("imagem teste");
        when(usuarioAutenticadoService.get()).thenReturn(logado);
        editarUsuarioService.editar(request);

        verify(usuarioRepository).save(logado);
        assertEquals(logado.getNome(), request.getNome());
        assertEquals(logado.getApelido(), request.getApelido());
        assertEquals(logado.getImagemPerfil(), request.getImagem_perfil());
    }
}
