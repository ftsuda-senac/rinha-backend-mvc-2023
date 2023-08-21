package ftsuda.rinhamvc;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

    @Query(nativeQuery = true,
            value = "SELECT DISTINCT * FROM pessoa WHERE nome ILIKE '%'||:termoBusca||'%' OR apelido ILIKE '%'||:termoBusca||'%' OR stack_db ILIKE '%'||:termoBusca||'%' LIMIT 50")
    List<Pessoa> findBySearchTerm(String termoBusca);

}
