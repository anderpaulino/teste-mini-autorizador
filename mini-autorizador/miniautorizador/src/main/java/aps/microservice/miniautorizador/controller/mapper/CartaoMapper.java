package aps.microservice.miniautorizador.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.model.Cartao;

@Mapper(componentModel="spring")
public interface CartaoMapper extends MD5Mapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "saldo", ignore = true)
  @Mapping(target = "senha", expression = "java(toMd5(dto.getSenha()))")
  Cartao toEntity(CartaoDto dto);

  CartaoDto toDto(Cartao entity);

}
