package pl.dawid.transportapp.service;

public interface DtoConverter<DTO, ENTITY> {

    DTO convertToDto(ENTITY entity);
    ENTITY convertToEntity(DTO t);
}
