package aps.microservice.miniautorizador.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.controller.dto.TransacaoDto;
import aps.microservice.miniautorizador.controller.mapper.TransacaoMapper;
import aps.microservice.miniautorizador.exception.CartaoInexistenteException;
import aps.microservice.miniautorizador.exception.SaldoInsuficioenteException;
import aps.microservice.miniautorizador.exception.SenhaInvalidaException;
import aps.microservice.miniautorizador.service.TransacaoService;

@SpringBootTest
@AutoConfigureMockMvc
class TransacaoControllerTest {

  @Mock
  TransacaoService transacaoService;
  
  TransacaoMapper transacaoMapper =  Mappers.getMapper(TransacaoMapper.class);;
  
  @InjectMocks
  TransacaoController transacaoController;

  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    transacaoController = new TransacaoController(transacaoService, transacaoMapper);
    mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
  }

  @Test
  void createTransacao() throws Exception {
    TransacaoDto transacaoDto = TransacaoDto.builder().numeroCartao("123456789").senhaCartao("123456789").valor(BigDecimal.ONE).build();

    when(transacaoService.createTransacao(any())).thenReturn(true);

    mockMvc.perform(post("/transacoes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(transacaoDto)))
      .andExpect(status().isCreated());
  }

  @Test
  void createTransacao_whenCartaoInexistente_throwsException() throws Exception {
    CartaoDto cartaoDto = CartaoDto.builder().numeroCartao("123456789").senha("123").build();    

    when(transacaoService.createTransacao(any())).thenThrow(new CartaoInexistenteException());
    mockMvc.perform(post("/transacoes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(cartaoDto)))
      .andExpect(status().is4xxClientError())
      .andExpect(content().string("CARTAO_INEXISTENTE"));
  }

  @Test
  void createTransacao_whenSenhaInvalida_throwsException() throws Exception {
    CartaoDto cartaoDto = CartaoDto.builder().numeroCartao("123456789").senha("123").build();    

    when(transacaoService.createTransacao(any())).thenThrow(new SenhaInvalidaException());
    mockMvc.perform(post("/transacoes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(cartaoDto)))
      .andExpect(status().is4xxClientError())
      .andExpect(content().string("SENHA_INVALIDA"));
  }

  @Test
  void createTransacao_whenSaldoInsuficiente_throwsException() throws Exception {
    CartaoDto cartaoDto = CartaoDto.builder().numeroCartao("123456789").senha("123").build();    

    when(transacaoService.createTransacao(any())).thenThrow(new SaldoInsuficioenteException());
    mockMvc.perform(post("/transacoes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(cartaoDto)))
      .andExpect(status().is4xxClientError())
      .andExpect(content().string("SALDO_INSUFICIENTE"));
  }
}