package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.mapper.UsuarioMapper;
import br.com.cwi.api.repository.AmizadeRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListarSolicitacoesService {
    @Autowired
    private AmizadeRepository amizadeRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public List<UsuarioResponse> listar() {
        Long logado = usuarioAutenticadoService.getId();
        List<Usuario> usuarios = amizadeRepository.findAllRequests(logado);
        return usuarios.stream().map(UsuarioMapper::toResponse).collect(Collectors.toList());
    }
}
