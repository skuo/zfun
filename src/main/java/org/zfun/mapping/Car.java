package org.zfun.mapping;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {

    private String make;
    private int numberOfSeats;
    private CarType type;
}
