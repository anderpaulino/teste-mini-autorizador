package aps.microservice.miniautorizador.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import aps.microservice.miniautorizador.exception.CartaoJaExisteException;
import aps.microservice.miniautorizador.model.Cartao;
import aps.microservice.miniautorizador.repository.CartaoRepository;

@ExtendWith(MockitoExtension.class)
class CartaoServiceTest {

    @Mock
    CartaoRepository cartaoRepository;

    CartaoService cartaoService;

    @BeforeEach
    void setUp() {
        cartaoService = new CartaoService(cartaoRepository);
    }

    @Test
    void testCreateCartao() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("123456");

        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao()))
        .thenReturn(Optional.empty());
        when(cartaoRepository.save(cartao)).thenReturn(cartao);

        Cartao createdCartao = cartaoService.createCartao(cartao);

        assertEquals(cartao.getNumeroCartao(), createdCartao.getNumeroCartao());
        assertEquals(cartao.getSenha(), createdCartao.getSenha());
    }

    @Test
    void testCreateCartao_whenNumeroCartaoAlreadyExists_throwsException() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("123456");

        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao()))
        .thenReturn(Optional.of(cartao));

        assertThrows(CartaoJaExisteException.class, () -> cartaoService.createCartao(cartao));
    }

    @Test
    void testGetSaldo() {
        String numeroCartao = "1234567890123456";
        BigDecimal saldo = new BigDecimal("500.00");

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSaldo(saldo);

        when(cartaoRepository.findByNumeroCartao(numeroCartao))
        .thenReturn(Optional.of(cartao));

        Optional<BigDecimal> actualSaldo = cartaoService.getSaldo(numeroCartao);

        assertEquals(saldo, actualSaldo.get());
    }

    @Test
    void testGetSaldo_whenNumeroCartaoDoesntExist() {
        String numeroCartao = "1234567890123456";
        BigDecimal saldo = new BigDecimal("500.00");

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao(numeroCartao);
        cartao.setSaldo(saldo);

        when(cartaoRepository.findByNumeroCartao(numeroCartao))
        .thenReturn(Optional.empty() );

        Optional<BigDecimal> actualSaldo = cartaoService.getSaldo(numeroCartao);

        assertTrue(actualSaldo.isEmpty());
    }
}