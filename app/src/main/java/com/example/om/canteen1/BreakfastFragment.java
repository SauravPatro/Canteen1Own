package com.example.om.canteen1;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import static android.content.ContentValues.TAG;

/**
 * Created by Om on 2/26/2018.
 */

public class BreakfastFragment extends Fragment //implements ExampleDialog.ExampleDialogListener//
{

    /*private EditText quantityDialog;
    private TextView nameDialog;
    private Button cancelDialog;
    private String quantity;
    private Dialog d;
    */
    private FirebaseAuth mAuth;
    RecyclerView list;
    DatabaseReference ref;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items_lists,container,false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*CardView idli;

        idli=view.findViewById(R.id.idli_1);
        idli.setOnClickListener(new View.OnClickListener() {
            //String name="IDLI";
            @Override
            public void onClick(View v) {

                openDialog();
                //Toast.makeText(getActivity(),"hii"+quantity,Toast.LENGTH_LONG).show();
            }
        });

        //Toast.makeText(getActivity(),"hii"+quantity,Toast.LENGTH_LONG).show();
    }
    public void openDialog()
    {
        ExampleDialog exampleDialog=new ExampleDialog();
        exampleDialog.show(getActivity().getSupportFragmentManager(),"Example dialog");
        /*Button setDialog;
        d=new Dialog(getActivity());
        d.setContentView(R.layout.dialog_layout);

        d.show();
        d.setCanceledOnTouchOutside(false);
        nameDialog=(TextView) view.findViewById(R.id.tvNameDialog);
        quantityDialog=(EditText) view.findViewById(R.id.etQuantityDialog);
        setDialog=(Button) view.findViewById(R.id.btnSetDialog);
        cancelDialog=(Button) view.findViewById(R.id.btnCancelDialog);


        //nameDialog.setText(name);
       setDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=Long.parseLong(quantityDialog.getText().toString());
                d.dismiss();
            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

    }


    @Override
    public void applyTexts(String name, String quantity) {

        //this.quantity=quantity;
        //Log.d(TAG, "hi "+quantity);
        //Toast.makeText(getActivity(),"hii"+quantity,Toast.LENGTH_LONG).show();
        //Toast.makeText(getActivity(),"hiii"+quantity,Toast.LENGTH_LONG).show();
    }*/

        FirebaseApp.initializeApp(getActivity());
        list=view.findViewById(R.id.list);
        ref= FirebaseDatabase.getInstance().getReference().child("item").child("breakfast");
        ref.keepSynced(true);
        mAuth=FirebaseAuth.getInstance();
        FirebaseRecyclerAdapter<ItemsPOJO,BreakfastFragment.UsersViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ItemsPOJO, BreakfastFragment.UsersViewHolder>(
                ItemsPOJO.class,
                R.layout.card_layout,
                BreakfastFragment.UsersViewHolder.class,
                ref
        ) {
            @Override
            protected void populateViewHolder(BreakfastFragment.UsersViewHolder viewHolder, final ItemsPOJO model, final int position) {
                viewHolder.setA(model.getName(),model.getPrice());
                viewHolder.setPhoto(model.getUrl(),getActivity());
                viewHolder.card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mAuth.getCurrentUser()==null){
                            Toast.makeText(getContext(), "You must login to make orders", Toast.LENGTH_SHORT).show();
                        }else {
                            new AddDialog(getContext(), model.getItemNo(),mAuth.getCurrentUser().getUid(),model.getName(),model.getPrice(),"brkfast" );
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
