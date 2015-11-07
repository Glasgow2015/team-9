package uk.org.urbanroots.www.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class sign_up extends AppCompatActivity {
    String nameStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);





        Button sign_up=(Button)findViewById(R.id.sign_up_button);
        sign_up.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v) {
                        TextView myTextView =
                                (TextView)findViewById(R.id.name_text);
                        Log.d(nameStr,myTextView.getText().toString() );
                        //myTextView.setText("Signed up successfully!");
                    }
                }
        );
    }
}
