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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public FirebaseUser user; // firebase reference to the currently logged in user
    List<Recipe> favoriteRecipes;
    CurrentUser currentUser;
    DatabaseReference reference;
    EditText loginEmail;
    EditText loginPassword;
    Button signInButton;
    Button signUpButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        favoriteRecipes = new ArrayList<>();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.editTextTextEmailAddress);
        loginPassword = findViewById(R.id.editTextTextPassword);
        signInButton = findViewById(R.id.sign_in_button);
        signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginEmail.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()){
                    Toast.makeText(SignInActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    String email = loginEmail.getText().toString();
                    String password = loginPassword.getText().toString();
                    signIn(email, password);
                }
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignIn", "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            initializeFavoriteRecipes();
                            ApplicationClass.currentUser = new CurrentUser(mAuth.getUid(), mAuth.getCurrentUser().getEmail(),favoriteRecipes);
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignIn", "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initializeFavoriteRecipes() {
        int length = 0;
        reference = FirebaseDatabase.getInstance().getReference("Favorites/users/"+ mAuth.getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()) {
                    int recipeID = 0; // testing purposes
                    if(snap.getValue().toString() != "") {
                        recipeID = Integer.parseInt(snap.getValue().toString());
                    }

                    String recipeName = snap.getKey().toString();
                    if (recipeID == 0) {
                        continue;
                    } else {
                        Recipe recipe = new Recipe(recipeID, recipeName);
                        favoriteRecipes.add(recipe);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error" + error.getMessage());
            }
        });
    }

}