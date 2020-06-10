package arnavigation.appsan.com.myapplication;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;

import static com.android.volley.VolleyLog.TAG;

public class NavALPHA extends Activity {

    EditText etSource,etDestination ;
    Button btTrack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav);

        etSource = findViewById(R.id.et_source);
        etDestination = findViewById(R.id.et_destination);
        btTrack = findViewById(R.id.bt_track);


        btTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sSource = etSource.getText().toString().trim();
                String sDestination = etDestination.getText().toString().trim();

                if (sSource.equals("")&& sDestination.equals("")){
                    Toast.makeText(getApplicationContext(),"Enter both locations",Toast.LENGTH_SHORT).show();
                }else{

                    DisplayTrack(sSource,sDestination);
                }

            }
        });


    }

    private void DisplayTrack(String sSource, String sDestination) {

        try {
            Uri uri = Uri.parse("http://www.google.co.in/maps/dir/"+ sSource + "/" + sDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setPackage("com.google.android.apps.maps");

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }catch(ActivityNotFoundException e){
            Uri uri = Uri.parse("https:/play.google.com/store/apps/details?id=com.google.android.apps.maps");

            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);


        }
    }
}
