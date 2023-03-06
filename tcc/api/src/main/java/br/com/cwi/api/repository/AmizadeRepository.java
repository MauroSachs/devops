package br.com.cwi.api.repository;

import br.com.cwi.api.domain.Amizade;
import br.com.cwi.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AmizadeRepository extends JpaRepository<Amizade, Long> {
    List<Amizade> findAllByAceitoTrueAndUsuario1IdOrAceitoTrueAndUsuario2Id(Long usuario1_id, Long usuario2_id);

    @Query(value = "select u from Usuario u inner join Amizade a on u.id=a.usuario1.id or u.id=a.usuario2.id" +
            " where not u.id=?1" +
            " and (lower(u.nome) like %?2% or lower(u.email) like %?2%)" +
            " and a.aceito=true " +
            " and  ((a.usuario1.id=?1) or (a.usuario2.id=?1))"
    )
    Page<Usuario> findAllFriendsPageable(Long logado, String text, Pageable pageable);

    @Query(value = "select u from Usuario u inner join Amizade a on u.id=a.usuario1.id or u.id=a.usuario2.id" +
            " where not u.id=?1" +
            " and a.aceito=true " +
            " and  ((a.usuario1.id=?1) or (a.usuario2.id=?1))"
    )
    List<Usuario> findAllFriends(Long logado);

    @Query(value = "select u from Usuario u inner join Amizade a on u.id=a.usuario1.id or u.id=a.usuario2.id where not u.id=?1 and a.aceito=false and (a.usuario2.id=?1)\t")
    List<Usuario> findAllRequests(Long logado);

    Optional<Amizade> findByUsuario1IdAndUsuario2IdAndAceitoFalse(Long usuario1_id, Long usuario2_id);
}
