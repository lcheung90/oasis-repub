package csci567.project.oasis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Vector;


public class BuildingInfo extends Activity {

    public final static String EXTRA_FLOOR = "csci567/project/oasis.FLOOR";



    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_info);

        TextView title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String newTitle = intent.getStringExtra(MapsActivity.EXTRA_MARKER);
        title.setText(newTitle);
        title.setTextSize(40);


        LinearLayout linearL= (LinearLayout) findViewById(R.id.linearlayout_id);


       for(int i =0; i < 4 ; i++ ){
           //View view = getLayoutInflater().inflate(R.layout.button, null);


           View view = getLayoutInflater().inflate(R.layout.button,linearL,false);
           Button b = (Button) view.findViewById(R.id.custom_button);
           b.setText("Floor " + i );
             linearL.setId(i);


        linearL.addView(view);

       }


//
//        Button floor = (Button) findViewById(R.id.b1floor);
//        final String floorNumber = (String) floor.getText();
//
//        floor.setOnClickListener(
//                new Button.OnClickListener() {
//                    public void onClick(View v) {
//
//                        Intent floorIntent = new Intent(BuildingInfo.this, FloorInfo.class);
//                        floorIntent.putExtra(EXTRA_FLOOR, floorNumber);
//                        startActivity(floorIntent);
//                    }
//                }
//        );




    }
}

