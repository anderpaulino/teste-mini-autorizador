package aps.microservice.miniautorizador.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aps.microservice.miniautorizador.exception.CartaoInexistenteException;
import aps.microservice.miniautorizador.exception.SaldoInsuficioenteException;
import aps.microservice.miniautorizador.exception.SenhaInvalidaException;
import aps.microservice.miniautorizador.model.Cartao;
import aps.microservice.miniautorizador.model.Transacao;
import aps.microservice.miniautorizador.repository.CartaoRepository;
import aps.microservice.miniautorizador.repository.TransacaoRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TransacaoService {
    TransacaoRepository transacaoRepository;
    CartaoRepository cartaoRepository;

    @Transactional
    public synchronized boolean createTransacao(Transacao transacao) {

        Cartao cartao = cartaoRepository.findByNumeroCartao(transacao.getNumeroCartao())
            .orElseThrow(CartaoInexistenteException::new);

        //TODO: Seria possível remover os próximo ifs utilizando methods queries igual usado acima (findByNumeroCartaoAndSenha) mas aumentaria a complexidade.
        if (!cartao.getSenha().equals(transacao.getSenhaCartao())) { 
            throw new SenhaInvalidaException();
        }

        synchronized(cartao) {
            if (cartao.getSaldo().compareTo(transacao.getValor()) < 0) {
                throw new SaldoInsuficioenteException();
            }

            cartao.setSaldo(cartao.getSaldo().subtract(transacao.getValor()));
        }   

        return transacaoRepository.save(transacao) != null;
    } 
}
