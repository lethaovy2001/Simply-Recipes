package com.example.simplyrecipes.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simplyrecipes.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText regUserName;
    EditText regEmail;
    EditText regPassword;
    Button regButton;
    Button loginButton;
    String email;
    String  password;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        regUserName = findViewById(R.id.editTextUsername);
        regEmail = findViewById(R.id.editTextTextEmailAddress);
        regPassword = findViewById(R.id.editTextTextPassword);
        regButton = findViewById(R.id.sign_up_button);
        loginButton = findViewById(R.id.sign_in_button);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(regUserName.getText().toString().isEmpty() || regEmail.getText().toString().isEmpty() || regPassword.getText().toString().isEmpty()){
                        Toast.makeText(SignUpActivity.this, "Please fill in empty fields", Toast.LENGTH_SHORT).show();
                    }else{
                        username = regUserName.getText().toString();
                        password = regPassword.getText().toString();
                        email = regEmail.getText().toString();
                        trySignUp();
                    }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.this.finish();
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });



    }

    private void trySignUp() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // BEGIN registering new user to the database
                            // initialize the new user's with Favorite Recipes branch
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Favorite")
                                    .child("none").child("none").setValue("none"); // start with dummy data as firebase won't allow there to be an empty branch

                            // then the user's pantry
                            FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().
                                    getCurrentUser().getUid())
                                    .child("Pantry").child("none").setValue("none");

                            // then the shopping list
                            FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid()).child("Shopping List").child("none").setValue("none");
                            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                            startActivity(intent);
                            SignUpActivity.this.finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Sign Up failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}