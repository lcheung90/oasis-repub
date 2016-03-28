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


}
