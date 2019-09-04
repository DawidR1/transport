package pl.dawid.transportapp.service;

import org.springframework.beans.BeanUtils;

public interface DtoConverter<DTO, ENTITY> {

    default DTO convertToDto(ENTITY entity,DTO dto){
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
    default ENTITY convertToEntity(DTO dto, ENTITY entity){
        BeanUtils.copyProperties(dto,entity);
        return entity;
    }
}
