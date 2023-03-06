package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.factories.UsuarioFactory;
import br.com.cwi.api.security.domain.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DetalharUsuarioServiceTest {
    @InjectMocks
    private DetalharUsuarioService detalharUsuarioService;
    @Mock
    private BuscarUsuarioService buscarUsuarioService;

    @Test
    @DisplayName("Deve detalhar usuario")
    void deveDetalharUsuario() {
        Usuario usuario = UsuarioFactory.get();

        Mockito.when(buscarUsuarioService.porId(usuario.getId())).thenReturn(usuario);

        UsuarioResponse response = detalharUsuarioService.detalhar(usuario.getId());

        assertEquals(response.getId(),usuario.getId());
        assertEquals(response.getNome(),usuario.getNome());
        assertEquals(response.getEmail(),usuario.getEmail());
    }
}
