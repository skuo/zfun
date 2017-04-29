package org.zfun.cassandra;

import java.util.concurrent.ExecutionException;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;

public class AsyncSimpleStatement {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
                .build();
        
        // create session on the "hotel" keyspace
        Session session = cluster.connect("hotel");
        
        // create a Hotel ID
        String id1 = "AZ123", id2 = "OZ789";
        
        // create parameterized INSERT statements and execute async
        SimpleStatement hotelInsert1 = new SimpleStatement("INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)",
                id1, "Super Hotel at WestWorld", "1-888-999-9999");
        ResultSetFuture hotelInsertRSF1 = session.executeAsync(hotelInsert1);
        SimpleStatement hotelInsert2 = new SimpleStatement("INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)",
                id2, "Super Hotel at Oz", "1-999-888-8888");
        ResultSetFuture hotelInsertRSF2 = session.executeAsync(hotelInsert2);
        System.out.println("Executing two INSERT statements asynchronously");

        ResultSet hotelInsertRS1 = hotelInsertRSF1.get();       
        System.out.println(hotelInsertRS1);
        System.out.println(hotelInsertRS1.wasApplied());
        System.out.println(hotelInsertRS1.getExecutionInfo());
        System.out.println(hotelInsertRS1.getExecutionInfo().getIncomingPayload());

        ResultSet hotelInsertRS2 = hotelInsertRSF2.get();       
        System.out.println(hotelInsertRS2);
        System.out.println(hotelInsertRS2.wasApplied());
        System.out.println(hotelInsertRS2.getExecutionInfo());
        System.out.println(hotelInsertRS2.getExecutionInfo().getIncomingPayload());
        
        // create parameterized SELECT statement
        SimpleStatement hotelSelect = new SimpleStatement("SELECT * FROM hotels WHERE id in (?,?)", id1, id2);
        hotelSelect.enableTracing();
        ResultSet hotelSelectResult = session.execute(hotelSelect);
        
        // result metadata
        System.out.println(hotelSelectResult);
        System.out.println(hotelSelectResult.wasApplied());
        System.out.println(hotelSelectResult.getExecutionInfo());
        System.out.println(hotelSelectResult.getExecutionInfo().getIncomingPayload());
        System.out.println(hotelSelectResult.getExecutionInfo().getQueryTrace());
        
        // print results
        for (Row row : hotelSelectResult) {
            System.out.format("id: %s, name: %s, phone: %s\n\n", row.getString("id"), 
                row.getString("name"), row.getString("phone"));
        }

        // delete
        SimpleStatement hotelDelete1 = new SimpleStatement("DELETE FROM hotels WHERE id=?",
                id1);
        ResultSetFuture hotelDeleteRSF1 = session.executeAsync(hotelDelete1);
        SimpleStatement hotelDelete2 = new SimpleStatement("DELETE FROM hotels WHERE id=?",
                id1);
        ResultSetFuture hotelDeleteRSF2 = session.executeAsync(hotelDelete2);
        System.out.println("Executing two DETELE statements asynchronously");

        ResultSet hotelDeleteRS1 = hotelDeleteRSF1.get();
        System.out.println(hotelDeleteRS1);
        System.out.println(hotelDeleteRS1.wasApplied());
        System.out.println(hotelDeleteRS1.getExecutionInfo());
        System.out.println(hotelDeleteRS1.getExecutionInfo().getIncomingPayload());
        
        ResultSet hotelDeleteRS2 = hotelDeleteRSF2.get();
        System.out.println(hotelDeleteRS2);
        System.out.println(hotelDeleteRS2.wasApplied());
        System.out.println(hotelDeleteRS2.getExecutionInfo());
        System.out.println(hotelDeleteRS2.getExecutionInfo().getIncomingPayload());
        
        // close and exit
        cluster.close();
        System.exit(0);
    }
}
