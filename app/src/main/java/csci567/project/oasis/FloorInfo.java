package csci567.project.oasis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FloorInfo extends Activity {

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
                        startActivityForResult(scanner, 0);
                    }
                }
        );


    }
}
