package com.example.om.canteen1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Om on 3/9/2018.
 */

public class CartFragment extends Fragment {
    private ListView list_cart;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("orders");
        list_cart = view.findViewById(R.id.list_cart);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        list_cart.setAdapter(arrayAdapter);
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() != null) {
                    ref = ref.child(mAuth.getCurrentUser().getUid());
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                                String bookskey = orderSnapshot.getKey();
                                String booksValue = orderSnapshot.getValue(String.class);
                                booksValue=bookskey+"-"+booksValue;
                                arrayList.add(booksValue);
                                arrayAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


        /*FirebaseListAdapter<OrdersPOJO> firebaseListAdapter = new FirebaseListAdapter<OrdersPOJO>(
                OrdersPOJO.class,
                R.layout.list_layout,

                ref
        ) {
            @Override
            protected void populateView(View v, OrdersPOJO model, int position) {

            }


                viewHolder.setA(model.getName(),model.getPrice());

                viewHolder.card.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View v){
                if (mAuth.getCurrentUser() == null) {
                    Toast.makeText(getContext(), "You must login to make orders", Toast.LENGTH_SHORT).show();
                } else {
                    new AddDialog(getContext(), model.getItemNo(), mAuth.getCurrentUser().getUid(), model.getName(), model.getPrice(), "beve");
                }
            }
            });

        };
        list_cart.setHasFixedSize(true);
        list_cart.setLayoutManager(new LinearLayoutManager(getActivity()));
        list_cart.setAdapter(firebaseListAdapter);
        */
    }
}
  /*  public static class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView textView;
        ImageView pic;
        CardView card;
        public UsersViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
            textView=mView.findViewById(R.id.text_view_card_layout);
            pic=mView.findViewById(R.id.image_view_card_layout);
            card=mView.findViewById(R.id.card_view_card_layout);
        }
        public void setA(String text,String text2)
        {
            String value=text+"\n"+text2;
            textView.setText(value);
        }


    }

*/


