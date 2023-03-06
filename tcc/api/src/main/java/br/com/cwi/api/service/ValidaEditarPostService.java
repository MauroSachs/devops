package br.com.cwi.api.service;

import br.com.cwi.api.domain.Post;
import br.com.cwi.api.security.domain.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidaEditarPostService {
    public void validar(Usuario logado, Post post) {
        if(!post.getUsuario().getId().equals(logado.getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Você não possui permissão para editar este post");
        }
    }
}
