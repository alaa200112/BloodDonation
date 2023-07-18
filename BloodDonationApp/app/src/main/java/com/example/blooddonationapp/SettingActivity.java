package com.example.blooddonationapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView profileImageView;
    private EditText userPhoneEditText, userNameEditText, userAddressEditText;
    private TextView profileChangeTextBtn, closeTextBtn, saveTextBtn;
    private Spinner genderTxt, bloodTypeTxt;
    private String uploadUri;
    private Uri imageUri;
    private String myUrl = "";
    private StorageReference storageProfilePictureRef;
    private String checker = "",email,pass;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    StorageReference ref;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting2);


        // Reference to an image file in Cloud Storage
        profileImageView = (CircleImageView) findViewById(R.id.profile_image_change);
        userPhoneEditText = (EditText) findViewById(R.id.inputMobile);
        userNameEditText = (EditText) findViewById(R.id.input_fullName);
        userAddressEditText = (EditText) findViewById(R.id.inputAddress);
         /*userEmailEditText = (EditText) findViewById(R.id.input_userEmail);
        userEmailEditText.setText(getIntent().getExtras().getString("userEmail"));
        userPasswordEditText = (EditText) findViewById(R.id.input_password);
        userPasswordEditText.setText(getIntent().getExtras().getString("pass"));*/
        email=getIntent().getExtras().getString("userEmail");
        pass=getIntent().getExtras().getString("pass");
        profileChangeTextBtn = (TextView) findViewById(R.id.profile_image_change_btn);

        saveTextBtn = (TextView) findViewById(R.id.update_account_settings_btn);
        userNameEditText.setText(getIntent().getExtras().getString("name"));
        userPhoneEditText.setText(getIntent().getExtras().getString("phone"));
        userAddressEditText.setText(getIntent().getExtras().getString("address"));
        String uri_Image=getIntent().getExtras().getString("image");
        storageProfilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");
// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        // Glide.with(this /* context */)
        //       .load(uri_Image)
        //     .into(profileImageView);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        userInfoDisplay(profileImageView, userNameEditText, userPhoneEditText, userAddressEditText);//,userEmailEditText,userPasswordEditText);


        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checker.equals("clicked")){
                    uploadImage();
                    userInfoSaved();}//save info and image}
                else
                    updateOnlyUserInfo(); //save only info
            }
        });

        profileChangeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                //choosePicture();
                CropImage.activity(imageUri).setAspectRatio(1, 1)
                        .start( com.example.blooddonationapp.SettingActivity.this);
            }
        });



    }

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText userPhoneEditText, final EditText userNameEditText, final EditText userAddressEditText){
        String userId="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.child("image").exists()){
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();
                        Glide.with(com.example.blooddonationapp.SettingActivity.this)
                                .load(image)
                                .into(profileImageView);
                        userNameEditText.setText(name);
                        userPhoneEditText.setText(phone); userPhoneEditText.setSelection(phone.length());
                        userAddressEditText.setText(address);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            long time =new Date().getTime();

            FirebaseFirestore firebaseFirestore;
            firebaseFirestore=FirebaseFirestore.getInstance();
            auth.createUserWithEmailAndPassword( email, pass)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            firebaseFirestore.collection("Profiles")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .set(time+"");
                            Toast.makeText(getApplicationContext(), " uploadImage", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(com.example.blooddonationapp.SettingActivity.this, com.example.blooddonationapp.SettingsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });


            imageUri = result.getUri();
            System.out.println("imageUri : "+imageUri);
            Toast.makeText(this, "imageUri : "+imageUri, Toast.LENGTH_SHORT).show();
            profileImageView.setImageURI(imageUri);}

        else{
            Toast.makeText(this, "Error, Try again :(", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(com.example.blooddonationapp.SettingActivity.this, com.example.blooddonationapp.SettingsActivity.class));
            finish();
        }
    }





    private void uploadImageAnduserInfoSaved()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating Profile...");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference ref = storageProfilePictureRef.child(auth.getCurrentUser().getPhoneNumber() + ".jpg");
            ref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String phone=auth.getCurrentUser().getPhoneNumber();

                                String userId="";
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    userId = user.getUid();
                                }
                                UploadTask.TaskSnapshot downloadUrl = task.getResult();
                                myUrl = "Profile pictures/images/"+uploadUri;
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap. put("name", userNameEditText.getText().toString());
                                userMap. put("address", userAddressEditText.getText().toString());
                                userMap. put("phone", userPhoneEditText.getText().toString());
                                userMap. put("email", email);
                                userMap. put("pass", pass);
                                //userMap. put("bloodType", bloodTypeTxt.getSelectedItem().toString());
                                // userMap.put("gender", genderTxt.getSelectedItem().toString());
                                userMap. put("image", myUrl);
                                db.collection("Users")
                                        .document(userId)
                                        .set(userMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "DocumentSnapshot successfully written.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "error.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                startActivity(new Intent(com.example.blooddonationapp.SettingActivity.this, HomeActivity.class));
                                Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "Profile Info update successfully***.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });}
                    else{
                        String userId="";
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if (user != null) {
                            userId = user.getUid();
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap. put("name", userNameEditText.getText().toString());
                        userMap. put("address", userAddressEditText.getText().toString());
                        userMap. put("phone", userPhoneEditText.getText().toString());
                        userMap. put("email", email);
                        userMap. put("pass",pass);
                        // userMap. put("bloodType", bloodTypeTxt.getSelectedItem().toString());
                        //  userMap.put("gender", genderTxt.getSelectedItem().toString());
                        userMap. put("image", "Profile pictures/images/23aedb0f-2bc3-4470-94a8-58e8bcc48e82");

                        db.collection("Users")
                                .document(userId)
                                .set(userMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "DocumentSnapshot successfully written.", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "error.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        startActivity(new Intent(com.example.blooddonationapp.SettingActivity.this, HomeActivity.class));
                        Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "Profile Info update successfully***.", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            });}
        else
        {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage()
    {
        if (imageUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            uploadUri= UUID.randomUUID().toString();
            // Defining the child of storageReference
            ref = storageProfilePictureRef.child("images/" +uploadUri);

            // adding listeners on upload
            // or failure of image
            ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {

                            // Image uploaded successfully
                            // Dismiss dialog
                            progressDialog.dismiss();
                            Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        // Progress Listener for loading
                        // percentage on the dialog box
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int)progress + "%");
                        }
                    });
        }
    }

    private void userInfoSaved()
    {
        if (TextUtils.isEmpty(userNameEditText.getText().toString()))
        {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userAddressEditText.getText().toString()))
        {
            Toast.makeText(this, "Address is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userPhoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Phone Number is mandatory.", Toast.LENGTH_SHORT).show();
        }

        else if(checker.equals("clicked"))
        {
            uploadImageAnduserInfoSaved();
        }
    }

    private void updateOnlyUserInfo()
    {
        String userId="";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userId = user.getUid();
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> userMap = new HashMap<>();
        if(!TextUtils.isEmpty(userNameEditText.getText().toString())){
            userMap. put("name", userNameEditText.getText().toString());
        }
        if(!TextUtils.isEmpty(userAddressEditText.getText().toString()))
            userMap. put("address", userAddressEditText.getText().toString());
        if(!TextUtils.isEmpty(userPhoneEditText.getText().toString()))
            userMap. put("phone", userPhoneEditText.getText().toString());


        userMap. put("email",email);
        userMap. put("pass", pass);
        db.collection("Users")
                .document(userId)
                .set(userMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "DocumentSnapshot successfully written.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "error.", Toast.LENGTH_SHORT).show();
                    }
                });
        startActivity(new Intent(com.example.blooddonationapp.SettingActivity.this, HomeActivity.class));
        Toast.makeText(com.example.blooddonationapp.SettingActivity.this, "Profile Info update successfully***.", Toast.LENGTH_SHORT).show();
        finish();
    }

}








