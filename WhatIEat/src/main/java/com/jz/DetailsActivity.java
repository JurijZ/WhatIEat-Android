package com.jz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = "Default";

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            message = extras.getString("MY_KEY");
        }

        //String message2 = intent.getStringExtra("MY_KEY");

        // Capture the layout's TextView and set the string as its text
        TextView textView = (TextView) findViewById(R.id.descriptionView);
        textView.setText(message);
    }
}
