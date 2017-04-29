package org.zfun.cassandra;

import java.util.Set;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(keyspace="hotel", name="hotels")
public class Hotel {

    @PartitionKey
    private String id;
    
    @Column(name="address")
    private Address address;

    @Column(name="name")
    private String name;
    
    @Column(name="phone")
    private String phone;
    
    @Column(name="pois")
    private Set<String> pointsOfInterest;
}
