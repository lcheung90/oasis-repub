package csci567.project.oasis;

/**
 * Created by Cheung on 4/8/2016.
 */
public class WaterFountain {
    private String qr_code;
    private boolean isOperating;

    public WaterFountain(){
        qr_code = null;
        isOperating = true;
    }

    public String getCode(){
        return qr_code;
    }

    public boolean getStatus(){
        return isOperating;
    }
}

