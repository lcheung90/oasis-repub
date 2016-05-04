package csci567.project.oasis;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Cheung on 4/9/2016.
 */
public class Building {
    private String name;
    private String _id;
    private String _rev;

    private double lat;
    private double lon;
    private int floor;
    private short num_floors;
    public ArrayList<String> floor_ids;

    public Building(){
        _id = null;
        _rev=null;
        floor = 1;
//        lat = 0.0;
//        lon = 0.0;
//        num_floors = 1;
//        floor_ids = new ArrayList<String>();
    }

    public int getNumFloors(){
        return floor;
    }

    public void setFloor(int nFloor){
        this.floor = nFloor;
    }
    public String toString() {
        return "{ id: " + _id + ",\nrev: " + _rev + ",\nfloor: " + floor + "}";
    }
}
