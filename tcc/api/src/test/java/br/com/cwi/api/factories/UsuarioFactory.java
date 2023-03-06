package br.com.cwi.api.factories;

import br.com.cwi.api.security.domain.Usuario;

public class UsuarioFactory {

    public static Usuario get() {
        Usuario usuario = new Usuario();
        usuario.setId(SimpleFactory.getRandomLong());
        usuario.setNome("Usuário de teste");
        usuario.setApelido("teste");
        usuario.setEmail("teste@cwi.com.br");
        return usuario;
    }
}
