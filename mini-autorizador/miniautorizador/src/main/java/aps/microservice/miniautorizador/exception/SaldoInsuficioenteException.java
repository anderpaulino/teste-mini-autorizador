package aps.microservice.miniautorizador.exception;

import aps.microservice.miniautorizador.exception.category.ExceptionEnum;

public class SaldoInsuficioenteException extends RuntimeException {

    public SaldoInsuficioenteException() {
      super(ExceptionEnum.SALDO_INSUFICIENTE.toString());
    }
  }
