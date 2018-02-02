package com.example.mikolaj.newapplication;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 2018-01-29.
 */

public class Districts {

    String districtName;
    ArrayList<LatLng> list = new ArrayList<>();
    List<DistrictBorderPoints> borderPoints= new ArrayList();
    Polygon polygon;

    public void addPolygon(Polygon polygon){
        this.polygon = polygon;
    }

    public Polygon getPolygon(){
        return polygon;
    }

    public Districts(String districtName) {
        this.districtName = districtName;
    }

    public void addBorderPoint(DistrictBorderPoints point)
    {
        borderPoints.add(point);
    }

    public void addListPoints(LatLng point)
    {
        list.add(point);
    }

    public String getDistrictName() {
        return districtName;
    }

    public ArrayList<LatLng> getList() {
        return list;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public List<DistrictBorderPoints> getBorderPoints() {
        return borderPoints;
    }

    public void setBorderPoints(List<DistrictBorderPoints> borderPoints) {
        this.borderPoints = borderPoints;
    }

    public static boolean isPointInPolygon(LatLng tap, ArrayList<LatLng> vertices) {
        int intersectCount = 0;
        for (int j = 0; j < vertices.size() - 1; j++) {
            if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
                intersectCount++;
            }
        }

        return ((intersectCount % 2) == 1); // odd = inside, even = outside;
    }

    private static boolean rayCastIntersect(LatLng tap, LatLng vertA, LatLng vertB) {

        double aY = vertA.latitude;
        double bY = vertB.latitude;
        double aX = vertA.longitude;
        double bX = vertB.longitude;
        double pY = tap.latitude;
        double pX = tap.longitude;

        if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
                || (aX < pX && bX < pX)) {
            return false; // a and b can't both be above or below pt.y, and a or
            // b must be east of pt.x
        }

        double m = (aY - bY) / (aX - bX); // Rise over run
        double bee = (-aX) * m + aY; // y = mx + b
        double x = (pY - bee) / m; // algebra is neat!

        return x > pX;
    }

}
