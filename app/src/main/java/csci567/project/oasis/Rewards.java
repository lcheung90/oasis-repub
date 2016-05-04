package csci567.project.oasis;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by 6661 on 5/3/2016.
 */
public class Rewards extends AppCompatActivity {

    public ImageView reward_konas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rewards);

        reward_konas = (ImageView) findViewById(R.id.reward1);
    }


}
