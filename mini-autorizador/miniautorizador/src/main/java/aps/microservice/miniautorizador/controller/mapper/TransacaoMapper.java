package aps.microservice.miniautorizador.controller.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import aps.microservice.miniautorizador.controller.dto.TransacaoDto;
import aps.microservice.miniautorizador.model.Transacao;

@Mapper(componentModel="spring")
public interface TransacaoMapper extends MD5Mapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "senhaCartao", expression = "java(toMd5(dto.getSenhaCartao()))")
  Transacao toEntity(TransacaoDto dto);

  TransacaoDto toDto(Transacao entity);

}
