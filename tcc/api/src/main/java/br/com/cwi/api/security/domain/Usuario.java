package br.com.cwi.api.security.domain;

import br.com.cwi.api.domain.Post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter @Setter @EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String apelido;
    private String imagemPerfil;
    private LocalDate dataNascimento;
    @Column(nullable = false)
    private boolean ativo;


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();



    @OneToMany(mappedBy = "usuario")
    private List<Post> posts = new ArrayList<>();

    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }

    public void adicionarPost(Post post) {
        this.posts.add(post);
        post.setUsuario(this);
    }

    public void removerPost(Post post) {
        this.posts.remove(post);
        post.setUsuario(null);
    }
}
