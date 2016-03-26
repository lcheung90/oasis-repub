package csci567.project.oasis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button buttonLocate = (Button) findViewById(R.id.btn_locate);
        assert buttonLocate != null;
        buttonLocate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                locate();
            }
        });
    }

    public void locate(){
        Intent intent = new Intent(this, MapsActivity.class);
    }

}
