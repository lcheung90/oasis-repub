package csci567.project.oasis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.mobilefirstplatform.clientsdk.android.security.googleauthentication.GoogleAuthenticationManager;

public class FloorInfo extends Activity {
    private static final int SCAN_REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_info);

        TextView title = (TextView) findViewById(R.id.Ftitle);
        Intent intent = getIntent();
        String newTitle = intent.getStringExtra(BuildingInfo.EXTRA_FLOOR);
        title.setText(newTitle);
        title.setTextSize(40);

        Button scanButton = (Button) findViewById(R.id.bscan);

        scanButton.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        Intent scanner = new Intent(FloorInfo.this, QRScanActivity.class);
                        startActivityForResult(scanner, 8);
                    }
                }
        );


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
