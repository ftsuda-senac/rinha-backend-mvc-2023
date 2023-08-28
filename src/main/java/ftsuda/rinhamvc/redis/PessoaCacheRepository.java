package ftsuda.rinhamvc.redis;

import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface PessoaCacheRepository extends KeyValueRepository<PessoaCache, String> {

}
