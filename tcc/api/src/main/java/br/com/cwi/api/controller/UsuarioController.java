package br.com.cwi.api.controller;

import br.com.cwi.api.controller.request.EditarUsuarioRequest;
import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.service.DetalharUsuarioService;
import br.com.cwi.api.service.EditarUsuarioService;
import br.com.cwi.api.service.ListarPostsService;
import br.com.cwi.api.service.ListarUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private ListarUsuariosService listarUsuariosService;
    @Autowired
    private DetalharUsuarioService detalharUsuarioService;
    @Autowired
    private ListarPostsService listarPostsService;
    @Autowired
    private EditarUsuarioService editarUsuarioService;


    @GetMapping
    public Page<UsuarioResponse> listarUsuarios(@RequestParam("text") Optional<String> text, Pageable pageable) {
        String paramValue = text.orElse("");
        return listarUsuariosService.listarPaginado(paramValue, pageable);
    }
    @GetMapping("/{id}")
    public UsuarioResponse detalhes(@PathVariable Long id) {
        return detalharUsuarioService.detalhar(id);
    }
    @GetMapping("/{id}/posts")
    public Page<PostResponse> listarPostsDoUsuario(@PathVariable Long id, Pageable pageable) {
        return listarPostsService.listarPorUsuario(id, pageable);
    }
    @PutMapping("/editar")
    public void editarUsuario(@Valid @RequestBody EditarUsuarioRequest request) {
        editarUsuarioService.editar(request);
    }


}
