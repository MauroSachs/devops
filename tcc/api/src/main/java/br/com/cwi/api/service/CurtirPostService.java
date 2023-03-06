package br.com.cwi.api.service;

import br.com.cwi.api.domain.Curtida;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.mapper.CurtidaMapper;
import br.com.cwi.api.repository.CurtidaRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurtirPostService {
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private CurtidaRepository curtidaRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public void curtir(Long postId) {
        Usuario logado = usuarioAutenticadoService.get();
        Post post = buscarPostService.porId(postId);
        if (post.getCurtidas().stream().anyMatch(curtida -> curtida.getUsuario().getId().equals(logado.getId()))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você já curtiu este post");
        }

        Curtida curtida = CurtidaMapper.toEntity(logado);
        post.adicionarCurtida(curtida);

        curtidaRepository.save(curtida);
    }

    public void descurtir(Long postId) {
        Long logado = usuarioAutenticadoService.getId();
        Post post = buscarPostService.porId(postId);
        Curtida curtida = post.getCurtidas().stream()
                .filter(curtida1 ->  curtida1.getUsuario().getId().equals(logado))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não curtiu este post"));

        curtidaRepository.delete(curtida);
        post.removerCurtida(curtida);

    }
}
