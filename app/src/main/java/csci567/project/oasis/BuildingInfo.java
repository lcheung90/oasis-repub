package csci567.project.oasis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;


public class BuildingInfo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_building_info);


//        LinearLayout ll = (LinearLayout)findViewById(R.id.activity_building_info);
//        int i;
//        for (i = 0; i <=2; i++) {
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
////                    LinearLayout.LayoutParams.MATCH_PARENT,
////                    LinearLayout.LayoutParams.WRAP_CONTENT);
//
//            Button btn = new Button(this);
//            btn.setId(i);
//            btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//
//            btn.setText("Floor " + i);
//            btn.setBackgroundColor(Color.rgb(70, 80, 90));
//            ll.addView(btn);
//        }


        TextView title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String newTitle = intent.getStringExtra(MapsActivity.EXTRA_MARKER);
        title.setText(newTitle);
        title.setTextSize(40);

        Button floor = (Button) findViewById(R.id.b1floor);

        floor.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent scanner = new Intent(BuildingInfo.this, QRScanActivity.class);
                        startActivityForResult(scanner, 0);
                    }
                }
        );



    }
}
