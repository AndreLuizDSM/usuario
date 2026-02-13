package com.javanauta.usuario.business.dto;

import lombok.Builder;

@Builder
public record ViaCepDTO (String cep,
                         String logradouro,
                         String complemento,
                         String unidade,
                         String bairro,
                         String localidade,
                         String uf,
                         String estado,
                         String regiao,
                         String ibge,
                         String gia,
                         String ddd,
                         String siafi){
}
