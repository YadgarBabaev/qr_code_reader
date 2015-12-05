//package ast.kg.qrcode;
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.ContentValues;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.text.InputFilter;
//import android.text.InputType;
//import android.text.method.DigitsKeyListener;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.UiSettings;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class M extends FragmentActivity {
//
//    SupportMapFragment mapFragment;
//    GoogleMap map;
//    LocationManager locationManager;
//    Location location;
//    double lat, lng;
//
//    ProgressDialog pDialog;
//    Marker marker;
//    Timer T;
//
//    EditText inputNumber;
//    ImageButton btnGetData;
//    Boolean i = true, clicked1, clicked2;
//    String request_field, phone_number, nickname, pass;
//    int delay, id;
//
//    //server URL
//    String server = "http://lab524.pe.hu/";
////    String server = "http://192.168.10.115/server/";
//
//
//    // JSON Node names
//    private static final String TAG_SUCCESS = "success";
//    String LOG_TAG = "MY_LOG";
//
//    // JSON parser class
//    JSONParser jsonParser = new JSONParser();
//    DBHelper dbHelper;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        inputNumber = (EditText) findViewById(R.id.edNum);
//        btnGetData = (ImageButton) findViewById(R.id.btnSearch);
//
//        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        map = mapFragment.getMap();
//        if (map == null) {
//            finish();
//            return;
//        }
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(42.87442305, 74.61158752), 11);
//        map.animateCamera(cameraUpdate);
//
//        map.setMyLocationEnabled(true);
//        UiSettings settings = map.getUiSettings();
//        settings.setAllGesturesEnabled(true);
//        settings.setZoomControlsEnabled(true);
//
//        init();
//        start_timer();
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            authorization();
////            settings();
//        }
//        if (id == R.id.map_type) {
//            if (i) {map.setMapType(GoogleMap.MAP_TYPE_HYBRID); i = false;}
//            else {map.setMapType(GoogleMap.MAP_TYPE_NORMAL); i = true;}
//        }
//        if (id == R.id.exit)
//        {
//            T.cancel();
//            System.exit(0);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void Tracker() {
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER); // getting GPS status
//        Boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // getting network status
//        if (!isGPSEnabled && !isNetworkEnabled) {
//            Log.d(LOG_TAG, "No Connection");
//        } else {
//            if (isNetworkEnabled && locationManager != null) {
//                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                if (location != null) {
//                    lat = location.getLatitude();
//                    lng = location.getLongitude();
//                }
//            }
//            else if (isGPSEnabled && locationManager != null) {
//                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                if (location != null) {
//                    lat = location.getLatitude();
//                    lng = location.getLongitude();
//                }
//            }
//            else Log.d(LOG_TAG, "Could not to find ");
//        }
//    }
//
//    private boolean check(){
//        if (inputNumber.getText().toString().equals("")) {
//            Toast.makeText(this, getString(R.string.blank_field), Toast.LENGTH_SHORT).show();
//            inputNumber.setText(null);
//            return false;
//        }
//        if(inputNumber.length()<10) {
//            Toast.makeText(this, getString(R.string.incorrect_input), Toast.LENGTH_SHORT).show();
//            inputNumber.setText(null);
//            return false;
//        }
//        else {
//            return true;
//        }
//    }
//
//    class My_Location extends AsyncTask<String, String, String> {
//
//        int success = 0;
//        @Override
//        protected void onPreExecute() {super.onPreExecute();}
//
//        @Override
//        protected String doInBackground(String... args) {
//
//            Tracker();
//            String number = phone_number;
//            String latitude = String.format("%1$.6f", lat);
//            String longitude = String.format("%1$.6f", lng);
//
//            List<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("number", number));
//            params.add(new BasicNameValuePair("latitude", latitude));
//            params.add(new BasicNameValuePair("longitude", longitude));
//            Log.d("MY_LOG", params.toString());
//
//            String url_send_location = server + "store.php";
//            JSONObject json = jsonParser.makeHttpRequest(url_send_location, "POST", params);
//
//            Log.d(LOG_TAG, "jsonParser: " + jsonParser.toString());
//            Log.d(LOG_TAG, "JSON: " + json);
//
//            // check logcat for response
//            if(json != null){
//                try {
//                    success = json.getInt(TAG_SUCCESS);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        protected void onPostExecute(String file_url) {
//            if (success != 1) {Log.d(LOG_TAG, "Failed to send data...");}
//            else Log.d(LOG_TAG, "MyLocation is OK");
//        }
//    }
//
//    class GetData extends AsyncTask<String, String, String> {
//
//        String number, data_time;
//        double nlat, nlng;
//        int success;
//
//        /*Before starting background thread Show Progress Dialog */
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage(getString(R.string.request));
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }
//
//        /* Getting product details in background thread */
//        protected String doInBackground(String... args) {
//            // Building Parameters
//            request_field = inputNumber.getText().toString();
//            List<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("requested", request_field));
//            Log.d(LOG_TAG, params.toString());
//
//            String url_details = "http://lab524.pe.hu/get_details.php";
//            JSONObject json = jsonParser.makeHttpRequest(url_details, "POST", params);
//
//            // check your log for json response
//            Log.d("Single Request Details", json.toString());
//
//            try {
//                success = json.getInt(TAG_SUCCESS);
//                if (success == 1) {
//                    // successfully received product details
//                    JSONArray requestObj = json.getJSONArray("request"); // JSON Array
//                    // get first data from JSON Array
//                    JSONObject data = requestObj.getJSONObject(0);
//
//                    number = data.getString("phone_number");
//                    nlat = Double.parseDouble(data.getString("latitude"));
//                    nlng = Double.parseDouble(data.getString("longitude"));
//                    data_time = data.getString("date_time");
//                    Log.d(LOG_TAG, data.toString());
//
//                } else {Log.d("MY_LOG", "No data found");}
//            } catch (JSONException e) {e.printStackTrace();}
//            return null;
//        }
//
//        /* After completing background task Dismiss the progress dialog */
//        protected void onPostExecute(String file_url) {
//            // dismiss the dialog once got all details
//            pDialog.dismiss();
//            inputNumber.setText(null);
//            if (success == 1) {
//                if (marker != null) { marker.remove(); }
//                if (nlat == 0 && nlng == 0){
//                    Toast.makeText(MainActivity.this, getString(R.string.not_enable), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                marker = map.addMarker(new MarkerOptions().
//                        position(new LatLng(nlat, nlng)).
//                        icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).
//                        snippet(getString(R.string.date) + data_time).title(getString(R.string.number) + ": " + number));
//                CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(nlat, nlng));
//                map.animateCamera(update);
//            }
//            else {Toast.makeText(MainActivity.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();}
//        }
//    }
//
//    void authorization(){
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        alert.setTitle("Log in");
//
//        final EditText inputName = new EditText(this);
//        inputName.setHint("Name");
//        layout.addView(inputName);
//
//        final EditText inputPass = new EditText(this);
//        inputPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        inputPass.setHint("Password");
//        layout.addView(inputPass);
//
//        alert.setView(layout);
//        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {}
//        });
//        alert.setNegativeButton("Log in", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {}
//        });
//
//        final AlertDialog alertDialog = alert.create();
//        alertDialog.show();
//        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!(inputName.getText().toString().equals(nickname))) {inputName.setError(getString(R.string.name_error));}
//                else if (!(md5(inputPass.getText().toString()).equals(pass))) {inputPass.setError(getString(R.string.pass_error));}
//                else {
////                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//                    alertDialog.cancel();
//                    settings();
//                }
//            }
//        });
//    }
//
//    void settings(){
//        clicked1 = false;
//        clicked2 = false;
//        int input_pass_width = 250;
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle(getString(R.string.action_settings));
//
//        final LinearLayout layout = new LinearLayout(this);
//        layout.setOrientation(LinearLayout.VERTICAL);
//        final LinearLayout layoutForName = new LinearLayout(this);
//        layoutForName.setOrientation(LinearLayout.VERTICAL);
//        final LinearLayout layoutForPass = new LinearLayout(this);
//        layoutForPass.setOrientation(LinearLayout.VERTICAL);
//
//        final LinearLayout hlayout1  = new LinearLayout(this);
//        hlayout1.setOrientation(LinearLayout.HORIZONTAL);
//        final LinearLayout hlayout2  = new LinearLayout(this);
//        hlayout2.setOrientation(LinearLayout.HORIZONTAL);
//        final LinearLayout hlayout3  = new LinearLayout(this);
//        hlayout3.setOrientation(LinearLayout.HORIZONTAL);
//        final LinearLayout hlayout4  = new LinearLayout(this);
//        hlayout4.setOrientation(LinearLayout.HORIZONTAL);
//        hlayout4.setFocusable(true);
//        final LinearLayout hlayout5  = new LinearLayout(this);
//        hlayout5.setOrientation(LinearLayout.HORIZONTAL);
//        final LinearLayout hlayout6  = new LinearLayout(this);
//        hlayout6.setOrientation(LinearLayout.HORIZONTAL);
//
//        final TextView NumField = new TextView(this);
//        NumField.setText("\t" + getString(R.string.number) + ": ");
//        NumField.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        final EditText inputNum = new EditText(this);
//        inputNum.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
//        inputNum.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        InputFilter[] fArray = new InputFilter[1];
//        fArray[0] = new InputFilter.LengthFilter(10);
//        inputNum.setFilters(fArray);
//        inputNum.setText(phone_number);
//
//        final TextView NameField = new TextView(this);
//        NameField.setText("\t" + getString(R.string.nickname) + ": ");
//        NameField.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        final EditText inputName = new EditText(this);
//        inputName.setMinWidth(250);
//        inputName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        InputFilter[] array = new InputFilter[1];
//        array[0] = new InputFilter.LengthFilter(20);
//        inputName.setFilters(array);
//        inputName.setText(nickname);
//
//        final TextView SpinnerField = new TextView(this);
//        SpinnerField.setText("\t" + getString(R.string.delay_txt) + " ");
//        SpinnerField.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        final Spinner spinner = new Spinner(this);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.delay, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(adapter.getPosition(String.valueOf(delay)));
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Object item = parent.getItemAtPosition(position);
//                delay = Integer.parseInt(item.toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//
//        final TextView opass = new TextView(MainActivity.this);
//        opass.setText("\t" + getString(R.string.password) + ": ");
//        opass.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        final EditText o_pass = new EditText(MainActivity.this);
//        o_pass.setMinWidth(input_pass_width);
//        o_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        o_pass.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        final TextView npass = new TextView(MainActivity.this);
//        npass.setText("\t" + getString(R.string.new_pass));
//        npass.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        final EditText n_pass = new EditText(MainActivity.this);
//        n_pass.setMinWidth(input_pass_width);
//        n_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        n_pass.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        final TextView cpass = new TextView(MainActivity.this);
//        cpass.setText("\t" + getString(R.string.c_password) + ": ");
//        cpass.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        final EditText c_pass = new EditText(MainActivity.this);
//        c_pass.setMinWidth(input_pass_width);
//        c_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        c_pass.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        final TextView NameParams = new TextView(this);
//        NameParams.setText(" " + getString(R.string.login_data));
//        NameParams.setTextSize(16);
//        NameParams.setFocusable(true);
//        NameParams.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NameParams.setSelected(true);
//                hlayout4.addView(NumField);
//                hlayout4.addView(inputNum);
//                layoutForName.addView(hlayout4);
//
//                hlayout5.addView(NameField);
//                hlayout5.addView(inputName);
//                layoutForName.addView(hlayout5);
//
//                hlayout6.addView(SpinnerField);
//                hlayout6.addView(spinner);
//                layoutForName.addView(hlayout6);
//
//                layoutForName.removeView(NameParams);
//                clicked1 = true;
//            }
//        });
//        layoutForName.addView(NameParams);
//        layout.addView(layoutForName);
//
//        EditText ed = new EditText(this);
//        ed.setVisibility(View.INVISIBLE);
//        layout.addView(ed);
//
//        final TextView PassParams = new TextView(this);
//        PassParams.setText(" " + getString(R.string.change_pass));
//        PassParams.setTextSize(16);
//        PassParams.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (!clicked2) {
//                    hlayout1.addView(opass);
//                    hlayout1.addView(o_pass);
//                    layoutForPass.addView(hlayout1);
//
//                    hlayout2.addView(npass);
//                    hlayout2.addView(n_pass);
//                    layoutForPass.addView(hlayout2);
//
//                    hlayout3.addView(cpass);
//                    hlayout3.addView(c_pass);
//                    layoutForPass.addView(hlayout3);
//                    clicked2 = true;
//                } else {
//                    layoutForPass.removeAllViews();
//                    layoutForPass.addView(PassParams);
//                    clicked2 = false;
//                }
//            }
//        });
//        layoutForPass.addView(PassParams);
//        layout.addView(layoutForPass);
//
//        EditText e = new EditText(this);
//        e.setVisibility(View.INVISIBLE);
//        layout.addView(e);
//
//        alert.setView(layout);
//        alert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//            }
//        });
//        alert.setNeutralButton(getString(R.string.loc_settings), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            }
//        });
//        alert.setNegativeButton(getString(R.string.save), new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {}
//        });
//
//        final AlertDialog dialog = alert.create();
//        dialog.show();
//        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dbHelper = new DBHelper(MainActivity.this);
//                        SQLiteDatabase db = dbHelper.getWritableDatabase();
//                        ContentValues cv = new ContentValues();
//                        if (clicked2 | clicked1) {
//                            if (clicked1) {
//                                String number = inputNum.getText().toString();
//                                String name = inputName.getText().toString();
//
//                                if (number.equals("")) {inputNum.setError(getString(R.string.blank_field)); return;}
//                                if (number.length() != 10) {inputNum.setError(getString(R.string.incorrect_input)); return;}
//                                if (name.equals("")) {inputName.setError(getString(R.string.blank_field)); return;}
//
//                                cv.put("my_number", number);
//                                cv.put("nickname", name);
//                                cv.put("delay", delay);
//                                db.update("user", cv, "id = ?", new String[]{String.valueOf(id)});
//                                dialog.dismiss();
//                                T.cancel();
//                                init();
//                                start_timer();
//                            }
//                            else if (clicked2) {
//                                String OldPassword = md5(o_pass.getText().toString());
//                                String NewPassword = n_pass.getText().toString();
//                                String ConfirmPassword = c_pass.getText().toString();
//
//                                if (!(OldPassword.equals(pass))) {o_pass.setError(getString(R.string.pass_error)); return;}
//                                if (!(NewPassword.matches(ConfirmPassword))) {n_pass.setError(getString(R.string.confirm_pass)); return;}
//
//                                String password = n_pass.getText().toString();
//                                cv.put("password", md5(password));
//                                db.update("user", cv, "id = ?", new String[]{String.valueOf(id)});
//                                dialog.dismiss();
//                                init();
//                            }
//                        } else {
//                            Log.d(LOG_TAG, "Not updated");
//                            dialog.dismiss();
//                        }
//                        dbHelper.close();
//                    }
//                });
//    }
//
//    void init(){
//        btnGetData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (check()) {new GetData().execute();}
//            }
//        });
//        dbHelper = new DBHelper(this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        Cursor c = db.query("user", null, null, null, null, null, null);
//        c.moveToFirst();
//
//        id = c.getInt(c.getColumnIndex("id"));
//        phone_number = c.getString(c.getColumnIndex("my_number"));
//        nickname = c.getString(c.getColumnIndex("nickname"));
//        pass = md5(c.getString(c.getColumnIndex("password")));
//        delay = c.getInt(c.getColumnIndex("delay"));
//        c.close();
//        dbHelper.close();
//        Log.d(LOG_TAG, phone_number + ", " + nickname + ", " + pass + ", " + delay);
////        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
////        String mPhoneNumber = tMgr.getLine1Number();
////        Log.d(LOG_TAG, mPhoneNumber);
//    }
//
//    private void start_timer(){
//        T = new Timer();
//        T.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        new My_Location().execute();
//                    }
//                });
//            }
//        }, 1000, delay * 60 * 1000);
//    }
//
//    String md5(String s) {
//        try {
//            // Create MD5 Hash
//            MessageDigest digest = MessageDigest.getInstance("MD5");
//            digest.update(s.getBytes());
//            byte messageDigest[] = digest.digest();
//
//            // Create Hex String
//            StringBuffer hexString = new StringBuffer();
//            for (int i=0; i<messageDigest.length; i++)
//                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//}