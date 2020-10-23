package com.pmgo.osworks.domain.model;

import com.pmgo.osworks.domain.exception.NegocioException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@Table(name = "ordem_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Comentado pois,, o DTO está assumindo a função de validação na entrada de dados na chamada da api

//    @Valid
//    @ConvertGroup(from = Default.class, to = ValidationGroups.ClienteId.class)
//    @NotNull
    @ManyToOne
    private Cliente cliente;

    private String descricao;

    private BigDecimal preco;


//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)  // Cliente não consegue atribuir valor, pois esta anotado como somente leitura.!
    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataAbertura;

//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private OffsetDateTime dataFinalizacao;

    @OneToMany(mappedBy = "ordemServico")
    private List<Comentario> comentarios = new ArrayList<>();


    public boolean podeSerFinalizada() {
        return StatusOrdemServico.ABERTA.equals(getStatus());
    }

    public boolean naoPodeSerFinalizada() {
        return !podeSerFinalizada();
    }

    public void finalizar() {
        if (naoPodeSerFinalizada()) {
            throw new NegocioException("Ordem de serviço não pode ser finalizada!");
        }
        setStatus(StatusOrdemServico.FINALIZADA);
        setDataFinalizacao(OffsetDateTime.now());
    }
}
