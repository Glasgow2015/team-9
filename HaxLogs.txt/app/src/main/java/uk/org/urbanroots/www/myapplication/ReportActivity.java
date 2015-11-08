package uk.org.urbanroots.www.myapplication;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
public class ReportActivity extends Activity implements OnClickListener {
    EditText editTextSubject, editTextMessage;
    TextView longitudetxt;
    TextView latitudetxt;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;
    private  String pamp;
    Button btnSend, btnAttachment;
    String subject, message, attachmentFile;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    int columnIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_report);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        btnAttachment = (Button) findViewById(R.id.buttonAttachment);
        TextView pampStr = (TextView) findViewById(R.id.textView3);
        btnSend = (Button) findViewById(R.id.buttonSend);
        btnSend.setOnClickListener(this);
        pamp= pampStr.getText().toString();
       // pampStr=pamp.getText();
//
        btnAttachment.setOnClickListener(this);
        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);
        longitudetxt = (TextView) findViewById(R.id.longitude);
        latitudetxt = (TextView) findViewById(R.id.langitude);
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Date is set!", Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day
            showDate(arg1, arg2+1, arg3);
        }
    };
    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            URI = selectedImage;
        }
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getApplicationContext(), myDateListener, year, month, day);
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
    }
    @Override
    public void onClick(View v) {
        if (v == btnAttachment) {
            openGallery();
        }
        if (v == btnSend) {
            try {


                subject = editTextSubject.getText().toString();
                message = editTextMessage.getText().toString();
                //TextView pamp = (TextView) findViewById(R.id.textView3);
                //String pampStr=pamp.getText().toString();
                //message.append(pampStr);



                //  myFile.getAbsolutePath();


                final Intent emailIntent = new Intent(
                        android.content.Intent.ACTION_SEND);
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[] { "orestisssss@gmail.com" });
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        subject);
                if (URI != null) {
                    emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
                }
                emailIntent
                        .putExtra(android.content.Intent.EXTRA_TEXT, message);
                this.startActivity(Intent.createChooser(emailIntent,
                        "Sending email..."));

            } catch (Throwable t) {
                Toast.makeText(this,
                        "Request failed try again: " + t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);
    }
    @TargetApi(Build.VERSION_CODES.M)

   /* public void getLocation() {
        double longitude;
        double latitude;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       android.location.Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        latitudetxt.setText(latitude + "");
        longitudetxt.setText(longitude + "");
    }
    */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}