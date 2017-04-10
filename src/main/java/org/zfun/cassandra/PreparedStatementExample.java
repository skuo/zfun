package org.zfun.cassandra;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class PreparedStatementExample {

    public static void main(String[] args) {
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
            //.withCredentials("steve", "i6XJsj!k#9")
            .build();
        
        // create session on the "hotel" keyspace"
        Session session = cluster.connect("hotel");
        
        // create a Hotel ID
        String id="AZ123";

        // -------------------------------------
        // create parameterized INSERT statement
        PreparedStatement hotelInsertPrepared = session.prepare(
                "INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)");
        BoundStatement hotelInsertBound = hotelInsertPrepared.bind(
                id, "Super Hotel at WestWorld", "1-888-999-9999");
        ResultSet hotelInsertResult = session.execute(hotelInsertBound);
        
        System.out.println(hotelInsertResult);
        System.out.println(hotelInsertResult.wasApplied());
        System.out.println(hotelInsertResult.getExecutionInfo());
        System.out.println(hotelInsertResult.getExecutionInfo().getIncomingPayload());
        
        // -------------------------------------
        // create parameterized SELECT statement
        PreparedStatement hotelSelectPrepared = session.prepare(
                "SELECT * FROM hotels WHERE id=?");
        BoundStatement hotelSelectBound = hotelSelectPrepared.bind(id);
        ResultSet hotelSelectResult = session.execute(hotelSelectBound);
        
        // result metadata
        System.out.println(hotelSelectResult);
        System.out.println(hotelSelectResult.wasApplied());
        System.out.println(hotelSelectResult.getExecutionInfo());
        System.out.println(hotelSelectResult.getExecutionInfo().getIncomingPayload());
        
        // print results
        for (Row row : hotelSelectResult) {
            System.out.format("id: %s, name: %s, phone: %s\n", row.getString("id"), 
                row.getString("name"), row.getString("phone"));
        }
        
        // -------------------------------------
        // delete
        PreparedStatement hotelDelete = session.prepare("DELETE FROM hotels WHERE id=?");
        BoundStatement hotelDeleteBound = hotelDelete.bind(id);
        ResultSet hotelDeleteResult = session.execute(hotelDeleteBound);
        
        System.out.println(hotelDeleteResult);
        System.out.println(hotelDeleteResult.wasApplied());
        System.out.println(hotelDeleteResult.getExecutionInfo());
        System.out.println(hotelDeleteResult.getExecutionInfo().getIncomingPayload());
        
        // close and exit
        cluster.close();
        System.exit(0);
    }
    
}
