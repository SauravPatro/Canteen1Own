package com.example.om.canteen1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * Created by Om on 3/3/2018.
 */

public class SnacksFragment extends Fragment {
    private FirebaseAuth mAuth;
    RecyclerView list;
    DatabaseReference ref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items_lists,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseApp.initializeApp(getActivity());
        list=view.findViewById(R.id.list);
        ref= FirebaseDatabase.getInstance().getReference().child("item").child("snacks");
        ref.keepSynced(true);
        mAuth=FirebaseAuth.getInstance();
        FirebaseRecyclerAdapter<ItemsPOJO,SnacksFragment.UsersViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ItemsPOJO,SnacksFragment.UsersViewHolder>(
                ItemsPOJO.class,
                R.layout.card_layout,
                SnacksFragment.UsersViewHolder.class,
                ref
        ) {
            @Override
            protected void populateViewHolder(SnacksFragment.UsersViewHolder viewHolder, final ItemsPOJO model, final int position) {
                viewHolder.setA(model.getName(),model.getPrice());
                viewHolder.setPhoto(model.getUrl(),getActivity());
                viewHolder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mAuth.getCurrentUser()==null){
                            Toast.makeText(getContext(), "You must login to make orders", Toast.LENGTH_SHORT).show();
                        }else {
                            new AddDialog(getContext(), model.getItemNo(),mAuth.getCurrentUser().getUid(),model.getName(),model.getPrice(),"snks");
                        }
                    }
                });
            }
        };
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(firebaseRecyclerAdapter);

    }
    public static class UsersViewHolder extends RecyclerView.ViewHolder{
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
        public void setPhoto(final String url, final Context context){
            Picasso.with(context).load(url).resize(150,150).centerCrop().networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.aluchop)
                    .into(pic, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso.with(context).load(url).resize(150,150).centerCrop().placeholder(R.drawable.aluchop).into(pic);
                        }
                    });
        }

    }
}

