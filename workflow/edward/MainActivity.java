package com.example.edward.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/*

make a layout with a progress bar based in the action taking place
let the yes trigger its visibility
set its attribues to hover over the orignal layout
 */
public class MainActivity extends AppCompatActivity {

    RelativeLayout layout1;
    String record, No, None, record1, record2, Others;
    ImageView mail, call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout1 = (RelativeLayout) findViewById(R.id.layout1);
        layout1.setVisibility(View.INVISIBLE);
        final EditText otherfield = (EditText) findViewById(R.id.other);
        otherfield.setVisibility(View.INVISIBLE);
        /*mail = (ImageView) findViewById(R.id.email);
        mail.setVisibility(View.INVISIBLE);
        call = (ImageView) findViewById(R.id.call);
        call.setVisibility(View.INVISIBLE);
        TextView t1 = (TextView) findViewById(R.id.text1);
*/

        Spinner check = (Spinner) findViewById(R.id.have1);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.yes_no, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        check.setAdapter(adapter1);
        check.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                record = (String) parent.getSelectedItem();
                if (record.equals("Yes")) {
                    layout1.setVisibility(View.VISIBLE);
                    otherfield.setVisibility(View.INVISIBLE);
                    //company
                    Spinner company = (Spinner) findViewById(R.id.hav2);
                    ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getBaseContext(), R.array.company, android.R.layout.simple_spinner_item);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    company.setAdapter(adapter2);
                    company.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            record1 = (String) parent.getSelectedItem();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    // reason for leaving
                    Spinner reason = (Spinner) findViewById(R.id.hav3);
                    ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getBaseContext(), R.array.reason, android.R.layout.simple_spinner_item);
                    adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    reason.setAdapter(adapter3);
                    reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            record2 = (String) parent.getSelectedItem();
                            if (record2.equals("Others")) {
                                otherfield.setVisibility(View.VISIBLE);
                                record2 = otherfield.getText().toString();
                            } else {

                                otherfield.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });


                    // if its no
                } else if (record.equals("No")) {
                    layout1.setVisibility(View.INVISIBLE);
                    record = No;
                    record1 = None;
                    record2 = None;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*t1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                call.setVisibility(View.VISIBLE);
                mail.setVisibility(View.VISIBLE);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:0706741591"));

                        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);

                    }
                });

                //email
                mail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto","esnabigwaku@gmail.com", null));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Recruiter' name");
                        intent.putExtra(Intent.EXTRA_TEXT, "Cannot login");
                        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
                    }
                });
                return false;
            }
        });*/

    }
}
