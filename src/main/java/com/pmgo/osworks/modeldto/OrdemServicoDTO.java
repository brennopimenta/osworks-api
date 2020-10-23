package com.pmgo.osworks.modeldto;

import com.pmgo.osworks.domain.model.StatusOrdemServico;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdemServicoDTO {

    private Long id;

    private String nomeCliente;

    private String descricao;

    private BigDecimal preco;

    private StatusOrdemServico status;

    private OffsetDateTime dataAbertura;

    private OffsetDateTime dataFinalizacao;
}
