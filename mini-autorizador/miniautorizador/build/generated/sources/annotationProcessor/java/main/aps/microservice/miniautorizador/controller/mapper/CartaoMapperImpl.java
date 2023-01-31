package aps.microservice.miniautorizador.controller.mapper;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.model.Cartao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T12:33:00-0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.6 (Homebrew)"
)
@Component
public class CartaoMapperImpl implements CartaoMapper {

    @Override
    public Cartao toEntity(CartaoDto dto) {
        if ( dto == null ) {
            return null;
        }

        Cartao.CartaoBuilder cartao = Cartao.builder();

        cartao.numeroCartao( dto.getNumeroCartao() );

        cartao.senha( toMd5(dto.getSenha()) );

        return cartao.build();
    }

    @Override
    public CartaoDto toDto(Cartao entity) {
        if ( entity == null ) {
            return null;
        }

        CartaoDto.CartaoDtoBuilder cartaoDto = CartaoDto.builder();

        cartaoDto.numeroCartao( entity.getNumeroCartao() );
        cartaoDto.senha( entity.getSenha() );

        return cartaoDto.build();
    }
}
