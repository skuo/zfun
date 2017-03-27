package org.zfun.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);
    
    @Mapping(source="numOfSeats", target="seatCount")
    CarDto carToCarDto(Car car);
}
