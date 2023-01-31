package aps.microservice.miniautorizador.usecase;

public interface BaseUseCase<T, R> {

    public R execute(T t); 
    
}
