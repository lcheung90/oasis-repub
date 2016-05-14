package csci567.project.oasis;

/**
 * Created by Cheung on 5/6/2016.
 */
public class User {
    private String _id;
    private String _rev;
    private short accum_points;

    public User(){
        _id = null;
        _rev = null;
        accum_points = 100;
    }

    public String getEmail(){
        return _id;
    }

    public void setEmail(String email){
        _id = email;
    }

    public short getPoints(){
        return accum_points;
    }

    public void addPoints(short points){
        accum_points += points;
    }

    @Override
    public String toString() {
        return "{ id: " + _id + ",\nrev: " + _rev + ",\naccum_points: " + accum_points + "}";
    }
}
