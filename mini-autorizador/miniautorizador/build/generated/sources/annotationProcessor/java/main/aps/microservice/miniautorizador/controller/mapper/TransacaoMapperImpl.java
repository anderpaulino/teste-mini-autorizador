package aps.microservice.miniautorizador.controller.mapper;

import aps.microservice.miniautorizador.controller.dto.TransacaoDto;
import aps.microservice.miniautorizador.model.Transacao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-31T12:14:55-0400",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 17.0.6 (Homebrew)"
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
