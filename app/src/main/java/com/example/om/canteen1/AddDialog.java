package com.example.om.canteen1;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Om on 3/1/2018.
 */

public class AddDialog extends Dialog {
    private DatabaseReference itemRef;
    private DatabaseReference orderRef;
    private TextView name;
    private EditText quantity,total;
    private Button addToCart;
    public AddDialog(@NonNull final Context context, String itemNumber, String uid, final String item_name, final String price, final String type) {
        super(context);
        setContentView(R.layout.add_dialog);
        setCanceledOnTouchOutside(false);
        show();
        orderRef=FirebaseDatabase.getInstance().getReference().child("orders").child(uid);
        name=findViewById(R.id.dialog_item_name);
        quantity=findViewById(R.id.dialog_item_quantity);
        total=findViewById(R.id.dialog_item_total);
        addToCart=findViewById(R.id.dialog_add_button);
        name.setText(item_name);

        orderRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String prevQuantity;
                String storeName=item_name+"_"+type;
                if(dataSnapshot.hasChild(storeName)){
                    prevQuantity=dataSnapshot.child(storeName).getValue().toString();
                }else {
                    prevQuantity="0";
                }
                quantity.setText(prevQuantity);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                long totalPrice;
                int quantity;
                if(TextUtils.isEmpty(s)){
                    totalPrice=0;
                    quantity=0;
                }else {
                    totalPrice = Integer.parseInt(s.toString()) * Integer.parseInt(price);
                    quantity=Integer.parseInt(s.toString());
                }
                total.setText(quantity+" X "+price+" ="+totalPrice);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemQuantity=quantity.getText().toString();
                String storeName=item_name+"_"+type;
                if(!(TextUtils.isEmpty(itemQuantity) || itemQuantity.equals("0"))) {
                    orderRef.child(storeName).setValue(itemQuantity).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "added to cart successfully !", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            dismiss();
                        }
                    });
                }else{
                    Toast.makeText(context, "You must set the quantity !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
