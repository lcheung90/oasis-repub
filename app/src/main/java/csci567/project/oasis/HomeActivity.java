package csci567.project.oasis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";


    String[] code = {"650VC2H","B71FP2Z"};
    String[] loc = {"Yolo Hall", "O'Connell"};
    String[] day = {"03/26/16 11:15 A.M.", "03/26/16 1:05 P.M."};









    @OnClick(R.id.scan_button) public void scan() {
        Log.d(TAG, "Scan Button Clicked");
        //Toast.makeText(Context)
    }
    @OnClick(R.id.locate_button) public void locate() {
        Log.d(TAG, "Locate Button Clicked");
        //Toast.makeText(Context)
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        RecyclerView qr_scan_history = (RecyclerView) findViewById(R.id.scan_history);
        RecyclerView.Adapter adapter = new RecyclerAdapter(code, loc, day);
        RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(this);


        qr_scan_history.setLayoutManager(layout_manager);
        qr_scan_history.setHasFixedSize(true);
        qr_scan_history.setAdapter(adapter);


    }
}
