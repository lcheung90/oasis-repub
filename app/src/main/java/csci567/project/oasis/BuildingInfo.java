package csci567.project.oasis;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.NoDocumentException;

public class BuildingInfo extends Activity {

    public final static String EXTRA_FLOOR = "csci567/project/oasis.FLOOR";
    private static final int SCAN_REQUEST_CODE = 8;
    private Exception exceptionToBeThrown;
    private Database db = CloudantSingleton.getInstance().getClient().database("buildings", false);
    private static int numberFloor;
    private int savedFloor;
    private Building clickedBuilding = new Building();

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
//       for(int i =1; i < 4 ; i++ ){
//
//           // Inflating the button into the Linear Layout from the activity_building_info.xml
//           View view = getLayoutInflater().inflate(R.layout.button,linearL,false);
//           final Button b = (Button) view.findViewById(R.id.custom_button);
//           b.setText("Floor " + i);
//
//           // setting the click Listener for the button that is being created
//           b.setOnClickListener(
//                   new Button.OnClickListener() {
//                       public void onClick(View v) {
//                           final String floorNumber = (String) b.getText();
//                           // starting a new intent and passing the FLoor number on the DEFINED variable EXTRA_FLOOR
//                           Intent floorIntent = new Intent(BuildingInfo.this, FloorInfo.class);
//                           floorIntent.putExtra(EXTRA_FLOOR, floorNumber);
//                           startActivityForResult(floorIntent, SCAN_REQUEST_CODE);
//                       }
//                   }
//           );
//        // Adds the new components ( buttons) to the view that is the activity_building_info.xml
//        linearL.addView(view);
//
//       }

        class AsyncTaskRunner extends AsyncTask<String, String, Integer> {
            private Exception exceptionToBeThrown;

            @Override
            protected Integer doInBackground(String... params) {
                String tmp_id = params[0];
                Building buildingCSU;
                try {
                    buildingCSU = db.find(Building.class, tmp_id);
                    return buildingCSU.getNumFloors();
                } catch (Exception e) {
                    exceptionToBeThrown = e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer result) {
                // Check if exception exists.
                if (exceptionToBeThrown != null) {
                    if (exceptionToBeThrown instanceof NoDocumentException) {
                        Toast.makeText(BuildingInfo.this, "Building not in the Database", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(BuildingInfo.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
                } else{
                    LinearLayout linearL= (LinearLayout) findViewById(R.id.linearlayout_id);
                    // creating buttons dynamically and settin all of them to be clickable
                    for(int i =1; i <= result ; i++ ){

                        // Inflating the button into the Linear Layout from the activity_building_info.xml
                        View view = getLayoutInflater().inflate(R.layout.button,linearL,false);
                        final Button b = (Button) view.findViewById(R.id.custom_button);
                        b.setText("Floor " + i);
//                        b.setBackgroundColor(Color.parseColor("ff009688"));
//                        b.setTextColor(Color.parseColor("#fffff"));


                        // setting the click Listener for the button that is being created
                        b.setOnClickListener(
                                new Button.OnClickListener() {
                                    public void onClick(View v) {
                                        final String floorNumber = (String) b.getText();
                                        // starting a new intent and passing the FLoor number on the DEFINED variable EXTRA_FLOOR
                                        Intent floorIntent = new Intent(BuildingInfo.this, FloorInfo.class);
                                        floorIntent.putExtra(EXTRA_FLOOR, floorNumber);
                                        startActivityForResult(floorIntent,SCAN_REQUEST_CODE);
                                    }
                                 }
                        );
                        // Adds the new components ( buttons) to the view that is the activity_building_info.xml
                        linearL.addView(view);

                    }

                }

            }
        }

        class AsyncButtonToggle extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }

        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(newTitle);
       // Toast.makeText(BuildingInfo.this, "FLoor:" + clickedBuilding.getNumFloors(), Toast.LENGTH_SHORT).show();
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
