package com.algaworks.apispring.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioModel {

    private Long id;
    private String descricao;
    private OffsetDateTime dataEnvio;

}
