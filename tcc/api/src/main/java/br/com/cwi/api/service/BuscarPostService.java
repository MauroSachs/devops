package br.com.cwi.api.service;

import br.com.cwi.api.domain.Post;
import br.com.cwi.api.repository.PostRepository;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Post porId(Long postId) {
        Long logado = usuarioAutenticadoService.getId();
        return postRepository.getAllowedPostById(logado, postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Post não encontrado"));
    }

}
