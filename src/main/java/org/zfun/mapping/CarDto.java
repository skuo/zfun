package org.zfun.mapping;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto {
    private String make;
    private int seatCount;
    private String type;
}
