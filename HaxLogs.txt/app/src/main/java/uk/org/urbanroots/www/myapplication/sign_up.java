package uk.org.urbanroots.www.myapplication;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class sign_up extends Activity {
    String name;
    String mobile;
    String email;
    String postcode;
    String interests;
    String availability;
    SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);



        final Button send = (Button) this.findViewById(R.id.sign_up_button);
        send.setOnClickListener(
                new Button.OnClickListener(){

                    public void onClick(View v) {
                        String TAG="";
                      TextView nameStr =
                                (TextView)findViewById(R.id.name_text);
                        //myTextView.setText("Signed up successfully!");
                        TextView mobileStr =
                                (TextView)findViewById(R.id.mobile_text);
                        TextView emailStr =
                                (TextView)findViewById(R.id.email_text);
                        TextView postcodeStr =
                                (TextView)findViewById(R.id.postcode_text);
                        TextView interestStr =
                                (TextView)findViewById(R.id.interests_text);
                        TextView availabilityStr =
                                (TextView)findViewById(R.id.availability_text);
                        name=nameStr.getText().toString();
                        mobile=mobileStr.getText().toString();
                        email=emailStr.getText().toString();
                        postcode=postcodeStr.getText().toString();
                        interests=interestStr.getText().toString();
                        availability=availabilityStr.getText().toString();

                        // Saving data in xml in the background
                        storage = getSharedPreferences(name, 0);
                        SharedPreferences.Editor editor = storage.edit();
                        editor.putString("mobile",mobile);
                        editor.putString("email",email);
                        editor.putString("postcode",postcode);
                        editor.putString("interests",interests);
                        editor.commit();
                        System.out.println(storage.getString("email","no email"));

                        

if (!name.isEmpty() && (!mobile.isEmpty() || (email.contains("@")) ) ) {
    // Enter the sender email user name and password in gmail acc
    final Mail la = new Mail("EmailUserName", "EmailPassword", name, mobile, email, postcode, interests,availability);
    try {
        la.send();
    } catch (Exception e) {
        e.printStackTrace();
    }
    Toast.makeText(getApplicationContext(), "Signed up!", Toast.LENGTH_LONG).show();
}
else
{
    Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_LONG).show();
}
                    }
                }
        );

    }
}
