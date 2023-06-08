package com.example.tot_educational.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.example.tot_educational.Activity.VideoPlayerActivity;
import com.example.tot_educational.Activity.addvideo;
import com.example.tot_educational.Adapter.myviewholder;
import com.example.tot_educational.Model.filemodel;
import com.example.tot_educational.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideosFragment extends Fragment {

    FloatingActionButton floatingActionButton;
    ShimmerRecyclerView recview;
    DatabaseReference likereference;
    Boolean testclick=false;
    FirebaseRecyclerAdapter<filemodel, myviewholder> firebaseRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_videos, container, false);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        recview=view.findViewById(R.id.recView);
        likereference=FirebaseDatabase.getInstance().getReference("likes");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), addvideo.class);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recview.setLayoutManager(layoutManager);

        FirebaseRecyclerOptions<filemodel> options =
                new FirebaseRecyclerOptions.Builder<filemodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("myvideos"), filemodel.class)
                        .build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<filemodel, myviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull filemodel model) {
                holder.prepareexoplayer(getActivity().getApplication(),model.getTitle(),model.getVurl(),model.getPurl());

                holder.image_thumbnel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), VideoPlayerActivity.class);
                        intent.putExtra("position",model.getVurl());
                        startActivity(intent);
                      //  Toast.makeText(getActivity(), video, Toast.LENGTH_SHORT).show();
                    }
                });
                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                final String userid=firebaseUser.getUid();
                final String postkey=getRef(position).getKey();

                holder.getlikebuttonstatus(postkey,userid);

                holder.like_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        testclick=true;

                        likereference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(testclick==true)
                                {
                                    if(snapshot.child(postkey).hasChild(userid))
                                    {
                                        likereference.child(postkey).child(userid).removeValue();
                                        testclick=false;
                                    }
                                    else
                                    {
                                        likereference.child(postkey).child(userid).setValue(true);
                                        testclick=false;
                                    }

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                });
            }

            @NonNull
            @Override
            public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.video_singelrow,parent,false);
                return new myviewholder(view);
            }
        };

        firebaseRecyclerAdapter.startListening();
        recview.setAdapter(firebaseRecyclerAdapter);
        return view;
    }
//    private void releasePlayer(){
//        if (simpleExoPlayer !=null){
//            simpleExoPlayer.release();
//            simpleExoPlayer=null;
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        if (Util.SDK_INT<24){
//            releasePlayer();
//        }
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        if (Util.SDK_INT>=24){
//            releasePlayer();
//        }
//        super.onStop();
//    }
}