package br.com.cwi.api.security.service;

import br.com.cwi.api.controller.request.UsuarioRequest;
import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.security.domain.Permissao;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static br.com.cwi.api.security.domain.Funcao.USUARIO;
import static br.com.cwi.api.mapper.UsuarioMapper.toEntity;
import static br.com.cwi.api.mapper.UsuarioMapper.toResponse;

@Service
public class IncluirUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidarEmailUnicoService validarEmailUnicoService;

    public UsuarioResponse incluir(UsuarioRequest request) {
        validarEmailUnicoService.validar(request.getEmail());
        Usuario usuario = toEntity(request);
        usuario.setSenha(getSenhaCriptografada(request.getSenha()));
        usuario.adicionarPermissao(getPermissaoPadrao());
        usuario.setAtivo(true);
        usuario.setDataNascimento(request.getData_nascimento());
        usuario.setApelido(getApelido(request));
        usuario.setImagemPerfil(request.getImagem_perfil());

        usuarioRepository.save(usuario);

        return toResponse(usuario);
    }

    private String getSenhaCriptografada(String senhaAberta) {
        return passwordEncoder.encode(senhaAberta);
    }

    private Permissao getPermissaoPadrao() {
        Permissao permissao = new Permissao();
        permissao.setFuncao(USUARIO);
        return permissao;
    }
    private String getApelido(UsuarioRequest request) {
        if(Objects.isNull(request.getApelido())) {
            return request.getNome().split(" ")[0];
        }
        return request.getApelido();
    }
}
