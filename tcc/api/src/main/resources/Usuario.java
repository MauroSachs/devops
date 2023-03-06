package br.com.cwi.api;

import br.com.cwi.api.domain.Post;
import br.com.cwi.api.security.domain.Permissao;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

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
    private String imagem_perfil;
    private LocalDate data_nascimento;


    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Permissao> permissoes = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "amizade",
            joinColumns = @JoinColumn(name = "usuario_1"),
            inverseJoinColumns = @JoinColumn(name = "usuario_2"))

    private List<Usuario> amigos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Post> posts = new ArrayList<>();

    public void adicionarPermissao(Permissao permissao) {
        this.permissoes.add(permissao);
        permissao.setUsuario(this);
    }
}
