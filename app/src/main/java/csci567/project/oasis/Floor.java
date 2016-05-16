package csci567.project.oasis;

/**
 * Created by Anna Maule on 5/13/2016.
 */
public class Floor {
    private String _id;
    private String _rev;
    private String direction;


    public Floor() {
        _id = null;
        _rev = null;
        direction = null;
    }

    String getDirection(){
        return direction;
    }

    void setDirection(String d){
        direction = d;
    }

    public String toString() {
        return "{ id: " + _id + ",\nrev: " + _rev + ",\ndirection: " + direction + "}";
    }
}


