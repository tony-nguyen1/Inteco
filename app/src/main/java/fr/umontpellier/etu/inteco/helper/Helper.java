package fr.umontpellier.etu.inteco.helper;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Helper {
    private static final String TAG = "debug Helper";

    public static enum userType {
        JOB_SEEKER,
        ENTREPRISE
    }

    /***
     * Check if email exist in collection
     *
     * @param email of the user
     * @param collection the firestore collection
     * @param response post the answer when request complete
     */
    public static void verifyEmailExists(String email, String collection, MutableLiveData<Boolean> response){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d(TAG, "verifyEmailExists: "+email);
        //response.setValue(Boolean.FALSE); //Initilize with a value


        // read
        Task<QuerySnapshot> q = db.
                collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean foundEmail = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "onComplete: "+document.getId()+"=>"+document.getData());
                                String s = document.get("email", String.class);

                                assert s != null;
                                if (email.equals(s)){
                                    response.postValue(Boolean.TRUE); // post a value
                                    foundEmail = true;
                                    break;
                                }
                            }
                            if (!foundEmail) {
                                response.postValue(Boolean.FALSE); }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static void resetPassword(String email, OnCompleteListener onCompleteListener) {
        Log.d(TAG, "resetPassword: "+email);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(onCompleteListener);
    }

    /**
     * Check if email belongs to a job seeker or a enterprise
     */
    public static void checkTypeUser(String email, MutableLiveData<Helper.userType> response) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.
                collection("allUsers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean foundEmail = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, "onComplete: "+document.getId()+"=>"+document.getData());
                                String s = document.get("email", String.class);

                                assert s != null;
                                if (email.equals(s)){
                                    foundEmail=true;
                                    String sUserType = document.getString("collection");
                                    if ("company".equals(sUserType)) {
                                        response.postValue(userType.ENTREPRISE); // post a value
                                    } else if ("users".equals(sUserType)) {
                                        response.postValue(userType.JOB_SEEKER);
                                    } else {
                                        throw new RuntimeException("collection field of allUsers not recognized");
                                    }
                                    break;
                                }
                            }
                            if (!foundEmail) {
                                throw new RuntimeException("user not found");
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static void getUser(FirebaseUser firebaseUser, String collection, MutableLiveData<Map<String,Object>> answer) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(collection)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean found = false;
                            QueryDocumentSnapshot infoUser = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String s = document.get("email",String.class);
                                if (firebaseUser.getEmail().equals(s)) {
                                    found = true;
                                    infoUser = document;
                                }
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            answer.postValue(infoUser.getData());
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    /***
     * Add aCompany to company & allUsers collections
     *
     * @param aCompany
     */
    public static void addACompanyToCollections(Map<String, Object> aCompany, MutableLiveData<Boolean> response) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        // adding to first collection
        db.collection("company")
                .add(aCompany)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: added to company collection");
                        Log.d(TAG, "onSuccess: DocumentSnapshot added with ID: " + documentReference.getId());


                        Map<String, Object> aCompanyLight = new HashMap<>();
                        aCompanyLight.put("email", aCompany.get("email"));
                        aCompanyLight.put("collection","company");
                        aCompanyLight.put("ref",documentReference);


                        // adding to snd collection
                        db.collection("allUsers")
                                .add(aCompanyLight)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        Log.d(TAG, "onSuccess: added to allUsers collection");
                                        Log.d(TAG, "onSuccess: DocumentSnapshot added with ID: " + documentReference.getId());

                                        response.postValue(Boolean.TRUE);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
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
}
