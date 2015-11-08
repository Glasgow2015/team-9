package uk.org.urbanroots.www.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_report);
    }

    private void handleOpenUpload(){
        Button buttonOpenMenu = (Button)findViewById(R.id.button3);
        buttonOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent upload = new Intent(ReportActivity.this, ReportActivity.class);
                ReportActivity.this.startActivity(upload);
            }
        });
    }

}
