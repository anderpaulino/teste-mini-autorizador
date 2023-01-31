package aps.microservice.miniautorizador.usecase.cartao;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import aps.microservice.miniautorizador.model.Cartao;
import aps.microservice.miniautorizador.repository.CartaoRepository;
import aps.microservice.miniautorizador.usecase.BaseUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(makeFinal=true, level=AccessLevel.PRIVATE)
public class GetCartaoUseCase implements BaseUseCase<String, Optional<BigDecimal>> {
    CartaoRepository cartaoRepository;


    @Transactional(readOnly = true)
    public Optional<BigDecimal> execute(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao)
        .map(Cartao::getSaldo);
    }
    
}
