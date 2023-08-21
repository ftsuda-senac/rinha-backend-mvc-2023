package ftsuda.rinhamvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class Pessoa implements Persistable<UUID> {

    private static final String STACK_SEPARATOR = ",";

    @Id
    private UUID id;

    @NotEmpty
    @Size(max = 32)
    private String apelido;

    @NotEmpty
    @Size(max = 100)
    private String nome;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate nascimento;

    @JsonIgnore
    private String stackDb;

    @Transient
    private List<@NotEmpty @Size(max = 32) String> stack;

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }

    @PostLoad
    private void afterLoad() {
        if (StringUtils.isNotBlank(this.stackDb)) {
            this.stack = Arrays.asList(this.stackDb.split(STACK_SEPARATOR));
        }
    }

    @PrePersist
    private void beforePersist() {
        this.id = UUID.randomUUID();
        if (this.stack != null && !this.stack.isEmpty()) {
            this.stackDb = String.join(STACK_SEPARATOR, this.stack);
        }
    }



}
