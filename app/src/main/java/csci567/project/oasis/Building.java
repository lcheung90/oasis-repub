package csci567.project.oasis;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Cheung on 4/9/2016.
 */
public class Building {
    private String _id;
    private String _rev;
    private int floor;


    public Building(){
        _id = null;
        _rev=null;
        floor = 1;
    }

    public int getNumFloors(){
        return floor;
    }


    public String toString() {
        return "{ id: " + _id + ",\nrev: " + _rev + ",\nfloor: " + floor + "}";
    }
}
