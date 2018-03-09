package com.example.om.canteen1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Om on 2/4/2018.
 */

public class EditProfileFragment extends Fragment {

    private EditText regdNoEditText,nameEditText,emailEditText;
    private Button saveBtn;
    private String regdNoString;
    private String nameString;
    private String emailString;
    private String uidEditPro;

    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth=FirebaseAuth.getInstance();
        userRef= FirebaseDatabase.getInstance().getReference().child("users");

        regdNoEditText= view.findViewById(R.id.edRegdNoEditPro);
        nameEditText=view.findViewById(R.id.edNameEditPro);
        emailEditText=view.findViewById(R.id.edEmailEditPro);

        saveBtn=view.findViewById(R.id.saveBtnEditPro);

        uidEditPro=mAuth.getCurrentUser().getUid();

        userRef.child(uidEditPro).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameString= dataSnapshot.child("name").getValue().toString();
                regdNoString=dataSnapshot.child("regNo").getValue().toString();
                emailString=dataSnapshot.child("email").getValue().toString();
                nameEditText.setText(nameString);
                emailEditText.setText(emailString);
                regdNoEditText.setText(regdNoString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        regdNoEditText.setEnabled(false);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString=nameEditText.getText().toString();
                emailString=emailEditText.getText().toString();
                userRef.child(uidEditPro).child("name").setValue(nameString);
                userRef.child(uidEditPro).child("email").setValue(emailString);
                Intent mainActIntent=new Intent(getActivity(),MainActivity.class);
                startActivity(mainActIntent);
            }
        });
    }
}
