package com.inauth;

import java.util.Random;

public class InAuth {
    
    // use Random for now.
    Random random = new Random();
    
    private Location randomLocation() {
        // The latitude must be a number between -90 and 90 and the longitude between -180 and 180.
        // random generates between 0.0 and 1.0
        float latitude = random.nextFloat()*180f-90f;
        float longitude = random.nextFloat()*360f-180f;
        return new Location(latitude, longitude);
    }
    
    public void initialLoad() {
        for (int i=0; i<100; i++) {
            Location loc = randomLocation();
            if (loc.validate(loc))
                System.out.println(loc.toString());
            else
                System.out.println("Invalid: " + loc.toString());
        }
    }
    
    public static void main(String[] args) {
        InAuth inAuth = new InAuth();
        inAuth.initialLoad();
    }
}
