package aps.microservice.miniautorizador.controller.mapper;

import aps.microservice.miniautorizador.controller.dto.TransacaoDto;
import aps.microservice.miniautorizador.model.Transacao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-30T22:38:12-0400",
    comments = "version: 1.5.3.Final, compiler: Eclipse JDT (IDE) 3.33.0.v20221215-1352, environment: Java 17.0.5 (Eclipse Adoptium)"
)
@Component
public class TransacaoMapperImpl implements TransacaoMapper {

    @Override
    public Transacao toEntity(TransacaoDto dto) {
        if ( dto == null ) {
            return null;
        }

        Transacao.TransacaoBuilder transacao = Transacao.builder();

        transacao.numeroCartao( dto.getNumeroCartao() );
        transacao.valor( dto.getValor() );

        transacao.senhaCartao( toMd5(dto.getSenhaCartao()) );

        return transacao.build();
    }

    @Override
    public TransacaoDto toDto(Transacao entity) {
        if ( entity == null ) {
            return null;
        }

        TransacaoDto.TransacaoDtoBuilder transacaoDto = TransacaoDto.builder();

        transacaoDto.numeroCartao( entity.getNumeroCartao() );
        transacaoDto.senhaCartao( entity.getSenhaCartao() );
        transacaoDto.valor( entity.getValor() );

        return transacaoDto.build();
    }
}
