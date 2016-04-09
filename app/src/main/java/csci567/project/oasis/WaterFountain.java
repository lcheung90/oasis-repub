package csci567.project.oasis;

/**
 * Created by Cheung on 4/8/2016.
 */
public class WaterFountain {
    private String _id;
    private String _rev;
    private boolean isOperating;
    private short points;

    public WaterFountain(){
        _id = null;
        _rev = null;
        isOperating = true;
        points = -1;
    }

    public String getCode(){
        return _id;
    }

    public boolean getStatus(){
        return isOperating;
    }

    public short getPoints(){
        return points;
    }

    @Override
    public String toString() {
        return "{ id: " + _id + ",\nrev: " + _rev + ",\nisOperating: " + isOperating + ",\npoints: " + points + "}";
    }

}

