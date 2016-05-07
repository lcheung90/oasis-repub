package csci567.project.oasis;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private TextView scan_history;
    private Button scanButton;
    private SignInButton signIn;
    private FloatingActionButton signOut;
    private Database db = CloudantSingleton.getInstance().getClient().database("waterfountains", false);
    private Database user_db = CloudantSingleton.getInstance().getClient().database("users",false);
    private static final int SCAN_REQUEST_CODE = 8;
    private static final int AUTH_REQUEST_CODE = 16;
    private static final int PERMISSION_REQUEST_GET_ACCOUNTS = 0;
    private static final String TAG = "Oasis-DEBUG";
    private boolean auth = false;
    private String email = "";
    private User user;

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private Exception exceptionToBeThrown;

        @Override
        protected String doInBackground(String... params) {
            String tmp_id = params[0];
            WaterFountain wfr;
            try {
                wfr = db.find(WaterFountain.class, tmp_id);
                return Short.toString(wfr.getPoints());
            } catch (Exception e) {
                exceptionToBeThrown = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Check if exception exists.
            if (exceptionToBeThrown != null) {
                if (exceptionToBeThrown instanceof NoDocumentException) {
                    Toast.makeText(HomeActivity.this, "Not an Oasis QRcode", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(HomeActivity.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
            } else
                barcodeInfo.setText(result);
        }
    }

    private class AsyncButtonToggle extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            togglebuttons();
            //email = AuthorizationManager.getInstance().getUserIdentity().getDisplayName().toString();
            AsyncDocument asyncDocument = new AsyncDocument();
            asyncDocument.execute();
        }
    }

    private class AsyncDocument extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params){
            try{
                user = user_db.find(User.class,email);
            }
            catch(NoDocumentException e){
                user = new User();
                user.setEmail(email);
                user_db.save(user);
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        scanButton = (Button) findViewById(R.id.btn_scan);
        barcodeInfo = (TextView) findViewById(R.id.tv_debug_qrresult);
        scan_history = (TextView) findViewById(R.id.scan_history);
        signIn = (SignInButton) findViewById(R.id.sign_in_button);
        signOut = (FloatingActionButton) findViewById(R.id.fab);

        try {
            BMSClient.getInstance().initialize(getApplicationContext(),
                    "http://csci567oasis.mybluemix.net",
                    "8b14e585-8a43-41b0-ba37-4746786bbf78");
        } catch (MalformedURLException e) {
            Toast.makeText(HomeActivity.this, "Authentication server configuration error", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            signIn.setClickable(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSION_REQUEST_GET_ACCOUNTS);
        }

        GoogleAuthenticationManager.getInstance().register(this);
        togglebuttons();

        scanButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if(!email.isEmpty()) {
                            Intent scanner = new Intent(HomeActivity.this, QRScanActivity.class);
                            startActivityForResult(scanner, SCAN_REQUEST_CODE);
                        }
                        else
                            //Toast.makeText(HomeActivity.this, "Please login", Toast.LENGTH_LONG).show();
                        new AlertDialog.Builder(HomeActivity.this)
                                .setMessage("Please login to scan")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                }
        );

        signIn.setOnClickListener(
            new SignInButton.OnClickListener() {
                public void onClick(View v) {
                    new Request(BMSClient.getInstance().getBluemixAppRoute() + "/protected", Request.GET).send(HomeActivity.this, HomeActivity.this);
                    Toast.makeText(HomeActivity.this, "login", Toast.LENGTH_SHORT).show();
                }
            }
        );

        signOut.setOnClickListener(
            new FloatingActionButton.OnClickListener() {
                public void onClick(View v) {
                    Toast.makeText(HomeActivity.this, "logout", Toast.LENGTH_SHORT).show();
                    GoogleAuthenticationManager.getInstance().logout(getApplicationContext(), null);
                    togglebuttons();
                    email = "";
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
    protected void onResume() {
        super.onResume();
        togglebuttons();
    }

    private void togglebuttons() {
        auth = AuthorizationManager.getInstance().getCachedAuthorizationHeader() != null;
        if (auth) {
            email = AuthorizationManager.getInstance().getUserIdentity().getDisplayName().toString();
            signIn.setVisibility(View.INVISIBLE);
            signOut.setVisibility(View.VISIBLE);
        } else {
            signOut.setVisibility(View.INVISIBLE);
            signIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCAN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    String s = b.getString("info");
                    AsyncTaskRunner runner = new AsyncTaskRunner();
                    runner.execute(s);
                }
                break;
            default:
                GoogleAuthenticationManager.getInstance()
                        .onActivityResultCalled(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onSuccess(Response response) {
        Log.d(TAG, "onSuccess :: " + response.getResponseText());
        Log.d(TAG, AuthorizationManager.getInstance().getUserIdentity().toString());
        AsyncButtonToggle at = new AsyncButtonToggle();
        at.execute();
    }

    @Override
    public void onFailure(Response response, Throwable t, JSONObject extendedInfo) {
        if (null != t) {
            Log.d(TAG, "onFailure :: " + t.getMessage());
        } else if (null != extendedInfo) {
            Log.d(TAG, "onFailure :: " + extendedInfo.toString());
        } else {
            Log.d(TAG, "onFailure :: " + response.getResponseText());
        }
        Toast.makeText(HomeActivity.this, "Unable to login, try again later", Toast.LENGTH_SHORT).show();
        GoogleAuthenticationManager.getInstance().logout(getApplicationContext(), null);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_GET_ACCOUNTS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    signIn.setClickable(true);

                } else {
                    signIn.setClickable(false);
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent redeem = new Intent(HomeActivity.this, Rewards.class);
            startActivity(redeem);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
