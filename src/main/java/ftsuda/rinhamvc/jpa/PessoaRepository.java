package ftsuda.rinhamvc.jpa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    // @formatter:off
    @Query(
        nativeQuery = true,
        value = "SELECT DISTINCT * FROM pessoa WHERE (nome || ';' || apelido || ';' || stack_db) ILIKE '%'||:termoBusca||'%' LIMIT 50")
 // @formatter:on
    List<Pessoa> findBySearchTerm(String termoBusca);

}
