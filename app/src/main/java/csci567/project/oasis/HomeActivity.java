package csci567.project.oasis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    @Bind(R.id.scan_history) ListView qr_scan_history;


    @OnClick(R.id.scan_button) public void scan() {
        Log.d(TAG, "Scan Button Clicked");
    }
    @OnClick(R.id.locate_button) public void locate() {
        Log.d(TAG, "Locate Button Clicked");
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }
}
