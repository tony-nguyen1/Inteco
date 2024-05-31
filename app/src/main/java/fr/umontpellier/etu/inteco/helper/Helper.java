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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;

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

    public interface after {
        abstract void afterGetUser(Helper.userType type);
    }

    public static void getUser(FirebaseUser firebaseUser, String collection, MutableLiveData<Map<String,Object>> answerJobTaker, MutableLiveData<Map<String,Object>> answerJobGiver) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final MutableLiveData<Map<String,Object>> answer;
        if (collection.equals("company")) {
            answer = answerJobGiver;
        } else if (collection.equals("users")) {
            answer = answerJobTaker;
        } else { answer = null; }

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

    public static void getCompanyDocumentReference(FirebaseUser firebaseUser, MutableLiveData<QueryDocumentSnapshot> answerJobGiver) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final MutableLiveData<Map<String,Object>> answer;
        db.collection("company")
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
                                    break;
                                }
                            }
                            answerJobGiver.postValue(infoUser);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public static void getJobSeekerDocumentReference(FirebaseUser firebaseUser, MutableLiveData<QueryDocumentSnapshot> answerJobSeeker) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        final MutableLiveData<Map<String,Object>> answer;
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QueryDocumentSnapshot infoUser = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String s = document.get("email",String.class);
                                if (firebaseUser.getEmail().equals(s)) {
                                    infoUser = document;
                                    break;
                                }
                            }
                            answerJobSeeker.postValue(infoUser);
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

    /***
     *  Save to uneOffre to Firebase. First, add uneOffre to offers collection then to company collection. Finally "wake up" caller through answer.
     * @param firebaseUser
     * @param uneOffre
     * @param answer
     */
    public static void addPost(FirebaseUser firebaseUser, Offer uneOffre, MutableLiveData<Boolean> answer)  {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        String email = firebaseUser.getEmail();
        Log.d(TAG, "addPost: email="+email+" uneOffre="+uneOffre);


        MutableLiveData<Map<String,Object>> listen = new MutableLiveData<>();

    }

    public static void addPostToOffers(Offer uneOffre, DocumentReference documentReferenceOfCompany, MutableLiveData<ArrayList<DocumentReference>> answer) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

//        Helper.getUser(firebaseUser, "company", null, listen);

        Map<String, Object> anOffer = new HashMap<>();
        anOffer.put("post_title", "titre du post");
        anOffer.put("date", "la date");
        anOffer.put("job_type", "type");
        anOffer.put("description", "");
        anOffer.put("job_title", "");
        anOffer.put("qualification_wanted", "");
        anOffer.put("experience_wanted", "");
        anOffer.put("contract_type", "");
        anOffer.put("start_time", "");
        anOffer.put("duration", "6 mois");
        anOffer.put("state", "open");
        anOffer.put("country", "");
        anOffer.put("city", "");
        anOffer.put("adress", "");
        anOffer.put("refCompany", documentReferenceOfCompany);

        db.collection("offers")
                .add(anOffer)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Log.d(TAG, "onComplete: added post to offers");
                        answer.postValue(new ArrayList<>(Arrays.asList(documentReferenceOfCompany, task.getResult())));
                    }
                });
    }

    public static void addPostToCompany(DocumentReference documentReferenceOfCompany, DocumentReference documentReferenceOffer) {
        documentReferenceOfCompany.update("posted", FieldValue.arrayUnion(documentReferenceOffer))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: field posted of company updated");
                    }
                });
    }



    public static void getCompanyPosted(DocumentReference aCompany, MutableLiveData<ArrayList<DocumentReference>> answer) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<DocumentReference> res = new ArrayList<>();

        db.collection("company").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (aCompany.equals(document.getReference())) {

//                            answer.postValue(aCompany.get().get);
                            break;
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
    public static void  getOffersOfCompany(ArrayList<DocumentReference> references, MutableLiveData<ArrayList<Map<String,Object>>> answer) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        final ArrayList<Map<String,Object>> res = new ArrayList<>();

        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (references.contains(document.getReference())) {
                            Map<String,Object> oof = document.getData();

                            long l = ((ArrayList<DocumentReference>) document.get("apply")).size();
                            Log.d(TAG, "onComplete: nbAppli="+l);
                            oof.put("nbAppli",l);

                            res.add(oof);
                        }
                    }
                    answer.postValue(res);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    public static void addOfferToUserApply(DocumentReference anOffer, DocumentReference aJobSeeker, MutableLiveData<DocumentReference> answer){
        anOffer.update("apply", FieldValue.arrayUnion(aJobSeeker)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "onComplete: user registered in the offer");
                        answer.postValue(aJobSeeker);
                    }
                });
    }
    public static void addUserToOfferApply(DocumentReference anOffer, DocumentReference aJobSeeker, MutableLiveData<Boolean> answer){
        aJobSeeker.update("apply", FieldValue.arrayUnion(anOffer)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG, "onComplete: offer registered in the user");
                answer.postValue(Boolean.TRUE);
            }
        });
    }
}
