package aps.microservice.miniautorizador.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.controller.mapper.CartaoMapper;
import aps.microservice.miniautorizador.exception.CartaoJaExisteException;
import aps.microservice.miniautorizador.usecase.cartao.CreateCartaoUseCase;
import aps.microservice.miniautorizador.usecase.cartao.GetCartaoUseCase;

@SpringBootTest
@AutoConfigureMockMvc
class CartaoControllerTest {

  @Mock
  CreateCartaoUseCase createCartaoUseCase;

  @Mock
  GetCartaoUseCase getCartaoUseCase;
  
  CartaoMapper cartaoMapper =  Mappers.getMapper(CartaoMapper.class);;
  
  @InjectMocks
  CartaoController cartaoController;

  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    cartaoController = new CartaoController(createCartaoUseCase, getCartaoUseCase, cartaoMapper);
    mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
  }

  @Test
  void createCartao() throws Exception {
    CartaoDto cartaoDto = CartaoDto.builder().numeroCartao("123456789").senha("123").build();    
    mockMvc.perform(post("/cartoes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(cartaoDto)))
      .andExpect(status().isCreated());
  }

  @Test
  void createCartao_whenNumeroCartaoAlreadyExists_throwsException() throws Exception {
    CartaoDto cartaoDto = CartaoDto.builder().numeroCartao("123456789").senha("123").build();    

    when(createCartaoUseCase.execute(any())).thenThrow(new CartaoJaExisteException(""));

    mockMvc.perform(post("/cartoes")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(cartaoDto)))
      .andExpect(status().is4xxClientError());
  }

  @Test
  void getSaldo() throws Exception {

    when(getCartaoUseCase.execute(any())).thenReturn(Optional.of(BigDecimal.ONE));

    mockMvc.perform(get("/cartoes/{numeroCartao}", "123456789"))
      .andExpect(status().isOk())
      .andExpect(content().string("1"));
  }

  @Test
  void getSaldo_whenNumeroCartaoDoesntExist() throws Exception {

    when(getCartaoUseCase.execute(any())).thenReturn(Optional.empty());

    mockMvc.perform(get("/cartoes/{numeroCartao}", "123456789"))
      .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
  }


}