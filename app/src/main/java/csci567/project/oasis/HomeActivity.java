package csci567.project.oasis;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.Toast;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

=======
import android.widget.TextView;
>>>>>>> refs/remotes/origin/master


public class HomeActivity extends AppCompatActivity {

<<<<<<< HEAD
    private static final String TAG = "HomeActivity";

    //private GoogleApiClient client;

    //Mock scan history data - until we get QR scan working with a database
    //String[] code = {"650VC2H", "B71FP2Z", "7NFRJSO", "PDJ87OJ", "VN8GRXY", "72OJI2H", "650VC2H"};
    //String[] loc = {"Yolo Hall", "O'Connell Hall", "BMU", "Langdon Hall", "Trinity Hall", "Plumas Hall", "Yolo Hall"};
    //String[] day = {"03/26/16 11:15 A.M.", "03/26/16 1:05 P.M.", "03/26/16 2:35 P.M.",
            //"03/26/16 4:05 P.M.", "03/26/16 5:15 P.M.", "03/25/16 3:30 P.M.", "03/24/16 1:15 P.M."};




    @OnClick(R.id.scan_button)
    public void scan() {
        Log.d(TAG, "Scan Button Clicked");
        //Intent scan = new Intent(HomeActivity.this, )
        //Toast.makeText(Context)
    }

    @OnClick(R.id.locate_button)
    public void locate() {
        Log.d(TAG, "Locate Button Clicked");

        Intent locate = new Intent(HomeActivity.this, MapsActivity.class);
        startActivity(locate);
        //Toast.makeText(Context)
    }



=======
	private TextView barcodeInfo;
    private Button scanButton;
>>>>>>> refs/remotes/origin/master
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

        ButterKnife.bind(this);

        //RecyclerView - one option for displaying our QR scan recent history
        //RecyclerView qr_scan_history = (RecyclerView) findViewById(R.id.scan_history);
        //RecyclerView.Adapter adapter = new RecyclerAdapter(code, loc, day);
        //RecyclerView.LayoutManager layout_manager = new LinearLayoutManager(this);

        //qr_scan_history.setLayoutManager(layout_manager);
        //qr_scan_history.setHasFixedSize(true);
        //qr_scan_history.setAdapter(adapter);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.redeem_rewards) {
            Log.d(TAG, "Rewards Selected");

            Intent redeem = new Intent(HomeActivity.this, RewardActivity.class);
            startActivity(redeem);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


/*
    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://csci567.project.oasis/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Home Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://csci567.project.oasis/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
*/







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Bundle b = data.getExtras();
            String s = b.getString("info");
            barcodeInfo.setText(s);
        }
    }
}
