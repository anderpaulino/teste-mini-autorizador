package aps.microservice.miniautorizador.controller.mapper;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.model.Cartao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-30T19:41:49-0400",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
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
