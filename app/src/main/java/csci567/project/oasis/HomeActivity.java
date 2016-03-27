package csci567.project.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class HomeActivity extends AppCompatActivity {

    private TextView barcodeInfo;
    private Button scanButton;

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
                        startActivity(scanner);
                    }
                }
        );
    }

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
