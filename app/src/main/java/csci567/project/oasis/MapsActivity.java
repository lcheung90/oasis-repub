package csci567.project.oasis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {


    private GoogleMap googleMap;

    private GoogleMap mMap;
    private LatLngBounds CHICO = new LatLngBounds(
            new LatLng(39.725193, -121.848341), new LatLng(39.734038, -121.843255));

    private static final LatLng LIBRARY = new LatLng(39.728123, -121.846275);
    private static final LatLng GLEEN = new LatLng(39.729144, -121.846413);
    private static final LatLng OCONNEL = new LatLng(39.727725, -121.847566);
    private static final LatLng YOLO = new LatLng(39.728829, -121.850050);
    private static final LatLng SUTTER = new LatLng(39.730777, -121.848578);
    private static final LatLng BUTTE = new LatLng(39.730067, -121.847339);
    private static final LatLng TEHAMA = new LatLng(39.729969, -121.848423);
    private static final LatLng WHITNEY = new LatLng(39.730554, -121.849114);
    private static final LatLng ALBERT = new LatLng(39.730924, -121.846131);
    private static final LatLng ACKERGYM = new LatLng(39.729977, -121.849785);
    private static final LatLng SHURMERGYM = new LatLng(39.729070, -121.849157);
    private static final LatLng PLUMAS = new LatLng(39.729553, -121.847983);
    private static final LatLng LASSEN = new LatLng(39.730617, -121.847409);
    private static final LatLng HOLT = new LatLng(39.731063, -121.845338);
    private static final LatLng SHASTA = new LatLng(39.731190, -121.847870);
    private static final LatLng COLUSA = new LatLng(39.729610, -121.845724);
    private static final LatLng SISKIYOU = new LatLng(39.729624, -121.844822);
    private static final LatLng BMU = new LatLng(39.728102, -121.845011);
    private static final LatLng ARTCENTER = new LatLng(39.728529, -121.844016);
    private static final LatLng KENDAL = new LatLng(39.729624, -121.844822);
    private static final LatLng TRINITY = new LatLng(39.728948, -121.845368);
    private static final LatLng LAXSON = new LatLng(39.729899, -121.843469);
    private static final LatLng AYRES = new LatLng(39.730365, -121.843541);
    private static final LatLng PHYSICALSCIENCE = new LatLng(39.731149, -121.843456);
    public final static String EXTRA_MARKER = "csci567/project/oasis.MARKER";
    private static final LatLng STUDENTCENTER = new LatLng(39.727210, -121.845747);
    private static final int SCAN_REQUEST_CODE = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(CHICO, 0));
        markers(mMap);


    }

    /**
     * Adding clickable markers to the mMap
     * The coordinates where declared as private static variable in the beginning of this clas
     * */
    public void markers(GoogleMap googleMap){
        Marker library = googleMap.addMarker(new MarkerOptions().position(LIBRARY).alpha(0).title("Meriam Library"));
        Marker Glenn = googleMap.addMarker(new MarkerOptions().position(GLEEN).alpha(0).title("Gleen Hall"));
        Marker ocONNEL = googleMap.addMarker(new MarkerOptions().position(OCONNEL).alpha(0).title("O'Connel"));
        Marker Yolo = googleMap.addMarker(new MarkerOptions().position(YOLO).alpha(0).title("Gleen Hall"));
        Marker Sutter = googleMap.addMarker(new MarkerOptions().position(SUTTER).alpha(0).title("Yolo"));
        Marker Tehama = googleMap.addMarker(new MarkerOptions().position(TEHAMA).alpha(0).title("Tehama"));
        Marker Butte = googleMap.addMarker(new MarkerOptions().position(BUTTE).alpha(0).title("Butte Hall"));
        Marker Whitney = googleMap.addMarker(new MarkerOptions().position(WHITNEY).alpha(0).title("Whitney Hall"));
        Marker Albert = googleMap.addMarker(new MarkerOptions().position(ALBERT).alpha(0).title("Albert"));
        Marker Acker = googleMap.addMarker(new MarkerOptions().position(ACKERGYM).alpha(0).title("Acker Gym"));
        Marker Shumer = googleMap.addMarker(new MarkerOptions().position(SHURMERGYM).alpha(0).title("Shurmer Gym"));
        Marker Plumas = googleMap.addMarker(new MarkerOptions().position(PLUMAS).alpha(0).title("Plumas Hall"));
        Marker Lassen = googleMap.addMarker(new MarkerOptions().position(LASSEN).alpha(0).title("Lassen Hall"));
        Marker Holt = googleMap.addMarker(new MarkerOptions().position(HOLT).alpha(0).title("Holt"));
        Marker Shasta = googleMap.addMarker(new MarkerOptions().position(SHASTA).alpha(0).title("Shasta"));
        Marker Colusa = googleMap.addMarker(new MarkerOptions().position(COLUSA).alpha(0).title("Colusa"));
        Marker Siskiyou = googleMap.addMarker(new MarkerOptions().position(SISKIYOU).alpha(0).title("Siskiyou"));
        Marker Trinity = googleMap.addMarker(new MarkerOptions().position(TRINITY).alpha(0).title("Trinity"));
        Marker Bmu = googleMap.addMarker(new MarkerOptions().position(BMU).alpha(0).title("BMU"));
        Marker ArtCenter = googleMap.addMarker(new MarkerOptions().position(ARTCENTER).alpha(0).title("Art Center"));
        Marker Kendal = googleMap.addMarker(new MarkerOptions().position(KENDAL).alpha(0).title("Kendal"));
        Marker Laxson = googleMap.addMarker(new MarkerOptions().position(LAXSON).alpha(0).title("Laxson"));
        Marker Ayres = googleMap.addMarker(new MarkerOptions().position(AYRES).alpha(0).title("Ayres"));
        Marker PhysicalScience = googleMap.addMarker(new MarkerOptions().position(PHYSICALSCIENCE).alpha(0).title("Physical Science"));
        Marker StudentCenter = googleMap.addMarker(new MarkerOptions().position(STUDENTCENTER).alpha(0).title("Student Center"));
    }

    /**
     *  Once the Marker, that is invisible on the Map, is clicked
     *  it will start another  activity ( intent)
     *  and pass an Extra variable (info) to the next activity, in this case it is the title of the marker ( building name)
     * */
    public boolean onMarkerClick(Marker marker) {

        Intent intent = new Intent(this, BuildingInfo.class);

        String title = marker.getTitle();
        intent.putExtra(EXTRA_MARKER, title);
        startActivityForResult(intent,SCAN_REQUEST_CODE);
        return false;
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCAN_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle b = data.getExtras();
                    String s = b.getString("info");
                    Intent i = new Intent();
                    i.putExtras(b);
                    setResult(RESULT_OK, i);
                    finish();
                }
                break;
        }
    }

}
