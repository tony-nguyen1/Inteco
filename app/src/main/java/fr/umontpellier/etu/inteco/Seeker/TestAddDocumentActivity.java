package fr.umontpellier.etu.inteco.Seeker;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.umontpellier.etu.inteco.R;

public class TestAddDocumentActivity extends AppCompatActivity {

    private static final String TAG = "debug fileTransfer";
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_add_document);
        verifyStoragePermissions(this);

        Button myBtn = findViewById(R.id.btnTest);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Create a child reference
                // imagesRef now points to "images"
                StorageReference imagesRef = storageRef.child("images");

                // Child references can also take paths
                // spaceRef now points to "images/space.jpg
                // imagesRef still points to "images"
                StorageReference spaceRef = storageRef.child("images/space.jpg");

                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                Log.d(TAG, "onCreate: "+dir.getAbsolutePath());
                File myFile = new File(dir,"CV_Tony_Nguyen.pdf");
                Log.d(TAG, "onCreate: "+myFile.toString());


                Uri file = Uri.fromFile(myFile);
                StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(file);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d(TAG, "onFailure: upload to cloud storage failed");
                        Log.d(TAG, "onFailure: "+exception.toString());
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Log.d(TAG, "onSuccess: upload to cloud storage complete");
                    }
                });

            }
        });



        Log.d(TAG, "onCreate: attempting to upload");
//        String userDirectory = new File("").getAbsolutePath();
//        Log.d(TAG, "onCreate: "+userDirectory);
       /* for (String name :
                listFilesUsingJavaIO("/")) {
            Log.d(TAG, "onCreate: "+name);
        }*/





//        for (File f:
//                Objects.requireNonNull(dir.listFiles())) {
//            Log.d(TAG, "onCreate: "+f.toString());
//        }
        /*requireContext(this).getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);*/



        // Register observers to listen for when the download is done or if it fails



        Button myBtn2 = findViewById(R.id.btnDownload);
        myBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: downloading");

                FirebaseStorage storage = FirebaseStorage.getInstance();
                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                StorageReference islandRef = storageRef.child("CV_Tony_Nguyen.pdf");
                StorageReference imgRef = storageRef.child("sun.jpg");


                // ImageView in your Activity
                ImageView imageView = findViewById(R.id.img);

                // Download directly from StorageReference using Glide
                // (See MyAppGlideModule for Loader registration)
//                Glide.with(TestAddDocumentActivity.this.getBaseContext())
//                        .load(imgRef)
//                        .into(imageView);
                File downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

                File localFile = null;
                try {
                    localFile = File.createTempFile("doc", ".pdf",downloadsDirectory);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                File finalLocalFile = localFile;
                islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: Local temp file has been created");
                        Log.d(TAG, "onSuccess: "+ finalLocalFile.getAbsolutePath());
                        // Local temp file has been created
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.d(TAG, "onFailure: no download");
                    }
                });
            }
        });

    }
    public Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(new File(dir).listFiles())
//                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}