package ftsuda.rinhamvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ftsuda.rinhamvc.jpa.PessoaRepository;

@RestController
@RequestMapping("/contagem-pessoas")
public class ContagemPessoaController {

    private final PessoaRepository repository;

    public ContagemPessoaController(PessoaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Long count() {
        return repository.count();
    }

}
