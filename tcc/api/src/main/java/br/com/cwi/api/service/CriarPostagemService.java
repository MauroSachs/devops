package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.PostRequest;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.mapper.PostMapper;
import br.com.cwi.api.repository.PostRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarPostagemService {
    @Autowired
    private ValidaNotaService validaNotaService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private PostRepository postRepository;

    public void postar(PostRequest request) {
        Usuario logado = usuarioAutenticadoService.get();
        validaNotaService.validar(request.getNotaFilme());
        Post post =  PostMapper.toEntity(request, logado);
        logado.adicionarPost(post);

        postRepository.save(post);
    }
}
