package csci567.project.oasis;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.NoDocumentException;

public class HomeActivity extends AppCompatActivity {

	private TextView barcodeInfo;
    private Button scanButton;

    private CloudantClient client = ClientBuilder.account("lamamafalsa")
            .username("lamamafalsa")
            .password("1234567890")
            .build();
    private Database db = client.database("waterfountains", false);

    private class AsyncTaskRunner extends AsyncTask<String,String, String>{
        private Exception exceptionToBeThrown;

        @Override
        protected String doInBackground(String... params){
            String tmp_id = params[0];
            WaterFountain wfr;
            try {
                wfr = db.find(WaterFountain.class, tmp_id);
                return Short.toString(wfr.getPoints());
            }
            catch(Exception e)
            {
                exceptionToBeThrown = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            // Check if exception exists.
            if (exceptionToBeThrown != null) {
                if (exceptionToBeThrown instanceof NoDocumentException) {
                    Toast.makeText(HomeActivity.this, "Not an Oasis QRcode", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(HomeActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
            }
            else
                barcodeInfo.setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
		scanButton = (Button) findViewById(R.id.btn_scan);
        barcodeInfo = (TextView) findViewById(R.id.tv_debug_qrresult);
        scanButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent scanner = new Intent(HomeActivity.this, QRScanActivity.class);
                        startActivityForResult(scanner,0);
                    }
                }
        );

        Button buttonLocate = (Button) findViewById(R.id.btn_locate);

        buttonLocate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.print("it is listening to the button");
                Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
                System.out.print("it will start an activity");
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            Bundle b = data.getExtras();
            String s = b.getString("info");
            AsyncTaskRunner runner = new AsyncTaskRunner();
            runner.execute(s);
        }
    }
}
