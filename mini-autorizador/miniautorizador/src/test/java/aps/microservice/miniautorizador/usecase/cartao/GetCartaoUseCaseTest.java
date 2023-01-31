package aps.microservice.miniautorizador.usecase.cartao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import aps.microservice.miniautorizador.model.Cartao;
import aps.microservice.miniautorizador.repository.CartaoRepository;

@ExtendWith(MockitoExtension.class)
class GetCartaoUseCaseTest {

    @Mock
    CartaoRepository cartaoRepository;

    GetCartaoUseCase userCase;

    @BeforeEach
    void setUp() {
        userCase = new GetCartaoUseCase(cartaoRepository);
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

        Optional<BigDecimal> actualSaldo = userCase.execute(numeroCartao);

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

        Optional<BigDecimal> actualSaldo = userCase.execute(numeroCartao);

        assertTrue(actualSaldo.isEmpty());
    }
}