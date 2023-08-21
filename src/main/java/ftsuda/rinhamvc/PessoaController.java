package ftsuda.rinhamvc;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.codec.DecodingException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private static final Logger log = LoggerFactory.getLogger(PessoaController.class);

    // TODO: Removendo camada Services pois é uma aplicação simples.
    private final PessoaRepository repository;

    // TODO: Cache local simples para veritar ida ao BD em caso de apelidos já cadastrados
    // Em cluster, não compartilha informações entre as instâncias, mas estatisticamente pode reduzir a carga ao BD
    // Idealmente, deve ser um cache externo compartilhado (ex: Redis)
    private Set<String> apelidosUsados = new TreeSet<>();

    public PessoaController(PessoaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Pessoa pessoa, UriComponentsBuilder ucb) {
        if (apelidosUsados.contains(pessoa.getApelido())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        repository.save(pessoa);
        apelidosUsados.add(pessoa.getApelido());
        return ResponseEntity
                .created(ucb.pathSegment("pessoas", "{id}").buildAndExpand(pessoa.getId().toString()).toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        return repository.findById(id).map(p -> ResponseEntity.ok(p)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pessoa> search(@RequestParam("t") String t) {
        return repository.findBySearchTerm(t);
    }

    /**
     * Tratamento dos erros de integridade dos dados
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public void handlePostError(Exception ex) {
        log.error(ex.getMessage());
    }

    /**
     * Tratamento dos erros de sintaxe
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({WebExchangeBindException.class, ValidationException.class, DecodingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleInvalidRequestError(Exception ex) {
        log.error(ex.getMessage());
    }

}
