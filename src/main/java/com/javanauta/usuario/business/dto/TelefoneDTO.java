package com.javanauta.usuario.business.dto;

import lombok.Builder;

@Builder
public record TelefoneDTO (Long id,String ddd, String numero){

}
