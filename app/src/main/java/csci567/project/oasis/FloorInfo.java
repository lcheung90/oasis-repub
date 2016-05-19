package csci567.project.oasis;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudant.client.api.Database;
import com.cloudant.client.org.lightcouch.NoDocumentException;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;
import com.ibm.mobilefirstplatform.clientsdk.android.security.api.AuthorizationManager;
import com.ibm.mobilefirstplatform.clientsdk.android.security.googleauthentication.GoogleAuthenticationManager;

import org.json.JSONObject;

public class FloorInfo extends Activity implements ResponseListener {
    private static final int SCAN_REQUEST_CODE = 8;
    private boolean auth = AuthorizationManager.getInstance().getCachedAuthorizationHeader() != null;
    private static final String TAG = "Oasis-DEBUG";
    private Database user_db = CloudantSingleton.getInstance().getClient().database("users",false);
    private Database direction = CloudantSingleton.getInstance().getClient().database("floors",false);
    private String email = "";
    private User user;
    private Floor aux;
    private Floor flr;
    private TextView direc;

    private class AsyncDocument extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params){
            try{
                if(!email.isEmpty())
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

    private class AsyncDirections extends AsyncTask<String, String, String> {
        private Exception exceptionToBeThrown;

        @Override
        protected String doInBackground(String... params) {
            String tmp_id = params[0];
            WaterFountain wfr;
            try {
                flr = direction.find(Floor.class, tmp_id);
                return flr.getDirection();
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
                    Toast.makeText(FloorInfo.this, "Not there", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(FloorInfo.this, "Something went wrong, try again", Toast.LENGTH_SHORT).show();
            } else{
                //Toast.makeText(FloorInfo.this, "result: " + result, Toast.LENGTH_SHORT).show();
                direc.setText(result);
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_info);

        TextView title = (TextView) findViewById(R.id.Ftitle);
        direc = (TextView) findViewById(R.id.directions);
        Intent intent = getIntent();
        String newTitle = intent.getStringExtra(BuildingInfo.EXTRA_FLOOR);
        title.setText(newTitle);
        title.setTextSize(40);


        GoogleAuthenticationManager.getInstance().register(this);
        Button scanButton = (Button) findViewById(R.id.bscan);

        AsyncDirections asyncDirections = new AsyncDirections();
        asyncDirections.execute(newTitle);


        scanButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (auth) {
                            Intent scanner = new Intent(FloorInfo.this, QRScanActivity.class);
                            startActivityForResult(scanner, SCAN_REQUEST_CODE);
                        } else {
                            new AlertDialog.Builder(FloorInfo.this)
                                    .setMessage("Please login before scanning")
                                    .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            new Request(BMSClient.getInstance().getBluemixAppRoute() + "/protected", Request.GET).send(FloorInfo.this, FloorInfo.this);
                                            Toast.makeText(FloorInfo.this, "login", Toast.LENGTH_SHORT).show();
                                        }
                                    }).show();
                        }
                    }
                });
    }

    @Override
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
        email = AuthorizationManager.getInstance().getUserIdentity().getDisplayName();
        auth = AuthorizationManager.getInstance().getCachedAuthorizationHeader() != null;
        AsyncDocument at = new AsyncDocument();
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
        Toast.makeText(FloorInfo.this, "Unable to login, try again later", Toast.LENGTH_SHORT).show();
        GoogleAuthenticationManager.getInstance().logout(getApplicationContext(), null);

    }
}
