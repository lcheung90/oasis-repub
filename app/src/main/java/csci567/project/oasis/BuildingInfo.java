package csci567.project.oasis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BuildingInfo extends Activity {

    public final static String EXTRA_FLOOR = "csci567/project/oasis.FLOOR";
    private static final int SCAN_REQUEST_CODE = 8;

    
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
                           startActivityForResult(floorIntent, SCAN_REQUEST_CODE);
                       }
                   }
           );
        // Adds the new components ( buttons) to the view that is the activity_building_info.xml
        linearL.addView(view);

       }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCAN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    String s = b.getString("info");
                    Intent i = new Intent();
                    i.putExtras(b);
                    setResult(RESULT_OK, i);
                    finish();
                }
                break;
        }
    }
}

