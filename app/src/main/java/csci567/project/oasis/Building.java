package csci567.project.oasis;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Cheung on 4/9/2016.
 */
public class Building {
    private String name;
    private double lat;
    private double lon;
    private short num_floors;
    public ArrayList<String> floor_ids;

    public Building(){
        name = null;
        lat = 0.0;
        lon = 0.0;
        num_floors = 1;
        floor_ids = new ArrayList<String>();
    }

    public short getNumFloors(){
        return num_floors;
    }

    public ArrayList<String> getFloors(){
        return floor_ids;
    }

    public LatLng getCoords(){
        return new LatLng(lat,lon);
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append("{ name: \"")
          .append(name)
          .append("\", lat: \"")
          .append(lat)
          .append("\", lon: \"")
          .append(lon)
          .append("\", num_floors: \"")
          .append(num_floors)
          .append("\", floors: [");

        for(int i = 0; i < floor_ids.size(); i++){
            sb.append("\"")
              .append(floor_ids.get(i))
              .append("\"");
            if(i != floor_ids.size()-1)
                sb.append(",");
        }
        sb.append("] }");
        return sb.toString();
    }
}
