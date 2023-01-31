package aps.microservice.miniautorizador.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.model.Cartao;

public class CartaoMapperTest {
    CartaoMapper cartaoMapper;

    @BeforeEach
    public void setup() {
        cartaoMapper = new CartaoMapperImpl();
    }

    @Test
    public void when_converting_CartaoDto_to_Cartao_then_return_correct_result() {
        CartaoDto dto = CartaoDto.builder().numeroCartao("123456789").senha("password").build();

        Cartao cartao = cartaoMapper.toEntity(dto);

        assertEquals("123456789", cartao.getNumeroCartao());
        assertEquals(cartaoMapper.toMd5("password"), cartao.getSenha());
    }

    @Test
    public void when_converting_Cartao_to_CartaoDto_then_return_correct_result() {
        Cartao cartao = Cartao.builder().numeroCartao("123456789").saldo(BigDecimal.ONE).senha(cartaoMapper.toMd5("password")).build();

        CartaoDto dto = cartaoMapper.toDto(cartao);

        assertEquals("123456789", dto.getNumeroCartao());
        assertEquals(cartaoMapper.toMd5("password"),  dto.getSenha());
    }
}
