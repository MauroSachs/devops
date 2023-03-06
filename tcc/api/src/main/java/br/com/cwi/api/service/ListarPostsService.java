package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.mapper.PostMapper;
import br.com.cwi.api.repository.PostRepository;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarPostsService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private PostRepository postRepository;

    public Page<PostResponse> listarPorUsuario(Long id, Pageable pageable) {
        Long logado = usuarioAutenticadoService.getId();
        return postRepository.findAllowedPostsByUser(logado, id, pageable).map(PostMapper::toResponse);

    }

    public Page<PostResponse> listarTodos(Pageable pageable) {
        Long logado = usuarioAutenticadoService.getId();
        return postRepository.findAllAllowedPosts(logado, pageable).map(PostMapper::toResponse);
    }
}
