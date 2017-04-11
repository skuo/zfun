package org.zfun.cassandra;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;

public class ObjectMapperExample {

    public static void main(String[] args) {
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
                .build();
        
        // create session on the "hotel" keyspace
        Session session = cluster.connect("hotel");
     
        MappingManager mappingMgr = new MappingManager(session);
        Mapper<Hotel> hotelMapper = mappingMgr.mapper(Hotel.class);
        
        String hotelId = "AZ123";
        // save a new hotel
        Address addr = new Address("7712 E. Broadway Blvd","Glendale", "CA", "92310", "US");
        Set<String> pois = new HashSet<>(Arrays.asList("Universal Studios", "Knott's Berry Farm", "Disneyland"));
        Hotel hotel = new Hotel(hotelId, addr, "1-888-999-9999", "Super Hotel at WestWolrd", pois);
        hotelMapper.save(hotel);
        
        // retrieve from db
        Hotel retrievedHotel = hotelMapper.get(hotelId);
        System.out.println(retrievedHotel.toString());
        
        // delete from db
        hotelMapper.delete(retrievedHotel);
        
        // close and exit
        cluster.close();
        System.exit(0);
    }
}
