package com.example.john.parkingcontrol.Activity.TIcketIssue.Photo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.john.parkingcontrol.API.interfaces.GetTokenApi;
import com.example.john.parkingcontrol.API.models.AddCarInc.UploadResponse;
import com.example.john.parkingcontrol.Activity.LoginActivity;
import com.example.john.parkingcontrol.Activity.MainActivity;
import com.example.john.parkingcontrol.BuildConfig;
import com.example.john.parkingcontrol.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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

public class PhotoActivity extends AppCompatActivity {

    private Button buttonChoose, buttonUpload, buttonTakePhoto;
    private ImageView preView;
    private String myToken, myGuid, postPath, file;
    private String mImageFileLocation = "";
    private Retrofit retrofit;
    private Bitmap bitmap;
    private GetTokenApi service;
    private SharedPreferences sPref;
    private Uri fileUri;
    private static final String TAG = PhotoActivity.class.getSimpleName();
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    public static final int IMG_REQUEST = 777;
    private static final int CAMERA_PIC_REQUEST = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        myGuid = getIntent().getExtras().getString("guid");

        Toast.makeText(this, "guid "+myGuid, Toast.LENGTH_SHORT).show();

        buttonChoose = findViewById(R.id.buttonChoose);
        buttonUpload = findViewById(R.id.buttonUpload);
        buttonTakePhoto = findViewById(R.id.buttonTakePhoto);
        preView = findViewById(R.id.imagePreview);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            buttonTakePhoto.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            buttonTakePhoto.setEnabled(true);
        }


        String url = getString(R.string.app_main_url);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMG_REQUEST);

            }
        });


        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                //final String file = imageToString();
                sPref = getSharedPreferences(getResources().getString(R.string.sp_folder_name), MODE_PRIVATE);
                myToken = sPref.getString(getResources().getString(R.string.sp_field_token), "");

                service = retrofit.create(GetTokenApi.class);

                Call<UploadResponse> uploadResponseCall = service.uploadPhoto(myGuid, file);
                uploadResponseCall.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        if (response.code()==200) {
                            if (response.body().getIsSuccess()) {
                                TextView textView = findViewById(R.id.textView7);
                                textView.setText(response.toString());
                                v.setEnabled(true);
                            }else if (response.code()==401){
                                new AlertDialog.Builder(PhotoActivity.this)
                                        .setTitle("Помилка")
                                        .setMessage("Помилка авторизації, бездіяльність більше 20 хв." +
                                                "Вас буде перенаправлено на сторінку авторизації")
                                        .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(PhotoActivity.this, LoginActivity.class);
                                                PhotoActivity.this.finish();
                                                dialog.dismiss();
                                            }
                                        })
                                        .create()
                                        .show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadResponse> call, Throwable t) {
                        Toast.makeText(PhotoActivity.this, "Помилка зв'язку, перевірте інтернет з'єднання", Toast.LENGTH_SHORT).show();

                        v.setEnabled(true);

                    }
                });
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            if(requestCode==IMG_REQUEST && data!=null){

                Uri path = data.getData();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                    preView.setImageBitmap(bitmap);
                    preView.setVisibility(View.VISIBLE);
                    buttonChoose.setEnabled(false);
                    buttonTakePhoto.setEnabled(false);
                    buttonUpload.setEnabled(true);

                    file = imageToString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            else if (requestCode == CAMERA_PIC_REQUEST){
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(this).load(mImageFileLocation).into(preView);
                    //postPath = mImageFileLocation;
                    bitmap = BitmapFactory.decodeFile(mImageFileLocation);
                    buttonUpload.setEnabled(true);
                    buttonTakePhoto.setEnabled(false);

                    file = imageToString();

                }else{
                    Glide.with(this).load(fileUri).into(preView);
                    postPath = fileUri.getPath();
                    bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                    buttonUpload.setEnabled(true);
                    buttonTakePhoto.setEnabled(false);

                    file = imageToString();

                }

            }

        }
        else if (resultCode != RESULT_CANCELED) {
            Toast.makeText(this, "Sorry, there was an error!", Toast.LENGTH_LONG).show();
        }

    }
    private String imageToString(){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }


    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
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

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


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
                buttonTakePhoto.setEnabled(true);
            }
        }
    }
}
