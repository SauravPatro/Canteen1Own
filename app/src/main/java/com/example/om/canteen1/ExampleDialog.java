package com.example.om.canteen1;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.om.canteen1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Om on 2/27/2018.
 */

public class ExampleDialog extends DialogFragment {

    private TextView tvName;
    private EditText etQuantity;
    private ExampleDialogListener listener;
    private String uidEditPro;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_layout,null);
        builder.setView(view).setTitle("Enter Quantity").setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth=FirebaseAuth.getInstance();
                userRef= FirebaseDatabase.getInstance().getReference().child("orders");
                uidEditPro=mAuth.getCurrentUser().getUid();
                String quantity=etQuantity.getText().toString();
                String name=tvName.getText().toString();
                listener.applyTexts(name,quantity);

               /* userRef.child(uidEditPro).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        userRef.child(mAuth.getCurrentUser().getUid()).child("itemNo").setValue(quantity);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });*/
                if(mAuth.getCurrentUser()!=null)
                {
                    userRef.child(mAuth.getCurrentUser().getUid()).child("1").setValue(quantity);
                }
                Toast.makeText(getActivity(),"hii"+quantity,Toast.LENGTH_LONG).show();

            }
        });
        tvName=view.findViewById(R.id.tvNameDialog);
        String idliii="idli";
        tvName.setText(idliii);
        etQuantity=view.findViewById(R.id.etQuantityDialog);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            listener=(ExampleDialogListener)context;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener
    {
        void applyTexts(String name,String quantity);
    }


}
