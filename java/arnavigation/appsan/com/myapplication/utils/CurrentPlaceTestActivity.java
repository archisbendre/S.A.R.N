package arnavigation.appsan.com.myapplication.utils;


import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place.Field;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import arnavigation.appsan.com.myapplication.R;
import arnavigation.appsan.com.myapplication.utils.FieldSelector;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;


public class CurrentPlaceTestActivity extends AppCompatActivity {

    private PlacesClient placesClient;
    private TextView responseView;
    private FieldSelector fieldSelector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_nav);

        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(this);

        // Set view objects
        List<Field> placeFields =
                FieldSelector.allExcept(
                        Field.ADDRESS_COMPONENTS,
                        Field.OPENING_HOURS,
                        Field.PHONE_NUMBER,
                        Field.UTC_OFFSET,
                        Field.WEBSITE_URI);
        fieldSelector =
                new FieldSelector(
                        findViewById(R.id.source_pick_btn),
                        findViewById(R.id.dest_pick_btn),
                        placeFields,
                        savedInstanceState);
        responseView = findViewById(R.id.geocoding_viewpager);
        setLoading(false);

        // Set listeners for programmatic Find Current Place
        findViewById(R.id.decode_btn).setOnClickListener((view) -> findCurrentPlace());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        fieldSelector.onSaveInstanceState(bundle);
    }

    /**
     * Fetches a list of {@link PlaceLikelihood} instances that represent the Places the user is
     * most
     * likely to be at currently.
     */
    private void findCurrentPlace() {
        if (ContextCompat.checkSelfPermission(this, permission.ACCESS_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    this,
                    "Both ACCESS_WIFI_STATE & ACCESS_FINE_LOCATION permissions are required",
                    Toast.LENGTH_SHORT)
                    .show();
        }

        // Note that it is not possible to request a normal (non-dangerous) permission from
        // ActivityCompat.requestPermissions(), which is why the checkPermission() only checks if
        // ACCESS_FINE_LOCATION is granted. It is still possible to check whether a normal permission
        // is granted or not using ContextCompat.checkSelfPermission().
        if (checkPermission(ACCESS_FINE_LOCATION)) {
            findCurrentPlaceWithPermissions();
        }
    }

    /**
     * Fetches a list of {@link PlaceLikelihood} instances that represent the Places the user is
     * most
     * likely to be at currently.
     */
    @RequiresPermission(allOf = {ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    private void findCurrentPlaceWithPermissions() {
        setLoading(true);

        FindCurrentPlaceRequest currentPlaceRequest =
                FindCurrentPlaceRequest.newInstance(getPlaceFields());
        Task<FindCurrentPlaceResponse> currentPlaceTask =
                placesClient.findCurrentPlace(currentPlaceRequest);


        currentPlaceTask.addOnFailureListener(
                (exception) -> {
                    exception.printStackTrace();
                    responseView.setText(exception.getMessage());
                });

        currentPlaceTask.addOnCompleteListener(task -> setLoading(false));
    }
    @SuppressLint("WrongViewCast")
    private List<Field> getPlaceFields() {
        if (((CheckBox) findViewById(R.id.source_select_text)).isChecked()) {
            return fieldSelector.getSelectedFields();
        } else {
            return fieldSelector.getAllFields();
        }


    }





    private boolean checkPermission(String permission) {
        boolean hasPermission =
                ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
        if (!hasPermission) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, 0);
        }
        return hasPermission;
    }



    private void setLoading(boolean loading) {
        findViewById(R.id.progressBar_maps).setVisibility(loading ? View.VISIBLE : View.INVISIBLE);
    }
}
