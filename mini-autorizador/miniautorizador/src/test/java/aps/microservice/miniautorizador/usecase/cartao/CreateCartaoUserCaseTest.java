package aps.microservice.miniautorizador.usecase.cartao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
class CreateCartaoUserCaseTest {

    @Mock
    CartaoRepository cartaoRepository;

    CreateCartaoUseCase userCase;

    @BeforeEach
    void setUp() {
        userCase = new CreateCartaoUseCase(cartaoRepository);
    }

    @Test
    void testCreateCartao() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("123456");

        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao()))
        .thenReturn(Optional.empty());
        when(cartaoRepository.save(cartao)).thenReturn(cartao);

        Cartao createdCartao = userCase.execute(cartao);

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

        assertThrows(CartaoJaExisteException.class, () -> userCase.execute(cartao));
    }

}