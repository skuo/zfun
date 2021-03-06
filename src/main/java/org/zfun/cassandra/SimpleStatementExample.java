package org.zfun.cassandra;

import java.text.SimpleDateFormat;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryTrace;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;

// https://github.com/jeffreyscarpenter/cassandra-guide/blob/master/cassandra-tdg/src/com/cassandraguide/clients/QueryBuilderExample.java
public class SimpleStatementExample {

    public static void main(String[] args) {
        
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1")
                .build();
        
        // create session on the "hotel" keyspace
        Session session = cluster.connect("hotel");
        
        // create a Hotel ID
        String id = "AZ123";
        
        // create parameterized INSERT statement
        SimpleStatement hotelInsert = new SimpleStatement("INSERT INTO hotels (id, name, phone) VALUES (?, ?, ?)",
                id, "Super Hotel at WestWorld", "1-888-999-9999");
        ResultSet hotelInsertResult = session.execute(hotelInsert);
        
        System.out.println(hotelInsertResult);
        System.out.println(hotelInsertResult.wasApplied());
        System.out.println(hotelInsertResult.getExecutionInfo());
        System.out.println(hotelInsertResult.getExecutionInfo().getIncomingPayload());
        
        // create parameterized SELECT statement
        SimpleStatement hotelSelect = new SimpleStatement("SELECT * FROM hotels WHERE id=?", id);
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

        // Trace
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        QueryTrace queryTrace = hotelSelectResult.getExecutionInfo().getQueryTrace();
        System.out.printf("Trace id: %s\n\n", queryTrace.getTraceId());
        System.out.printf("%-42s | %-12s | %-10s \n", "activity",
           "timestamp", "source");
        System.out.println("-------------------------------------------+--------------+------------");
              
        for (QueryTrace.Event event : queryTrace.getEvents()) {
          System.out.printf("%42s | %12s | %10s\n",     
             event.getDescription(),
             dateFormat.format((event.getTimestamp())),
             event.getSource());
        }

        // delete
        SimpleStatement hotelDelete = new SimpleStatement("DELETE FROM hotels WHERE id=?",
                id);
        ResultSet hotelDeleteResult = session.execute(hotelDelete);
        
        System.out.println(hotelDeleteResult);
        System.out.println(hotelDeleteResult.wasApplied());
        System.out.println(hotelDeleteResult.getExecutionInfo());
        System.out.println(hotelDeleteResult.getExecutionInfo().getIncomingPayload());
        
        // close and exit
        cluster.close();
        System.exit(0);
    }
}
