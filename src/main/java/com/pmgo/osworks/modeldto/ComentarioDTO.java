package com.pmgo.osworks.modeldto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {

    private Long id;
    private String descricao;
    private OffsetDateTime dataEnvio;
}
