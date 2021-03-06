package com.example.phalguns.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

        private Button btnChangeEmail, btnChangePassword, btnSendResetEmail, btnRemoveUser,
                changeEmail, changePassword, sendEmail, remove, signOut,update,verify_email_button;

        private EditText oldEmail, newEmail, password, newPassword;
        private ProgressBar progressBar;
        private FirebaseAuth.AuthStateListener authListener;
        private FirebaseAuth auth;
        private FirebaseDatabase mFireBaseDataBase;
        private DatabaseReference mRef;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            //get firebase auth instance
            auth = FirebaseAuth.getInstance();
            //write data to firebase database
            mFireBaseDataBase = FirebaseDatabase.getInstance();
            mRef = mFireBaseDataBase.getReference();
            mRef.child("State").setValue("Karnataka");
            mRef.child("Street").setValue("Malleswaram 15th cross");
            mRef.child("No").setValue("2888");

            //get current user
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user == null) {
                        // user auth state is changed - user is null
                        // launch login activity
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            };



            btnChangeEmail = (Button) findViewById(R.id.change_email_button);
            btnChangePassword = (Button) findViewById(R.id.change_password_button);
            btnSendResetEmail = (Button) findViewById(R.id.sending_pass_reset_button);
            btnRemoveUser = (Button) findViewById(R.id.remove_user_button);
            changeEmail = (Button) findViewById(R.id.changeEmail);
            changePassword = (Button) findViewById(R.id.changePass);
            sendEmail = (Button) findViewById(R.id.send);
            remove = (Button) findViewById(R.id.remove);
            signOut = (Button) findViewById(R.id.sign_out);
            update = (Button) findViewById(R.id.update);
            verify_email_button = (Button) findViewById(R.id.verify_email_button);


            oldEmail = (EditText) findViewById(R.id.old_email);
            newEmail = (EditText) findViewById(R.id.new_email);
            password = (EditText) findViewById(R.id.password);
            newPassword = (EditText) findViewById(R.id.newPassword);

            oldEmail.setVisibility(View.GONE);
            newEmail.setVisibility(View.GONE);
            password.setVisibility(View.GONE);
            newPassword.setVisibility(View.GONE);
            changeEmail.setVisibility(View.GONE);
            changePassword.setVisibility(View.GONE);
            sendEmail.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);

            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            if (progressBar != null) {
                progressBar.setVisibility(View.GONE);
            }

           /* if(user.isEmailVerified()){
                verify_email_button.setVisibility(View.GONE);
            }else {
                verify_email_button.setVisibility(View.VISIBLE);

            }*/

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateData();
                }
            });

            btnChangeEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldEmail.setVisibility(View.GONE);
                    newEmail.setVisibility(View.VISIBLE);
                    password.setVisibility(View.GONE);
                    newPassword.setVisibility(View.GONE);
                    changeEmail.setVisibility(View.VISIBLE);
                    changePassword.setVisibility(View.GONE);
                    sendEmail.setVisibility(View.GONE);
                    remove.setVisibility(View.GONE);
                }
            });

            verify_email_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    user.sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    findViewById(R.id.verify_email_button).setEnabled(true);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this,
                                                "Verification email sent to " + user.getEmail(),
                                                Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(MainActivity.this,
                                                "Failed to send verification email.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            });

            changeEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (user != null && !newEmail.getText().toString().trim().equals("")) {
                        user.updateEmail(newEmail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Email address is updated. Please sign in with new email id!", Toast.LENGTH_LONG).show();
                                            signOut();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to update email!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    } else if (newEmail.getText().toString().trim().equals("")) {
                        newEmail.setError("Enter email");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

            btnChangePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldEmail.setVisibility(View.GONE);
                    newEmail.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                    newPassword.setVisibility(View.VISIBLE);
                    changeEmail.setVisibility(View.GONE);
                    changePassword.setVisibility(View.VISIBLE);
                    sendEmail.setVisibility(View.GONE);
                    remove.setVisibility(View.GONE);
                }
            });

            changePassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (user != null && !newPassword.getText().toString().trim().equals("")) {
                        if (newPassword.getText().toString().trim().length() < 6) {
                            newPassword.setError("Password too short, enter minimum 6 characters");
                            progressBar.setVisibility(View.GONE);
                        } else {
                            user.updatePassword(newPassword.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(MainActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
                                                signOut();
                                                progressBar.setVisibility(View.GONE);
                                            } else {
                                                Toast.makeText(MainActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }
                    } else if (newPassword.getText().toString().trim().equals("")) {
                        newPassword.setError("Enter password");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

            btnSendResetEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oldEmail.setVisibility(View.VISIBLE);
                    newEmail.setVisibility(View.GONE);
                    password.setVisibility(View.GONE);
                    newPassword.setVisibility(View.GONE);
                    changeEmail.setVisibility(View.GONE);
                    changePassword.setVisibility(View.GONE);
                    sendEmail.setVisibility(View.VISIBLE);
                    remove.setVisibility(View.GONE);
                }
            });

            sendEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (!oldEmail.getText().toString().trim().equals("")) {
                        auth.sendPasswordResetEmail(oldEmail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Reset password email is sent!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    } else {
                        oldEmail.setError("Enter email");
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

            btnRemoveUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (user != null) {
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(MainActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                            finish();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MainActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                    }
                }
            });

            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });

        }

        //sign out method
        public void signOut() {
            auth.signOut();
        }

        @Override
        protected void onResume() {
            super.onResume();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onStart() {
            super.onStart();
            auth.addAuthStateListener(authListener);
        }

        @Override
        public void onStop() {
            super.onStop();
            if (authListener != null) {
                auth.removeAuthStateListener(authListener);
            }
            }


    private void updateData() {
        Toast.makeText(this, "kjkckskb", Toast.LENGTH_SHORT).show();
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myref = database.getReference();
        myref.child("Users").child("Id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("Username").setValue("palghundixit@gmail.com");


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }


}


