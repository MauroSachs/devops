package br.com.cwi.api.domain;

import br.com.cwi.api.security.domain.Usuario;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id") @ToString(of = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tituloFilme;
    private String notaFilme;
    private String texto;
    private LocalDateTime dataInclusao;

    @Enumerated(EnumType.STRING)
    private Privacidade privacidade;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany (mappedBy = "post")
    private List<Curtida> curtidas = new ArrayList<>();

    @OneToMany (mappedBy = "post")
    private List<Comentario> comentarios = new ArrayList<>();

    public void adicionarComentario(Comentario comentario) {
        this.comentarios.add(comentario);
        comentario.setPost(this);
    }

    public void adicionarCurtida(Curtida curtida) {
        this.curtidas.add(curtida);
        curtida.setPost(this);
    }
    public void removerCurtida(Curtida curtida) {
        this.curtidas.remove(curtida);
        curtida.setPost(null);
    }

}
