package aps.microservice.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aps.microservice.miniautorizador.exception.CartaoJaExisteException;
import aps.microservice.miniautorizador.model.Cartao;
import aps.microservice.miniautorizador.repository.CartaoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
@Slf4j
public class CartaoService {
    private static final BigDecimal DEFAULT_SALDO_VALUE = new BigDecimal("500.00");
    CartaoRepository cartaoRepository;

    @Transactional
    public Cartao createCartao(Cartao cartao) {
        Optional<Cartao>  cartaoExistente = cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao());

        cartaoExistente.ifPresent(existingCartao -> {
            log.warn("Cartão com o número {} já existe", cartao.getNumeroCartao());
            throw new CartaoJaExisteException(String.format("O Cartão com o número %s já existe em nossa base de dados", cartao.getNumeroCartao()));
        });      

        cartao.setSaldo(DEFAULT_SALDO_VALUE);
        
        return cartaoRepository.save(cartao); 
    }

    @Transactional(readOnly = true)
    public Optional<BigDecimal> getSaldo(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao)
        .map(Cartao::getSaldo);
    }
    
}
