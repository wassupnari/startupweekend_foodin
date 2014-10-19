package com.foodin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.foodin.R;

/**
 * Created by wassupnari on 10/19/14.
 */
public class ProfileActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_profile);

        setupHeader();
    }

    private void setupHeader() {
        ViewStub stub = (ViewStub) findViewById(R.id.profile_header);
        View view = stub.inflate();

        ImageView back = (ImageView) view.findViewById(R.id.header_back_btn);

        back.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        });

    }
}
