package csci567.project.oasis;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Vector;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Bind;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap googleMap;

    private GoogleMap mMap;
    private LatLngBounds CHICO = new LatLngBounds(
            new LatLng(39.725193, -121.848341), new LatLng(39.734038, -121.843255));

    private static final LatLng LIBRARY = new LatLng(39.728123, -121.846275);
    private static final LatLng GLEEN = new LatLng(39.729144, -121.846413);
    private static final LatLng OCONNEL = new LatLng(39.727725, -121.847566);
    private static final LatLng YOLO = new LatLng(39.728829, -121.850050);
    private static final LatLng STUDENTCENTER = new LatLng(39.727210, -121.845747);
    public final static String EXTRA_MARKER = "csci567/project/oasis.MARKER";
    private Vector <LatLng> MarkerLat = new Vector<LatLng>() ;
    private Vector <String> MarkerName = new Vector<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
               .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void initializeMarker(){


        MarkerLat.set(0, new LatLng(39.728123, -121.846275));
        MarkerName.set(0,"Library");
        MarkerLat.set(1,new LatLng(39.729144, -121.846413));
        MarkerName.set(1,"Glenn Hall");


    }

//    private void addGoogleMap() {
//        // check if we have got the googleMap already
//        if (googleMap == null) {
//            googleMap = ((SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map)).getMap();
//            googleMap.setOnMarkerClickListener(this);
//            googleMap.setOnMarkerDragListener(this);
//        }
//
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng csuChico = new LatLng(39.73, -121.84);
        mMap.setOnMarkerClickListener(this);
        mMap.addMarker(new MarkerOptions().position(csuChico).title("Marker in Chico State University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(CHICO, 0));
        markers(mMap);


       // mMap.animateCamera(CameraUpdateFactory.zoomIn());
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(30), 2000, null);

    }

    public void markers(GoogleMap googleMap){
        //initializeMarker();

        Marker library = googleMap.addMarker(new MarkerOptions()
                .position(LIBRARY)
                .alpha(0)
                .title("Meriam Library"));
        Marker Glenn = googleMap.addMarker(new MarkerOptions()
                .position(GLEEN)
                .alpha(0)
                .title("Gleen Hall"));
    }


    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(this, BuildingInfo.class);

        String title = marker.getTitle();
        intent.putExtra(EXTRA_MARKER, title);
        //i.putExtra("str1", db.getCulturaName(arg0.getTitle()).getDescription());
        startActivity(intent);
        return false;
    }

}
