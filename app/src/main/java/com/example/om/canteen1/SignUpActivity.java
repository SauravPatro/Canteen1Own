package com.example.om.canteen1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText regNoEditText,passwordEditText,nameEditText,conPasswordEditText,emailEditText;
    private Button signUpButton;
    private TextView loginTextView;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    private String regNoString,passString,nameString,conPassString,emailString,regNoWithMailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regNoEditText=findViewById(R.id.edRegdNoSignUp);
        nameEditText=findViewById(R.id.edNameSignUp);
        passwordEditText=findViewById(R.id.edPassSignUp);
        conPasswordEditText=findViewById(R.id.edConPassSignUp);
        emailEditText=findViewById(R.id.edEmailSignUp);
        signUpButton=findViewById(R.id.signUpBtnSignUp);
        loginTextView=findViewById(R.id.tvLoginSignUp);

        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference().child("users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regNoString=regNoEditText.getText().toString();
                passString=passwordEditText.getText().toString();
                nameString=nameEditText.getText().toString();
                conPassString=conPasswordEditText.getText().toString();
                emailString=emailEditText.getText().toString();
                if(regNoString.length()==0||passString.length()==0||nameString.length()==0||conPassString.length()==0||emailString.length()==0) {
                    regNoEditText.setText("");
                    passwordEditText.setText("");
                    nameEditText.setText("");
                    conPasswordEditText.setText("");
                    emailEditText.setText("");
                    Toast.makeText(getApplicationContext(), "!!!TEXT CANNOT BE BLANK!!!", Toast.LENGTH_LONG).show();
                }
                else if (passString.equals(conPassString)==false)
                {

                    passwordEditText.setText("");

                    conPasswordEditText.setText("");

                    Toast.makeText(getApplicationContext(), "!!!PASSWORD NOT MATCHED!!!", Toast.LENGTH_LONG).show();
                }
                else {
                    regNoWithMailString=regNoString+"@iter.com";
                    mAuth.createUserWithEmailAndPassword(regNoWithMailString,passString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                userRef.child(task.getResult().getUser().getUid()).child("name").setValue(nameString);
                                userRef.child(task.getResult().getUser().getUid()).child("email").setValue(emailString);
                                userRef.child(task.getResult().getUser().getUid()).child("regNo").setValue(regNoString);
                                mAuth.signInWithEmailAndPassword(regNoWithMailString,passString);
                            }
                        }
                    });

                }

            }
        });
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mAuth.getCurrentUser()!=null)
                {
                    Intent mainIntent=new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });
    }
}
