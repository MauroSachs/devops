package br.com.cwi.api.repository;

import br.com.cwi.api.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "" +
            "select * from post p" +
            " where p.usuario_id=?1" +
            " or p.privacidade='PUBLICO'" +
            " or (select exists (select * from amizade" +
            " where aceito = true" +
            " and " +
            "((usuario_1=p.usuario_id and usuario_2=?1)" +
            " or (usuario_1=?1 and usuario_2=p.usuario_id))))"
            , nativeQuery = true)
    Page<Post> findAllAllowedPosts(Long logadoId, Pageable pageable);

    @Query(value = "select * from post p where ( p.usuario_id=?2" +
            " and ((select exists (select * from amizade where (usuario_1=p.usuario_id and usuario_2=?1)" +
                " or (usuario_1=?1 and usuario_2=p.usuario_id) and aceito=true)) " +
                " or p.privacidade='PUBLICO' or p.usuario_id=?1))"
            , nativeQuery = true)
    Page<Post> findAllowedPostsByUser(Long logadoId, Long usuarioId, Pageable pageable);
    @Query(value = "\n" +
            "select * from post p where ( p.id=?2 " +
            "and ((select exists (select * from amizade where (usuario_1=p.usuario_id and usuario_2=?1)" +
                "or (usuario_1=?1 and usuario_2=p.usuario_id) and aceito=true)) " +
                "or p.privacidade='PUBLICO' or p.usuario_id=?1))"
            , nativeQuery = true)
    Optional<Post> getAllowedPostById(Long logadoId, Long postId);

}
