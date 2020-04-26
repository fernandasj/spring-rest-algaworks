package com.algaworks.apispring.domain.model;

import com.algaworks.apispring.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServico {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Cliente cliente;
    private String descricao;
    private BigDecimal preco;
    private OffsetDateTime dataAbertura;
    private OffsetDateTime dataFinalizacao;

    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdemServico that = (OrdemServico) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean podeSerFinalizada(){
        return StatusOrdemServico.ABERTA.equals(getStatus());
    }

    public boolean naoPodeSerFinalizada(){
        return !podeSerFinalizada();
    }

    public void finalizar() {
        if (naoPodeSerFinalizada()){
            throw new NegocioException("Ordem de servico nao pode ser finalizada");
        }

        setStatus(StatusOrdemServico.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }
}
