package br.com.cwi.api.controller;

import br.com.cwi.api.controller.request.AlterarPostRequest;
import br.com.cwi.api.controller.request.ComentarioRequest;
import br.com.cwi.api.controller.request.PostRequest;
import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private ListarPostsService listarPostsService;
    @Autowired
    private DetalharPostService detalharPostService;
    @Autowired
    private CriarPostagemService criarPostagemService;
    @Autowired
    private CriarComentarioService criarComentarioService;
    @Autowired
    private CurtirPostService curtirPostService;
    @Autowired
    private AlterarPostService alterarPostService;
    @Autowired
    private DeletarPostService deletarPostService;

    @GetMapping
    public Page<PostResponse> listarPosts(Pageable pageable) {
        return listarPostsService.listarTodos(pageable);
    }

    @GetMapping("/{id}")
    public PostResponse detalhar(@PathVariable Long id) {
        return detalharPostService.detalhar(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarPost(@Valid @RequestBody PostRequest request) {
        criarPostagemService.postar(request);
    }

    @PostMapping("/{id}/comentar")
    @ResponseStatus(HttpStatus.CREATED)
    public void comentar(@Valid @RequestBody ComentarioRequest request, @PathVariable Long id) {
        criarComentarioService.comentar(request, id);
    }

    @PutMapping("/{id}/curtir")
    @ResponseStatus(HttpStatus.CREATED)
    public void curtir(@PathVariable Long id) {
        curtirPostService.curtir(id);
    }

    @PutMapping("/{id}/descurtir")
    @ResponseStatus(HttpStatus.CREATED)
    public void descurtir(@PathVariable Long id) {
        curtirPostService.descurtir(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void alterar(@Valid @RequestBody AlterarPostRequest request, @PathVariable Long id) {
        alterarPostService.alterar(request,id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        deletarPostService.deletar(id);
    }
}

