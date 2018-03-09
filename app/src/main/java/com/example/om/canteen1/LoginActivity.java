package com.example.om.canteen1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText regNoEditText,passwordEditText;
    private Button loginButton;
    private TextView signUpTextView,forgotPassTextView;
    private String regNoString,passwordString,regNoWithMailString="";

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        regNoEditText=findViewById(R.id.edRegdNoLogin);
        passwordEditText=findViewById(R.id.edPassLogin);
        loginButton=findViewById(R.id.loginBtnLogin);
        signUpTextView=findViewById(R.id.txtSignUpLogin);
        forgotPassTextView=findViewById(R.id.forgetPassLogin);

        mAuth=FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                regNoString=regNoEditText.getText().toString();
                passwordString=passwordEditText.getText().toString();

                if(regNoString.length()==0||passwordString.length()==0) {
                    regNoEditText.setText("");
                    passwordEditText.setText("");
                    Toast.makeText(getApplicationContext(), "!!!TEXT CANNOT BE BLANK!!!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    regNoWithMailString=regNoString+"@iter.com";
                    mAuth.signInWithEmailAndPassword(regNoWithMailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(LoginActivity.this,task.getException().getMessage().toString(),Toast.LENGTH_LONG).show();

                            }
                            else
                            {
                                Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });

                }
            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        forgotPassTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
                if(mAuth.getCurrentUser()!=null)
                {
                    Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

    }
    /*static  class userViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView name,age;
        public userViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            name=mView.findViewById(R.id.textView);

        }
    }*/
}
