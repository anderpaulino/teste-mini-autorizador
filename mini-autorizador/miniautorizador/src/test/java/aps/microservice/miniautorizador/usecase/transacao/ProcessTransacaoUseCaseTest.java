package aps.microservice.miniautorizador.usecase.transacao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import aps.microservice.miniautorizador.exception.CartaoInexistenteException;
import aps.microservice.miniautorizador.exception.SaldoInsuficioenteException;
import aps.microservice.miniautorizador.exception.SenhaInvalidaException;
import aps.microservice.miniautorizador.model.Cartao;
import aps.microservice.miniautorizador.model.Transacao;
import aps.microservice.miniautorizador.repository.CartaoRepository;
import aps.microservice.miniautorizador.repository.TransacaoRepository;

@ExtendWith(MockitoExtension.class)
class ProcessTransacaoUseCaseTest {

    @Mock
    CartaoRepository cartaoRepository;

    @Mock
    TransacaoRepository transacaoRepository;

    ProcessTransacaoUseCase userCase;

    @BeforeEach
    void setUp() {
        userCase = new ProcessTransacaoUseCase(transacaoRepository, cartaoRepository);
    }

    @Test
    void testCreateTransacao() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("123456");
        cartao.setSenha("1234");
        cartao.setSaldo(new BigDecimal(1000));

        Transacao transacao = new Transacao();
        transacao.setNumeroCartao("123456");
        transacao.setSenhaCartao("1234");
        transacao.setValor(new BigDecimal(100));

        when(cartaoRepository.findByNumeroCartao("123456"))
            .thenReturn(Optional.of(cartao));
        
        when(transacaoRepository.save(transacao))
            .thenReturn(transacao);

        userCase.execute(transacao);
    }

    @Test
    void testCreateTransacao_whenNumeroCartaoInexistente_throwsException() {
        Transacao transacao = new Transacao();
        transacao.setNumeroCartao("1234567890123456");
        transacao.setSenhaCartao("1234");
        transacao.setValor(new BigDecimal(100));

        when(cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao()))
        .thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class, () -> userCase.execute(transacao));
    }

    @Test
    void testCreateTransacao_whenSenhaInvalida_throwsException() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("123456");

        Transacao transacao = new Transacao();
        transacao.setNumeroCartao("1234567890123456");
        transacao.setSenhaCartao("1234");
        transacao.setValor(new BigDecimal(100));

        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao()))
        .thenReturn(Optional.of(cartao));

        assertThrows(SenhaInvalidaException.class, () -> userCase.execute(transacao));
    }

    @Test
    void testCreateTransacao_whenSaldoInsuficiente_throwsException() {
        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("123456");
        cartao.setSaldo(BigDecimal.ZERO);

        Transacao transacao = new Transacao();
        transacao.setNumeroCartao("1234567890123456");
        transacao.setSenhaCartao("123456");
        transacao.setValor(new BigDecimal(0.01));

        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao()))
        .thenReturn(Optional.of(cartao));

        assertThrows(SaldoInsuficioenteException.class, () -> userCase.execute(transacao));
    }

    @Test
    public void testCreateTransacaoSuccessWithConcurrency() throws InterruptedException {
        String numeroCartao = "123456789";
        String senhaCartao = "1234";
        BigDecimal valor = BigDecimal.valueOf(500.0);
        Transacao transacao1 = Transacao.builder().numeroCartao(numeroCartao).senhaCartao(senhaCartao).valor(BigDecimal.valueOf(100.0)).build();
        Cartao cartao = Cartao.builder().numeroCartao(numeroCartao).senha(senhaCartao).saldo(valor).build();
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao()))
        .thenReturn(Optional.of(cartao));

        List<Boolean> list = new ArrayList<>();

        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<10;i++) {
            es.execute(() -> list.add(userCase.execute(transacao1)));
        }
        es.shutdown();
        es.awaitTermination(1, TimeUnit.MINUTES);

        assertEquals(5, list.size());
    }


}