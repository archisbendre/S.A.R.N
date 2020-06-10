package arnavigation.appsan.com.myapplication.utils;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.libraries.places.api.Places;

import java.util.Arrays;


import arnavigation.appsan.com.myapplication.NavActivity;
import arnavigation.appsan.com.myapplication.R;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        String ApiKey = "AIzaSyATDPZetqfKX5-ZikKeVB9pcTr63TYEpyk";

        if (ApiKey.equals("")) {
            Toast.makeText(this, getString(R.string.error_api_key), Toast.LENGTH_LONG).show();
            return;
        }


        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(),ApiKey);
        }





    }

    private void setLaunchActivityClickListener(int source_pick_btn, final Class<NavActivity> navActivityClass) {
        int dest_pick_btn = 0;
        findViewById(source_pick_btn)
                .setOnClickListener(
                        v -> {
                            Intent intent = new Intent(MainActivity.this, navActivityClass);
                            startActivity(intent);

                        }
                );

    }


}
