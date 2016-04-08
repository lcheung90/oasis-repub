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

        // Getting the title to show on top of the activity , the name is stored in the previous intent on a DEFINED global variable
        // called EXTRA_MARKER. EXTRA_MARKER stores the name of the building that was cliked on the map.
        TextView title = (TextView) findViewById(R.id.title);
        Intent intent = getIntent();
        String newTitle = intent.getStringExtra(MapsActivity.EXTRA_MARKER);
        title.setText(newTitle);
        title.setTextSize(40);


        LinearLayout linearL= (LinearLayout) findViewById(R.id.linearlayout_id);

      // creating buttons dynamically and settin all of them to be clickable
       for(int i =1; i < 4 ; i++ ){

           // Inflating the button into the Linear Layout from the activity_building_info.xml
           View view = getLayoutInflater().inflate(R.layout.button,linearL,false);
           final Button b = (Button) view.findViewById(R.id.custom_button);
           b.setText("Floor " + i);

           // setting the click Listener for the button that is being created
           b.setOnClickListener(
                   new Button.OnClickListener() {
                       public void onClick(View v) {
                           final String floorNumber = (String) b.getText();
                           // starting a new intent and passing the FLoor number on the DEFINED variable EXTRA_FLOOR
                           Intent floorIntent = new Intent(BuildingInfo.this, FloorInfo.class);
                           floorIntent.putExtra(EXTRA_FLOOR, floorNumber);
                           startActivity(floorIntent);
                       }
                   }
           );
        // Adds the new components ( buttons) to the view that is the activity_building_info.xml
        linearL.addView(view);

       }



    }
}

