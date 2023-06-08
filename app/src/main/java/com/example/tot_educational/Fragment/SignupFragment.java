package com.example.tot_educational.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tot_educational.MainActivity;
import com.example.tot_educational.Model.User;
import com.example.tot_educational.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignupFragment extends Fragment {

    TextView already_have_an_account;
    FrameLayout frameLayout;

    EditText email,name,password,conform_password,phone;
    ImageView profileImage;
    Uri selectedImage;
    Button signup;
    ProgressBar progressBar;

    FirebaseAuth auth;
    FirebaseDatabase firebaseDataba;
    FirebaseStorage storage;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_signup, container, false);
        already_have_an_account=view.findViewById(R.id.already_have_an_account);
        frameLayout=getActivity().findViewById(R.id.frameLayout);

        email=view.findViewById(R.id.sign_up_email);
        name=view.findViewById(R.id.sign_up_name);
        password=view.findViewById(R.id.sign_up_password);
        phone=view.findViewById(R.id.sign_up_phone);
        conform_password=view.findViewById(R.id.sign_up_conform_password);
        profileImage=view.findViewById(R.id.profile_image);

        signup=view.findViewById(R.id.sign_up_btn);
        progressBar=view.findViewById(R.id.sign_up_progressbar);

        auth= FirebaseAuth.getInstance();
        firebaseDataba=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        conform_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkedInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkemailandPassword();
            }
        });

        already_have_an_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });
        return view;
    }

    private void checkemailandPassword() {

        Drawable customErrorIcon=getResources().getDrawable(R.drawable.custom_error_icon);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());
        if (email.getText().toString().matches(emailPattern)){
        if (!phone.getText().toString().isEmpty()) {
            if (password.getText().toString().equals(conform_password.getText().toString())) {

                progressBar.setVisibility(View.VISIBLE);
                signup.setEnabled(false);
                signup.setTextColor(Color.argb(50, 255, 255, 255));
                auth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if (selectedImage != null) {
                                        StorageReference reference = storage.getReference().child("profiles").child(auth.getUid());
                                        reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String imageUri = uri.toString();

                                                            String uid = auth.getUid();
                                                            String phone1 = phone.getText().toString();
                                                            String fullName = name.getText().toString();
                                                            String email1 = email.getText().toString();

                                                            User user = new User(uid, fullName, phone1, imageUri, email1,"");

                                                            firebaseDataba.getReference()
                                                                    .child("users")
                                                                    .child(uid)
                                                                    .setValue(user)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            progressBar.setVisibility(View.INVISIBLE);
                                                                            startActivity(new Intent(getActivity(), MainActivity.class));
                                                                        }
                                                                    });
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }else {
                                        String uid = auth.getUid();
                                        String phone1 = phone.getText().toString();
                                        String fullName = name.getText().toString();
                                        String email1 = email.getText().toString();

                                        User user = new User(uid, fullName, phone1, "No Image", email1,"");

                                        firebaseDataba.getReference()
                                                .child("users")
                                                .child(uid)
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        startActivity(new Intent(getActivity(), MainActivity.class));
                                                    }
                                                });
                                    }


                                } else {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signup.setEnabled(true);
                                    signup.setTextColor(Color.rgb(255, 255, 255));
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                conform_password.setError("Doesn't match email!");
            }
        }else {
            phone.setError("Enter phone number");
        }
        }else {
            email.setError("Invalid Email!");
        }

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void checkedInput() {
        if (!TextUtils.isEmpty(email.getText())){
            if (!TextUtils.isEmpty(name.getText())){
                if (!TextUtils.isEmpty(password.getText()) && password.length() >=8){
                    if (!TextUtils.isEmpty(conform_password.getText()) && conform_password.length() >=8){
                        signup.setEnabled(true);
                        signup.setTextColor(Color.rgb(255,255,255));
                    }else {
                        signup.setEnabled(false);
                        signup.setTextColor(Color.argb(50,255,255,255));
                    }
                }else {
                    signup.setEnabled(false);
                    signup.setTextColor(Color.argb(50,255,255,255));
                }
            }else {
                signup.setEnabled(false);
                signup.setTextColor(Color.argb(50,255,255,255));
            }
        }else {
            signup.setEnabled(false);
            signup.setTextColor(Color.argb(50,255,255,255));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data !=null){
            if (data.getData() !=null){
                profileImage.setImageURI(data.getData());
                Glide
                        .with(getContext())
                        .load(data.getData())
                        .into(profileImage);
                selectedImage=data.getData();
            }
        }
    }
}