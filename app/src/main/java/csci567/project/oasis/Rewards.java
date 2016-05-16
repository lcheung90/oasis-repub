package csci567.project.oasis;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class Rewards extends AppCompatActivity {

    public ImageView reward_konas, reward_kinders;
    public TextView konas_reward, kinders_reward;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rewards);

        reward_konas = (ImageView) findViewById(R.id.reward1);
        konas_reward = (TextView) findViewById(R.id.konas_reward);

        reward_kinders = (ImageView) findViewById(R.id.reward2);
        kinders_reward = (TextView) findViewById(R.id.kinders_reward);

    }


}
