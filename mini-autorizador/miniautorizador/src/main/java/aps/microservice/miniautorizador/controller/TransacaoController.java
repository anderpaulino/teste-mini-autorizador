package aps.microservice.miniautorizador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aps.microservice.miniautorizador.controller.dto.TransacaoDto;
import aps.microservice.miniautorizador.controller.mapper.TransacaoMapper;
import aps.microservice.miniautorizador.usecase.transacao.ProcessTransacaoUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/transacoes")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class TransacaoController {

    ProcessTransacaoUseCase useCase;
    TransacaoMapper transacaoMapper;

    @PostMapping
    public ResponseEntity<String> createTransacao(@RequestBody TransacaoDto transacaoDto) {

        try {
            useCase.execute(transacaoMapper.toEntity(transacaoDto));
        } catch (Exception e) {
            log.warn("Transação não realizada!", transacaoDto.getNumeroCartao(), e);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
    
}
