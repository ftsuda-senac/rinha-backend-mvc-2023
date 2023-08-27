package ftsuda.rinhamvc.redis;

import org.springframework.data.repository.CrudRepository;

public interface PessoaCacheRepository extends CrudRepository<PessoaCache, String> {

    boolean existsByApelido(String apelido);
}
