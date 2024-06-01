package fr.umontpellier.etu.inteco.Authentication.Seeker;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.umontpellier.etu.inteco.Authentication.LoginFirstActivity;
import fr.umontpellier.etu.inteco.Seeker.HomePageSeeker;
import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker3 extends AppCompatActivity {
    private static final String TAG = "debug signUp";
    private String email;
    private String password;
    private String firstName ;
    private String lastName ;
    private String birthday ;
    private String nationality;
    private String phoneNumber;
    private String city;
    private String address;
    private String sex;
    private Button btnUploadCV ;
    private Button btnNext;
    private FirebaseAuth mAuth;

    private static final int PICK_FILE_REQUEST = 1;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    // Create a storage reference from our app
    private StorageReference storageRef = storage.getReference();

    private UploadTask uploadTask;
    private String nameOfFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seeker3);

        // Retrieve data from the intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        birthday = intent.getStringExtra("birthday");
        nationality= intent.getStringExtra("nationality");
        phoneNumber= intent.getStringExtra("phoneNumber");
        city= intent.getStringExtra("city");
        address= intent.getStringExtra("address");
        sex= intent.getStringExtra("sex");

        btnUploadCV = findViewById(R.id.btnCV);
        btnNext = findViewById(R.id.btnNext);

        btnUploadCV.setOnClickListener(v -> {
            this.openFileChooser();
        });

        btnNext.setOnClickListener(v -> {
            Log.i("Test Inputs",email+password+firstName+lastName+birthday+nationality+phoneNumber+city+address+sex);
            //TODO check if inputs are empty or not ???
            createAccount(email,password,firstName,lastName,birthday,nationality,phoneNumber,city,address,sex);
            Intent intent2 = new Intent(SignUpSeeker3.this, HomePageSeeker.class);
            intent2.putExtra("email", email);
            intent2.putExtra("firstname", firstName);
            intent2.putExtra("lastname", lastName);
            startActivity(intent2);
        });


        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password, String firstName, String lastName,
                               String birthday, String nationality, String phoneNumber,
                               String city, String address, String sex){
        //TODO add CV to the parameters
        //TODO create the account for the user
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("address",address);
        user.put("apply", new ArrayList<>());
        user.put("birthday",birthday);
        user.put("city",city);
        user.put("email", email);
        user.put("firstname", firstName);
        user.put("gender",sex);
        user.put("lastname", lastName);
        user.put("nationality",nationality);
        user.put("phoneNumber",phoneNumber);
        user.put("saved",new ArrayList<>());

        if (this.uploadTask != null) {
            uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d(TAG, "onFailure: fail");
                        user.put("cv",null);
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                        Log.d(TAG, "onSuccess: upload");
                        Toast.makeText(SignUpSeeker3.this, "Upload of "+nameOfFile+" successful", Toast.LENGTH_SHORT).show();
                        user.put("cv",nameOfFile);

                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                        Map<String, Object> oof = new HashMap<>();
                                        oof.put("collection", "users");
                                        oof.put("email", email);
                                        oof.put("ref", documentReference);
                                        db.collection("allUsers").add(oof).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d(TAG, "onSuccess: done");
                                            }
                                        });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });
                    }
                });

        } else {
            user.put("cv",null);
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            Log.d(TAG, "onComplete: "+user.toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpSeeker3.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }
    private void performFileSearch() {
        //TODO implement filesearch + permissions
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*"); // Pour permettre la sélection de tous types de fichiers
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File"), PICK_FILE_REQUEST);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedFileUri = data.getData();
                this.nameOfFile = this.getFileName(selectedFileUri);
                Log.d(TAG, "onActivityResult: name="+this.getFileName(selectedFileUri));


                StorageReference riversRef = storageRef.child("images/"+nameOfFile);
                this.uploadTask = riversRef.putFile(selectedFileUri);

//                uploadTask.addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Handle unsuccessful uploads
//                        Log.d(TAG, "onFailure: fail");
//                    }
//                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
//                        // ...
//                        Log.d(TAG, "onSuccess: upload");
//                        Toast.makeText(SignUpSeeker3.this, "Upload of "+nameOfFile+" successful", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }
    }
    @SuppressLint("Range")
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}