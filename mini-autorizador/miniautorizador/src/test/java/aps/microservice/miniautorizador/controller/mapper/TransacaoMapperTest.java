package aps.microservice.miniautorizador.controller.mapper;


import aps.microservice.miniautorizador.controller.dto.TransacaoDto;
import aps.microservice.miniautorizador.model.Transacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class TransacaoMapperTest {

  TransacaoMapper mapper = Mappers.getMapper(TransacaoMapper.class);

  @Test
  public void testToEntity() {
    TransacaoDto transacaoDto = new TransacaoDto();
    transacaoDto.setNumeroCartao("1234567812345678");
    transacaoDto.setSenhaCartao("senha123");
    transacaoDto.setValor(BigDecimal.valueOf(1000.0));

    Transacao transacao = mapper.toEntity(transacaoDto);

    assertEquals("1234567812345678", transacao.getNumeroCartao());
    assertEquals(mapper.toMd5(transacaoDto.getSenhaCartao()), transacao.getSenhaCartao());
    assertEquals(BigDecimal.valueOf(1000.0), transacao.getValor());
  }

  @Test
  public void testToDto() {
    Transacao transacao = new Transacao();
    transacao.setNumeroCartao("1234567812345678");
    transacao.setSenhaCartao(mapper.toMd5("senha123"));
    transacao.setValor(BigDecimal.valueOf(1000.0));

    TransacaoDto transacaoDto = mapper.toDto(transacao);

    assertEquals("1234567812345678", transacaoDto.getNumeroCartao());
    assertEquals(transacao.getSenhaCartao(), transacaoDto.getSenhaCartao());
    assertEquals(BigDecimal.valueOf(1000.0), transacaoDto.getValor());
  }
}
