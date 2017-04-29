package com.inauth;

import java.util.Random;

public class InAuth {

    // use Random for now.
    Random random = new Random();

    private Location randomLocation() {
        // The latitude must be a number between -90 and 90 and the longitude
        // between -180 and 180.
        // random generates between 0.0 and 1.0
        float latitude = random.nextFloat() * 180f - 90f;
        float longitude = random.nextFloat() * 360f - 180f;
        return new Location(latitude, longitude);
    }

    public void initialLoad() {
        for (int i = 0; i < 10000; i++) {
            Location loc = randomLocation();
            if (loc.validate(loc))
                System.out.println(loc.toString());
            else
                System.out.println("Invalid: " + loc.toString());
        }
    }

    public static void main(String[] args) {
        InAuth inAuth = new InAuth();
        // inAuth.initialLoad();
        
        System.out.println(GeoDistanceSource.distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
        System.out.println(GeoDistanceSource.distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
        System.out.println(GeoDistanceSource.distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");

        System.out.println(GeoDistanceSource.distanceWithHeight(32.9697, -96.80322, 0, 29.46786, -98.53506, 0, "M") + " Miles\n");
        System.out.println(GeoDistanceSource.distanceWithHeight(32.9697, -96.80322, 0, 29.46786, -98.53506, 0, "K") + " Kilometers\n");
        System.out.println(GeoDistanceSource.distanceWithHeight(32.9697, -96.80322, 0, 29.46786, -98.53506, 0, "N") + " Nautical Miles\n");
  
        System.out.println("=== GeoDesy ===");
        System.out.println(GeoDesy.distance(32.9697, -96.80322, 29.46786, -98.53506, "M") + " Miles\n");
        System.out.println(GeoDesy.distance(32.9697, -96.80322, 29.46786, -98.53506, "K") + " Kilometers\n");
        System.out.println(GeoDesy.distance(32.9697, -96.80322, 29.46786, -98.53506, "N") + " Nautical Miles\n");
        
        // LA to SF
        System.out.println("=== LA to SF ===");
        System.out.println(GeoDistanceSource.distance(34.0522f, -118.2437f, 37.7749, -122.4194, "M") + " Miles\n");
        System.out.println(GeoDistanceSource.distanceWithHeight(34.0522f, -118.2437f, 0f, 37.7749, -122.4194, 0f, "M") + " Miles\n");
        System.out.println(GeoDesy.distance(34.0522, -118.2437, 37.7749, -122.4194, "M") + " Miles\n");
    }
}
