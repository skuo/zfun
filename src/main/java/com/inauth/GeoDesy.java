package com.inauth;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

public class GeoDesy {

    public static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        GeodeticCalculator geoCalc = new GeodeticCalculator();

        Ellipsoid reference = Ellipsoid.WGS84;  

        GlobalPosition pointA = new GlobalPosition(lat1, lon1, 0.0); // Point A

        GlobalPosition userPos = new GlobalPosition(lat2, lon2, 0.0); // Point B

        // in Km
        double dist = geoCalc.calculateGeodeticCurve(reference, userPos, pointA).getEllipsoidalDistance() / 1000;
        if (unit == "M") {
            dist = dist / 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.539957;
        }
        return dist;
    }
}
