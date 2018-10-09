package com.example.john.parkingcontrol.Activity.TIcketIssue.Photo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddressByGPS.AddressRequest;
import com.example.john.parkingcontrol.API.models.AddCarInc.AddressByGPS.AddressResponse;
import com.example.john.parkingcontrol.API.models.AddCarInc.UploadResponse;
import com.example.john.parkingcontrol.Activity.LoginActivity;
import com.example.john.parkingcontrol.Activity.MainActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.FillTicketActivity;
import com.example.john.parkingcontrol.Activity.TIcketIssue.ProtokolActivity;
import com.example.john.parkingcontrol.BuildConfig;
import com.example.john.parkingcontrol.DifferentHelpers.PrDialog;
import com.example.john.parkingcontrol.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_EDGE;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GPRS;
import static android.telephony.TelephonyManager.NETWORK_TYPE_GSM;
import static android.telephony.TelephonyManager.NETWORK_TYPE_UNKNOWN;

public class PhotoActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4;
    private ImageView preView;
    private String myToken, myGuid, postPath, file, encodedFile;
    private Double gpsLat, gpsLon;

    private String responseCarNumber;
    private String mImageFileLocation = "";
    private Boolean isEmptyNumber;
    private Retrofit retrofit;
    private Bitmap bitmap;
    private GetTokenApi service, service2;
    private SharedPreferences sPref;
    private Uri fileUri;
    private static final String TAG = PhotoActivity.class.getSimpleName();
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public static final int IMG_REQUEST = 777;
    private static final int CAMERA_PIC_REQUEST = 1111;
    private PrDialog prDialog = new PrDialog();
    int i = 0;
    private int perspectiveCode = 0;
    private int clickedButton;
    private LocationManager locationManager;
    private StringBuilder sbGPS;
    private StringBuilder sbNet;
    private int g = 0;
    private int gpsStatus=111;
    private int buttonStatus=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        prDialog.initDialog(getString(R.string.app_text_loading), this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        responseCarNumber = getIntent().getExtras().getString("responseCarNumber");
        isEmptyNumber = getIntent().getExtras().getBoolean("isEmptyNumber");
        if (!isEmptyNumber) {
            findViewById(R.id.buttonGetBack).setEnabled(false);
        }

        myGuid = getIntent().getExtras().getString("guid");
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button3);
        button3 = findViewById(R.id.button4);
        button4 = findViewById(R.id.button2);

        if (buttonStatus==0)
        {if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            buttonStatus=1;
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
        }
    }


        String url = getString(R.string.app_main_url);
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                captureImage();
                perspectiveCode = 1;
                clickedButton = button1.getId();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                captureImage();
                perspectiveCode = 2;
                clickedButton = button2.getId();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                captureImage();
                perspectiveCode = 3;
                clickedButton = button3.getId();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                captureImage();
                perspectiveCode = 4;
                clickedButton = button4.getId();
            }
        });
        findViewById(R.id.buttonGetBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (g==0||gpsStatus==111){
            prDialog.showDialog();
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);
        }

        startLocation();
    }
    private void startLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 5, 10, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 5, 10, locationListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (location==null||gpsLat==null||gpsLat==0||gpsLat==0.0||gpsLon==null||gpsLon==0||gpsLon==0.0){
                g=0;
                return;
            }
            else if (location!=null&&gpsLat!=null&&gpsLat!=0&&gpsLat!=0.0&&gpsLon!=null&&gpsLon!=0&&gpsLon!=0.0&&g==0){
                prDialog.hideDialog();
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
            }
            gpsLat = location.getLatitude();
            gpsLon = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            if ((provider.equals(LocationManager.GPS_PROVIDER)&&status==2)||(provider.equals(LocationManager.NETWORK_PROVIDER)&&status==2&&g==0)){
                prDialog.hideDialog();
                g=1;
                gpsStatus=status;
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
            }

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void setLocation(Location location){
        if (location==null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)){

            //TODO
        }
        else if (location.getProvider().equals(LocationManager.NETWORK_PROVIDER)){
            //TODO
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        prDialog.showDialog();
        if(resultCode==RESULT_OK){
            findViewById(R.id.buttonGetBack).setEnabled(false);

            if(requestCode==IMG_REQUEST && data!=null){

                Uri path = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                    Matrix matrix = new Matrix();
                    matrix.postRotate(90);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 900, 1200, false);
                    preView.setImageBitmap(bitmap);
                    preView.setVisibility(View.VISIBLE);
//                    buttonChoose.setEnabled(false);
//                    buttonTakePhoto.setEnabled(false);
//                    buttonUpload.setEnabled(true);

                    //file = imageToString();
                    Button myButton = findViewById(clickedButton);
                    uploadImage(perspectiveCode, myButton);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (requestCode == CAMERA_PIC_REQUEST){
                if (Build.VERSION.SDK_INT > 21) {

                    if (android.os.Build.VERSION.SDK_INT >= 26){

                        //Glide.with(this).load(mImageFileLocation).into(preView);
                        postPath = mImageFileLocation;

                        try {
                            byte[] bytes = Files.readAllBytes(new File(postPath).toPath());
                            encodedFile = Base64.encodeToString(bytes, Base64.DEFAULT);
                            Button myButton = findViewById(clickedButton);
                            uploadImage(perspectiveCode, myButton);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    else {
                        FileInputStream fS = null;
                        byte[] bytes;
                        File f;

                        try {
                            postPath = mImageFileLocation;
                            f = new File(postPath);
                            fS = new FileInputStream(f);
                            int fileSize = (int) f.length();
                            if (fileSize>0){
                                bytes = new byte[fileSize];
                                int read = fS.read(bytes,0, fileSize);
                                if (read>0){

                                    encodedFile = Base64.encodeToString(bytes, Base64.DEFAULT);

                                    Button myButton = findViewById(clickedButton);
                                    uploadImage(perspectiveCode, myButton);
                                }
                                else{
                                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(this, "File is empty", Toast.LENGTH_SHORT).show();
                            }

                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if (fS!=null){
                                try {
                                    fS.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
/*
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PhotoActivity.this);
                        builder.setTitle("Помилка");
                        builder.setMessage("Підтримується версія Android від 8.0" +
                                "Вас буде перенаправлено на сторінку авторизації");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(PhotoActivity.this, LoginActivity.class);
                                startActivity(intent);
                                PhotoActivity.this.finish();
                                dialog.dismiss();
                            }
                        });
                        builder.setCancelable(false);
                        android.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        */
                    }
                }else{
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PhotoActivity.this);
                    builder.setTitle("Помилка");
                    builder.setMessage("Підтримується версія Android від 8.0" +
                            "Вас буде перенаправлено на сторінку авторизації");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(PhotoActivity.this, LoginActivity.class);
                            startActivity(intent);
                            PhotoActivity.this.finish();
                            dialog.dismiss();
                        }
                    });
                    builder.setCancelable(false);
                    android.app.AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }
        }
        else if (resultCode == RESULT_CANCELED) {
            Button myButton = findViewById(clickedButton);
            myButton.setEnabled(true);

        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Невиправна помилка", Toast.LENGTH_LONG).show();

        }

    }
    private String imageToString(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }


    private void captureImage() {
        int resulution = 300;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nf = cm.getActiveNetworkInfo();

        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        int nt = telephonyManager.getNetworkType();

        if (nf.getType()==ConnectivityManager.TYPE_WIFI){
            resulution = 1024;
        }
        else if( nt == NETWORK_TYPE_UNKNOWN | nt == NETWORK_TYPE_GPRS | nt == NETWORK_TYPE_EDGE | nt == NETWORK_TYPE_GSM ){
            resulution = 300;
        }
        else {
            resulution = 1024;
        }

        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, resulution);
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            //prDialog.showDialog();
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            //prDialog.showDialog();
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


    }
    private void uploadImage(int photoPerspectiveCode, final Button currentButton){
                sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
                myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

                service = retrofit.create(GetTokenApi.class);

                Call<UploadResponse> uploadResponseCall = service.uploadPhoto("Bearer "+myToken, myGuid, encodedFile, photoPerspectiveCode);
                uploadResponseCall.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        prDialog.hideDialog();
                        if (response.code()==200) {
                            if (response.body().getIsSuccess()) {
                                currentButton.setBackgroundColor(Color.GREEN);
                                currentButton.setEnabled(false);
                                i++;
//                                TextView textView = findViewById(R.id.textView7);
//                                textView.setText(response.toString());

                                    if (i==4) {
                                        prDialog.showDialog();
                                        getAdrGPS();
                                    }
                                    return;
                            }
                            else{
                                currentButton.setEnabled(true);
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PhotoActivity.this);
                                builder.setTitle("Помилка");
                                builder.setMessage("Помилка, зверніться до адміністратора."+" "+response.code()+" "+response.body().getErrorMsg());
                                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        locationManager.removeUpdates(locationListener);
                                        Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        PhotoActivity.this.finish();
                                        dialog.dismiss();
                                    }
                                });
                                builder.setCancelable(false);
                                android.app.AlertDialog alertDialog = builder.create();
                                alertDialog.show();

                            }
                        }else if (response.code()==401){
                            currentButton.setEnabled(true);
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(PhotoActivity.this);
                            builder.setTitle("Помилка");
                            builder.setMessage("Помилка авторизації, бездіяльність більше 20 хв." +
                                    "Вас буде перенаправлено на сторінку авторизації");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    locationManager.removeUpdates(locationListener);
                                    Intent intent = new Intent(PhotoActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    PhotoActivity.this.finish();
                                    dialog.dismiss();
                                }
                            });
                            builder.setCancelable(false);
                            android.app.AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                        }else {
                            currentButton.setEnabled(true);
                            Toast.makeText(PhotoActivity.this, "Помилка. Зменьшіть якість фото " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable t) {
                        prDialog.hideDialog();
                        Toast.makeText(PhotoActivity.this, "Помилка зв'язку, перевірте інтернет з'єднання", Toast.LENGTH_SHORT).show();
                        currentButton.setEnabled(true);
                    }
                });
    }

    private void getAdrGPS(){
        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

        String url = getString(R.string.app_main_url);
        final Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service2 = retrofit2.create(GetTokenApi.class);
        AddressRequest addressRequest = new AddressRequest();

        addressRequest.setLatitude(gpsLat);
        addressRequest.setLongitude(gpsLon);

        sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
        myToken = "Bearer "+sPref.getString(getResources().getString(R.string.sp_field_token), "");

        final Call<AddressResponse> addressResponseCall = service2.getAddress(myToken, addressRequest);
        addressResponseCall.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                prDialog.hideDialog();
                if (response.code()==200){
                    try{

                        locationManager.removeUpdates(locationListener);
                        Intent intent = new Intent(PhotoActivity.this, ProtokolActivity.class);
                        intent.putExtra("guid", myGuid);
                        intent.putExtra("isEmptyNumber", isEmptyNumber);
                        intent.putExtra("responseCarNumber", responseCarNumber);
                        intent.putExtra("gpsLat", gpsLat);
                        intent.putExtra("gpsLon", gpsLon);
                        intent.putExtra("responseAddress", response.body().getAddress());
                        startActivity(intent);
                        finish();
                    }
                    catch (NullPointerException e){
                        String responseAddress = "";
                    }
                }
                else {
                    String responseAddress = "";
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                prDialog.hideDialog();
                String responseAddress = "";
            }
        });
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }
    File createImageFile() throws IOException {
        Logger.getAnonymousLogger().info("Generating the image - method started");

        // Here we create a "non-collision file name", alternatively said, "an unique filename" using the "timeStamp" functionality
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmSS").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp;
        // Here we specify the environment location and the exact path where we want to save the so-created file
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/photo_saving_app");
        Logger.getAnonymousLogger().info("Storage directory set");

        // Then we create the storage directory if does not exists
        if (!storageDirectory.exists()) storageDirectory.mkdir();

        // Here we create the file using a prefix, a suffix and a directory
        File image = new File(storageDirectory, imageFileName + ".jpg");
        // File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        // Here the location is saved into the string mImageFileLocation
        Logger.getAnonymousLogger().info("File name and path set");

        mImageFileLocation = image.getAbsolutePath();
        // fileUri = Uri.parse(mImageFileLocation);
        // The file is returned to the previous intent across the camera application
        return image;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save file url in bundle as it will be null on screen orientation
        // changes
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                buttonTakePhoto.setEnabled(true);
                button1.setEnabled(true);
                button2.setEnabled(true);
                button3.setEnabled(true);
                button4.setEnabled(true);
            }
        }
    }


    @Override
    public void onBackPressed()
    {
        locationManager.removeUpdates(locationListener);
        finish();
    }
}
