package com.algohary.ropt_driver.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.algohary.ropt_driver.Models.Modelinfo;
import com.algohary.ropt_driver.Models.Modelinfo_driver;
import com.algohary.ropt_driver.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int LOCATION_REQUEST = 500;
    int state = 1;
    Button startTrip;
    ArrayList<LatLng> path;
    String oId;
    LinearLayout linearLayout;
    Button button2;
    ImageView image_map;
    TextView date_order, from, to, distance, price, phone;
    Drawable img;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Modelinfo modelinfo;
    Modelinfo_driver modelinfo_driver;
    private GoogleMap mMap;
    private double[] LatLngStart;
    private double[] LatLngEnd;
    private DatabaseReference databaseReference, reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


        img = getResources().getDrawable(R.drawable.ic_done_green_24dp);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference = FirebaseDatabase.getInstance().getReference("Notifications");

        image_map = findViewById(R.id.image_truck_detail);
        date_order = findViewById(R.id.date_post);
        from = findViewById(R.id.from_detail);
        to = findViewById(R.id.to_detail);
        distance = findViewById(R.id.distance_detail);
        price = findViewById(R.id.price_detail);
        phone = findViewById(R.id.phone_detail);

        startTrip = findViewById(R.id.button_startTrip);
        startTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startTrip.getText() == "Start trip") {

                    Toast.makeText(MapsActivity.this, startTrip.getText(), Toast.LENGTH_SHORT).show();
                    String timesStamp = String.valueOf(System.currentTimeMillis());
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("cId", firebaseUser.getUid());
                    hashMap.put("cName", modelinfo_driver.getFull_name());
                    hashMap.put("cPhone", modelinfo_driver.getPhone_num());
                    hashMap.put("nCar", modelinfo_driver.getTruck_type());
                    hashMap.put("nId", timesStamp);
                    hashMap.put("nStatus", "Accepted");
                    hashMap.put("nTime", timesStamp);
                    hashMap.put("oId", modelinfo.getoId());
                    hashMap.put("uId", modelinfo.getuId());
                    databaseReference.child(timesStamp).setValue(hashMap);
                    ///////////////////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////////////////
                    reference.child(oId).child("oStatus").setValue("Accepted...");
                    reference.child(oId).child("cId").setValue(firebaseUser.getUid());

                } else {
                    String timesStamp = String.valueOf(System.currentTimeMillis());
                    HashMap<Object, String> hashMap = new HashMap<>();
                    hashMap.put("cId", firebaseUser.getUid());
                    hashMap.put("cName", modelinfo_driver.getFull_name());
                    hashMap.put("cPhone", modelinfo_driver.getPhone_num());
                    hashMap.put("nCar", modelinfo_driver.getTruck_type());
                    hashMap.put("nId", timesStamp);
                    hashMap.put("nStatus", "Finished");
                    hashMap.put("nTime", timesStamp);
                    hashMap.put("oId", modelinfo.getoId());
                    hashMap.put("uId", modelinfo.getuId());
                    databaseReference.child(timesStamp).setValue(hashMap);

                    reference.child(oId).child("oStatus").setValue("Finished");
                }
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        path = new ArrayList<>();
        //Toast.makeText(this, getIntent().getStringExtra("oId"), Toast.LENGTH_SHORT).show();
        linearLayout = findViewById(R.id.linear);
        button2 = findViewById(R.id.button_expand);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (state) {
                    case 1:
                        linearLayout.setVisibility(View.GONE);
                        state = 2;
                        cheackDrawable();
                        break;
                    case 2:
                        linearLayout.setVisibility(View.VISIBLE);
                        state = 1;
                        cheackDrawable();
                        break;
                }

            }
        });
        oId = getIntent().getStringExtra("oId");
        cheackDrawable();
        get_driver_info();
        get_inf_order();

    }

    private void cheackDrawable() {
        if (linearLayout.getVisibility() == View.VISIBLE) {
            button2.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_expand_more_coloraccent_24dp), null);
        } else {
            button2.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_expand_less_coloraccent_24dp), null);
        }
    }

    private void get_driver_info() {
        DatabaseReference databaseReference_driver = FirebaseDatabase.getInstance().getReference("Drivers");

        databaseReference_driver.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelinfo_driver = dataSnapshot.child(firebaseUser.getUid()).getValue(Modelinfo_driver.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void get_inf_order() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(oId).exists()) {
                    modelinfo = dataSnapshot.child(oId).getValue(Modelinfo.class);
                    assert modelinfo != null;
                    LatLngStart = latlng_start(modelinfo.getoStart());
                    LatLngEnd = latlng_End(modelinfo.getoEnd());
                    if (Objects.equals(modelinfo.getoStatus(), "Accepted...")) {
                        phone.setVisibility(View.VISIBLE);
                        startTrip.setText(R.string.finish_trip);
                        startTrip.setBackgroundResource(R.drawable.button_default_red);
                    } else if (Objects.equals(modelinfo.getoStatus(), "Waiting...")) {
                        startTrip.setText("Start trip");
                        startTrip.setBackgroundResource(R.drawable.button_default);
                    } else {
                        startTrip.setText(R.string.completed);
                        startTrip.setEnabled(false);
                        startTrip.setClickable(false);
                        startTrip.setBackgroundResource(R.drawable.button_default_gray);
                        startTrip.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, img, null);
                    }
                    date_order.setText(getDate(Long.parseLong(modelinfo.getoTime())));
                    phone.setText(modelinfo.getuPhone());
                    price.setText(modelinfo.getoPrice());
                    distance.setText(modelinfo.getoDis());
                    to.setText(modelinfo.getoTo());
                    from.setText(modelinfo.getoFrom());

                    if (Objects.equals(modelinfo.getoCar(), "1")) {
                        try {
                            Picasso.get().load(R.drawable.truck_1).placeholder(R.drawable.truck_1).into(image_map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (Objects.equals(modelinfo.getoCar(), "2")) {
                        try {
                            Picasso.get().load(R.drawable.truck_2).placeholder(R.drawable.truck_2).into(image_map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (Objects.equals(modelinfo.getoCar(), "3")) {
                        try {
                            Picasso.get().load(R.drawable.truck_3).placeholder(R.drawable.truck_3).into(image_map);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // mMap.setMyLocationEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        //Toast.makeText(this, latlngh1[0], Toast.LENGTH_SHORT).show();


        // path.add(new LatLng(LatLngStart[0], LatLngStart[1]));
        // path.add(new LatLng(LatLngEnd[0], LatLngEnd[1]));

        // LatLng ostart = new LatLng(LatLngStart[0], LatLngStart[1]);
        // LatLng oend = new LatLng(LatLngEnd[0], LatLngEnd[1]);


        // mMap.addMarker(new MarkerOptions().position(ostart).title("Start").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        //mMap.addMarker(new MarkerOptions().position(oend).title("End"));
        /*if (path.size() == 2) {
            //Toast.makeText(this, ""+path.size(), Toast.LENGTH_SHORT).show();
            String url = getRequestUrl(path.get(0), path.get(1));
            TaskRequestDirections taskRequestDirections = new TaskRequestDirections();
            taskRequestDirections.execute(url);
        }*/

        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ostart, 13));

    }

    private double[] latlng_End(String latLngEnd) {
        String[] lat_lng0 = latLngEnd.split("\\(");
        String[] lat_lng1 = lat_lng0[1].split("\\)");
        String[] lat_lng2 = lat_lng1[0].split(",");
        double[] lat_lng = new double[2];
        lat_lng[0] = Double.parseDouble(lat_lng2[0]);
        lat_lng[1] = Double.parseDouble(lat_lng2[1]);
        return lat_lng;
    }

    private double[] latlng_start(String latLngStart) {
        String[] lat_lng0 = latLngStart.split("\\(");
        String[] lat_lng1 = lat_lng0[1].split("\\)");
        String[] lat_lng2 = lat_lng1[0].split(",");
        double[] lat_lng = new double[2];
        lat_lng[0] = Double.parseDouble(lat_lng2[0]);
        lat_lng[1] = Double.parseDouble(lat_lng2[1]);

        return lat_lng;
    }


   /* private String getRequestUrl(LatLng origin, LatLng dest) {
        //Value of origin
        String str_org = "origin=" + origin.latitude + "," + origin.longitude;
        //Value of destination
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        //Set value enable the sensor
        String sensor = "sensor=false";
        //Mode for find direction
        String mode = "mode=driving";
        //Build the full param
        String param = str_org + "&" + str_dest + "&" + mode;
        //Output format
        String output = "json";
        //Create url to request
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + param + "&key=AIzaSyBYJdokp0OpRsTV024pDeehqUh0WyjnL7w";
        return url;

    }*/

    private String requestDirection(String reqUrl) throws IOException {
        String responseString = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(reqUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();

            //Get the response result
            inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer stringBuffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            responseString = stringBuffer.toString();
            bufferedReader.close();
            inputStreamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            httpURLConnection.disconnect();
        }
        return responseString;
    }


    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;
        }
    }*/

    private String getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Date d = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy, hh:mm a");
        return simpleDateFormat.format(d);

    }



  /*  public class TaskRequestDirections extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String responseString = "";
            try {
                responseString = requestDirection(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Parse json here
            TaskParser taskParser = new TaskParser();
            taskParser.execute(s);
        }
    }*/

   /* public class TaskParser extends AsyncTask<String, Void, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject = null;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                DirectionsParser directionsParser = new DirectionsParser();
                routes = directionsParser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

       /* @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            //Get list route and display it into the map

            ArrayList points = null;

            PolylineOptions polylineOptions = null;

            for (List<HashMap<String, String>> path : lists) {
                points = new ArrayList();
                polylineOptions = new PolylineOptions();

                for (HashMap<String, String> point : path) {
                    double lat = Double.parseDouble(point.get("lat"));
                    double lon = Double.parseDouble(point.get("lon"));

                    points.add(new LatLng(lat, lon));
                }

                polylineOptions.addAll(points);
                polylineOptions.width(15);
                polylineOptions.color(Color.BLUE);
                polylineOptions.geodesic(true);
            }

            if (polylineOptions != null) {
                mMap.addPolyline(polylineOptions);
            } else {
                Toast.makeText(getApplicationContext(), "Direction not found!", Toast.LENGTH_SHORT).show();
            }

        }*/
}


