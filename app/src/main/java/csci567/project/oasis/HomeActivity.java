package csci567.project.oasis;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.google.android.gms.common.SignInButton;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.security.api.AuthorizationManager;
import com.ibm.mobilefirstplatform.clientsdk.android.security.googleauthentication.GoogleAuthenticationManager;

import org.json.JSONObject;

import java.net.MalformedURLException;

public class HomeActivity extends AppCompatActivity implements ResponseListener {

	private TextView barcodeInfo;
    private Button scanButton;
    private SignInButton signIn;
    private FloatingActionButton signOut;
    private Database db = CloudantSingleton.getInstance().getClient().database("waterfountains",false);
    private static final int SCAN_REQUEST_CODE = 8;
    private static final int AUTH_REQUEST_CODE = 16;
    private static final String TAG = "Oasis-DEBUG";
    private boolean auth = false;

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
        signIn = (SignInButton) findViewById(R.id.sign_in_button);
        signOut = (FloatingActionButton) findViewById(R.id.fab);

        try {
            BMSClient.getInstance().initialize(getApplicationContext(),
                    "http://csci567oasis.mybluemix.net",
                    "8b14e585-8a43-41b0-ba37-4746786bbf78");
        } catch (MalformedURLException e) {
            Toast.makeText(HomeActivity.this, "Authentication server configuration error", Toast.LENGTH_SHORT).show();
        }

        GoogleAuthenticationManager.getInstance().register(this);

        new Request(BMSClient.getInstance().getBluemixAppRoute() + "/protected", Request.GET).send(this, this);


        scanButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent scanner = new Intent(HomeActivity.this, QRScanActivity.class);
                        startActivityForResult(scanner, SCAN_REQUEST_CODE);
                    }
                }
        );

        signIn.setOnClickListener(
            new SignInButton.OnClickListener() {
                public void onClick(View v) {
                    if(!auth) {
                        GoogleAuthenticationManager.getInstance().register(HomeActivity.this);
                        new Request(BMSClient.getInstance().getBluemixAppRoute() + "/protected", Request.GET).send(HomeActivity.this, HomeActivity.this);
                        Toast.makeText(HomeActivity.this, "login", Toast.LENGTH_SHORT).show();
                        auth = true;
                    }
                }
            }
        );

        signOut.setOnClickListener(
            new FloatingActionButton.OnClickListener() {
                public void onClick(View v) {
                    if(auth) {
                        GoogleAuthenticationManager.getInstance().logout(getApplicationContext(), null);
                        Toast.makeText(HomeActivity.this, "logout", Toast.LENGTH_SHORT).show();
                        auth = false;
                    }
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
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SCAN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    String s = b.getString("info");
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute(s);
                }
                break;
            case AUTH_REQUEST_CODE:
                GoogleAuthenticationManager.getInstance()
                        .onActivityResultCalled(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onSuccess (Response response) {
        Log.d(TAG, "onSuccess :: " + response.getResponseText());
        Log.d(TAG, AuthorizationManager.getInstance().getUserIdentity().toString());
        auth = true;
    }

    @Override
    public void onFailure (Response response, Throwable t, JSONObject extendedInfo) {
        if (null != t) {
            Log.d(TAG, "onFailure :: " + t.getMessage());
        } else if (null != extendedInfo) {
            Log.d(TAG, "onFailure :: " + extendedInfo.toString());
        } else {
            Log.d(TAG, "onFailure :: " + response.getResponseText());
        }
        Toast.makeText(HomeActivity.this,"Unable to login, try again later",Toast.LENGTH_SHORT).show();
    }
}
