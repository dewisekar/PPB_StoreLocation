package com.example.ppb_storelocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Tag;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LocationManager lm;
    private LocationListener ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new lokasiListener();
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED){
            return;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 2, ll);

    }

    private class lokasiListener implements LocationListener{
        private TextView txtLat, txtLong;
        @Override
        public void onLocationChanged(Location location) {
            txtLat = (TextView) findViewById(R.id.text_lat);
            txtLong = (TextView) findViewById(R.id.text_long);

            txtLat.setText(String.valueOf(location.getLatitude()));
            txtLong.setText(String.valueOf(location.getLongitude()));
            sendPost(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            Toast.makeText(getBaseContext(), "GPS Capture:" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    public void sendPost(String latitude, String longitude) {
        ApiInterface apiService =
                RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        Call<Message> call = apiService.saveLonglat(latitude, longitude);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "sukses", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "gagal ngepost", Toast.LENGTH_LONG).show();
                Log.e("kenapa gagal ngepost " + TAG, t.toString());
            }
        });
    }
}


