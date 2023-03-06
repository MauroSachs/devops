package br.com.cwi.api.security.controller;

import br.com.cwi.api.controller.request.UsuarioRequest;
import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.security.service.IncluirUsuarioService;
import br.com.cwi.api.service.BuscarUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioSecurityController {

    @Autowired
    private IncluirUsuarioService service;
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;

    @PostMapping
    public UsuarioResponse incluir(@RequestBody UsuarioRequest request) {
        return service.incluir(request);
    }
    @GetMapping("/me")
    public UsuarioResponse buscar() {
        return buscarUsuarioService.logado();
    }

}
