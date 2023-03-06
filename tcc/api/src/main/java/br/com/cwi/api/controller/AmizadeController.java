package br.com.cwi.api.controller;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.service.AdministrarAmigosService;
import br.com.cwi.api.service.ListarAmigosService;
import br.com.cwi.api.service.ListarSolicitacoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios/amigos")
public class AmizadeController {
    @Autowired
    private ListarAmigosService listarAmigosService;
    @Autowired
    private ListarSolicitacoesService listarSolicitacoesService;
    @Autowired
    private AdministrarAmigosService administrarAmigosService;

    @GetMapping("/paginado")
    public Page<UsuarioResponse> listarAmigosPaginados(@RequestParam("text") Optional<String> text, Pageable pageable) {
        String paramValue = text.orElse("");
        return listarAmigosService.listarPaginado(paramValue, pageable);
    }
    @GetMapping("/{id}")
    public List<UsuarioResponse> listarAmigos(@PathVariable Long id) {
        return  listarAmigosService.listar(id);
    }
    @GetMapping("/solicitacoes")
    public List<UsuarioResponse> listarSolicitacoes() {
        return listarSolicitacoesService.listar();
    }

    @PutMapping("/{id}/solicitar")
    public void solicitar(@PathVariable Long id) {
        administrarAmigosService.solicitar(id);
    }

    @PutMapping("/{id}/aceitar")
    public void aceitar(@PathVariable Long id) {
        administrarAmigosService.aceitar(id);
    }

    @PutMapping("/{id}/recusar")
    public void recusar(@PathVariable Long id) {
        administrarAmigosService.recusar(id);
    }

    @DeleteMapping("/{id}/remover")
    public void deletar(@PathVariable Long id) {
        administrarAmigosService.deletar(id);
    }

}
