package com.developer.arun.androidappdevelopmenttutorails;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Email extends AppCompatActivity {
    private EditText meditto;
    private EditText meditsubject;
    private EditText meditmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        meditto=(EditText) findViewById(R.id.editview1);
        meditsubject=(EditText) findViewById(R.id.editview2);
        meditmessage=(EditText) findViewById(R.id.editview3);
        Button button=(Button) findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }
    private void sendMail(){
        String recipientList=meditto.getText().toString();
        String[] recipients=recipientList.split(",");

        String subject=meditsubject.getText().toString();
        String message=meditmessage.getText().toString();

        Intent intent=new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto","",null));
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        startActivity(Intent.createChooser(intent,"send email..."));
    }
}
