package org.zfun.cassandra;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@UDT(keyspace="hotel", name="address")
public class Address {

    private String street;
    private String city;
    
    @Field(name="state_or_province")
    private String stateOrProvince;
    
    @Field(name="postal_code")
    private String postalCode;
    
    private String country;
}
