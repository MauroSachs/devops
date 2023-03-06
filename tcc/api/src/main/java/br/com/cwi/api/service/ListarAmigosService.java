package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.mapper.UsuarioMapper;
import br.com.cwi.api.repository.AmizadeRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarAmigosService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private AmizadeRepository amizadeRepository;

    public Page<UsuarioResponse> listarPaginado(String text, Pageable pageable) {
        Long logado = usuarioAutenticadoService.getId();
        Page<Usuario> usuarios = amizadeRepository.findAllFriendsPageable( logado, text, pageable);
        return usuarios.map(UsuarioMapper::toResponse);
    }

    public List<UsuarioResponse> listar(Long usuarioId) {
        List<Usuario> usuarios = amizadeRepository.findAllFriends(usuarioId);
        return usuarios.stream().map(UsuarioMapper::toResponse).collect(Collectors.toList());
    }
}
