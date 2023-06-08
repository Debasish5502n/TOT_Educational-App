package com.example.tot_educational.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.tot_educational.Adapter.BannerAdapter;
import com.example.tot_educational.Adapter.CategoryCourseAdapter;
import com.example.tot_educational.Adapter.CompletedCourseAdapter;
import com.example.tot_educational.Adapter.RecentlyStartedCourseAdapter;
import com.example.tot_educational.Adapter.UpcomingCourseAdapter;
import com.example.tot_educational.Adapter.categoryAdapter;
import com.example.tot_educational.Model.BannerModel;
import com.example.tot_educational.Model.CategoryCourseModel;
import com.example.tot_educational.Model.ChategoryModel;
import com.example.tot_educational.Model.UpcomingCourseModel;
import com.example.tot_educational.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    ImageSlider imageSlider;
    ShimmerRecyclerView recyclerView;
    ArrayList<ChategoryModel> list;
    ArrayList<BannerModel> bannerModels;
    RecyclerView courseRecyclerView,upComingRecyclerView,bannerRecyclerView,completedRecyclerView,recentlyStartedRecyclerView;
    ArrayList<CategoryCourseModel> categoryCourseModels;
    CategoryCourseAdapter categoryCourseAdapter;
    BannerAdapter bannerAdapter;
    NestedScrollView scrollView;

    TextView name;
    CircleImageView profileImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider=view.findViewById(R.id.image_slider);
        final List<SlideModel> remoteImages=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        courseRecyclerView=view.findViewById(R.id.category_recyclerView);
        upComingRecyclerView=view.findViewById(R.id.upcoming_recyclerView);
        bannerRecyclerView=view.findViewById(R.id.banner_recyclerView);
        completedRecyclerView=view.findViewById(R.id.completed_recyclerView);
        recentlyStartedRecyclerView=view.findViewById(R.id.recently_started_recyclerView);
        scrollView = view.findViewById(R.id.scrollView);
        name = view.findViewById(R.id.name);
        profileImage = view.findViewById(R.id.circleImageView);

        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.setSmoothScrollingEnabled(true);

        upcomingCourse();
        recentlyStartedCourse();
        completedCourse();

        list=new ArrayList<>();
        categoryAdapter adapter=new categoryAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.showShimmerAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        courseRecyclerView.setLayoutManager(layoutManager);
        categoryCourseModels=new ArrayList<>();

        categoryCourseAdapter=new CategoryCourseAdapter(categoryCourseModels);
        courseRecyclerView.setAdapter(categoryCourseAdapter);

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String title = snapshot.child("name").getValue(String.class);
                String profile = snapshot.child("profileImage").getValue(String.class);

                name.setText(title);
                if (profile.equals("No Image")){
                    Glide.with(getContext()).load(profile).placeholder(R.drawable.avatar);
                }else {
                    Glide.with(getContext()).load(profile).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference().child("courseCategory")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            categoryCourseModels.add(dataSnapshot1.getValue(CategoryCourseModel.class));
                        }
                        categoryCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference().child("category")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            list.add(dataSnapshot1.getValue(ChategoryModel.class));
                        }
                        adapter.notifyDataSetChanged();
                        recyclerView.hideShimmerAdapter();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        FirebaseDatabase.getInstance().getReference().child("Slider")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren())
                            remoteImages.add(new SlideModel(data.child("url").getValue().toString(),data.child("title").getValue().toString(),ScaleTypes.FIT));

                        imageSlider.setImageList(remoteImages,ScaleTypes.FIT);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        LinearLayoutManager bannerLayout = new LinearLayoutManager(getContext());
        bannerLayout.setOrientation(RecyclerView.HORIZONTAL);
        bannerRecyclerView.setLayoutManager(bannerLayout);
        bannerModels=new ArrayList<>();

        bannerAdapter=new BannerAdapter(getActivity(),bannerModels);
        bannerRecyclerView.setAdapter(bannerAdapter);
        final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(bannerRecyclerView);

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (bannerLayout.findLastCompletelyVisibleItemPosition() < (bannerAdapter.getItemCount() - 1)) {

                    bannerLayout.smoothScrollToPosition(bannerRecyclerView, new RecyclerView.State(), bannerLayout.findLastCompletelyVisibleItemPosition() + 1);
                }

                else if (bannerLayout.findLastCompletelyVisibleItemPosition() == (bannerAdapter.getItemCount() - 1)) {

                    bannerLayout.smoothScrollToPosition(bannerRecyclerView, new RecyclerView.State(), 0);
                }
            }
        }, 0, 4000);

        FirebaseDatabase.getInstance().getReference().child("Banner")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            BannerModel bannerModel1 =dataSnapshot1.getValue(BannerModel.class);

                            if (bannerModel1.getBanner_image().equals("https://tse3.mm.bing.net/th?id=OIP.zsEgRepQ6Uh5OYkkhJyn2gHaE5&pid=Api&P=0")) {
                                bannerModels.add(bannerModel1);
                            }
                        }
                        bannerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return view;
    }

    private void upcomingCourse(){
        ArrayList<UpcomingCourseModel> upcomingCourseModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        upComingRecyclerView.setLayoutManager(layoutManager);
        UpcomingCourseAdapter upcomingCourseAdapter=new UpcomingCourseAdapter(getContext(),upcomingCourseModels);
        upComingRecyclerView.setAdapter(upcomingCourseAdapter);

        FirebaseDatabase.getInstance().getReference().child("Course")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            UpcomingCourseModel upcomingCourseModel = dataSnapshot1.getValue(UpcomingCourseModel.class);

                            if (upcomingCourseModel.getCourseUpdate().equals("Upcoming")) {
                                upcomingCourseModels.add(upcomingCourseModel);
                            }
                        }
                        upcomingCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void recentlyStartedCourse(){
        ArrayList<UpcomingCourseModel> upcomingCourseModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recentlyStartedRecyclerView.setLayoutManager(layoutManager);
        RecentlyStartedCourseAdapter recentlyStartedCourseAdapter=new RecentlyStartedCourseAdapter(getContext(),upcomingCourseModels);
        recentlyStartedRecyclerView.setAdapter(recentlyStartedCourseAdapter);

        FirebaseDatabase.getInstance().getReference().child("Course")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            UpcomingCourseModel upcomingCourseModel = dataSnapshot1.getValue(UpcomingCourseModel.class);

                            if (upcomingCourseModel.getCourseUpdate().equals("Started")) {
                                upcomingCourseModels.add(upcomingCourseModel);
                            }
                        }
                        recentlyStartedCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void completedCourse(){
        ArrayList<UpcomingCourseModel> upcomingCourseModels = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        completedRecyclerView.setLayoutManager(layoutManager);
        CompletedCourseAdapter completedCourseAdapter=new CompletedCourseAdapter(getContext(),upcomingCourseModels);
        completedRecyclerView.setAdapter(completedCourseAdapter);

        FirebaseDatabase.getInstance().getReference().child("Course")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                            UpcomingCourseModel upcomingCourseModel = dataSnapshot1.getValue(UpcomingCourseModel.class);

                            if (upcomingCourseModel.getCourseUpdate().equals("Completed")) {
                                upcomingCourseModels.add(upcomingCourseModel);
                            }
                        }
                        completedCourseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}