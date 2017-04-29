package org.zfun.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CarMapperTest {

    @Test
    // A simple test for lombok's @Data and @Builder
    public void testLombok() {
        Car car = new Car.CarBuilder()
                .make("Toyota")
                .build();
        car.setNumberOfSeats(5);
        car.setType(CarType.ECONOMY);
        assertEquals(5, car.getNumberOfSeats());
    }
    
    @Test
    public void shouldMapCarToDto() {
        Car car = new Car.CarBuilder()
                .make("Morris")
                .numberOfSeats(5)
                .type(CarType.FULLSIZE)
                .build();
        
        CarDto carDto = CarMapper.INSTANCE.carToCarDto(car);
        
        assertNotNull(carDto);
        assertEquals(carDto.getMake(), car.getMake());
        assertEquals(carDto.getSeatCount(), car.getNumberOfSeats());
        assertEquals(carDto.getType(), car.getType().toString());
    }
}
