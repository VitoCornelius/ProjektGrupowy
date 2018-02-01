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
    List<LatLng> list = new ArrayList<>();
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

    public List<LatLng> getList() {
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
}
