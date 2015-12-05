package ast.kg.qrcode;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.getQR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnectionEnabled()) {startActivity(new Intent(getApplicationContext(), QRCodeScanner.class));}
                else Log.d("MY_LOG","No Connection");
            }
        });
    }
    @Override
    protected void onResume() {super.onResume();}

    @Override
    protected void onPause() {super.onPause();}

    boolean isConnectionEnabled() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); // getting GPS status
        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // getting network status
        return (isGPSEnabled);
    }
}
