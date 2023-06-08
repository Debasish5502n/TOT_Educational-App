package com.example.tot_educational.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.bibhutisirclasses.Adapter.CustomUiController;
import com.example.tot_educational.Adapter.LessonCommentsAdapter;
import com.example.tot_educational.Adapter.LessonScoreAdapter;
import com.example.tot_educational.Model.LessonCommentsModel;
import com.example.tot_educational.Model.LessonScoreModel;
import com.example.tot_educational.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VideoPlayerActivity extends AppCompatActivity {
    SimpleExoPlayerView simpleExoPlayerView;
    SimpleExoPlayer simpleExoPlayer;
    String VideoID;
    ImageView enter, exit, screenShots;

    String lessonTitle, lessonVideo, lessonImage, lessonId, courseTitle, lessonYoutubeVideo;
    YouTubePlayerView youTubePlayerView;
    LinearLayout youtubePLayer_background;
    TextView poll_1, poll_2, poll_3, poll_4;
    LinearLayout poll_1_linear, poll_2_linear, poll_3_linear, poll_4_linear;
    SeekBar poll_1_progress, poll_2_progress, poll_3_progress, poll_4_progress;
    TextView poll_1_percentage, poll_2_percentage, poll_3_percentage, poll_4_percentage;
    ConstraintLayout poll_system, live_chat;

    ////Lesson Live Chat
    RecyclerView liveChatRecyclerView;
    ArrayList<LessonCommentsModel> lessonCommentsModels;
    LessonCommentsAdapter lessonCommentsAdapter;
    EditText lessonCommentBox;
    ImageView lessonCommentSentBtn;
    TextView lessonLiveWatching;
    ////Lesson Live Chat

    ////Lesson Score
    Dialog dialogLessonScore;
    ImageView pollScoreBtn;
    RecyclerView lessonScoreRecyclerView;
    LessonScoreAdapter lessonScoreAdapter;
    ArrayList<LessonScoreModel> lessonScoreModel;
    ////Lesson Score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        lessonTitle = getIntent().getStringExtra("lessonTitle");
        lessonVideo = getIntent().getStringExtra("lessonVideo");
        lessonYoutubeVideo = getIntent().getStringExtra("lessonYoutubeVideo");
        lessonImage = getIntent().getStringExtra("lessonImage");
        lessonId = getIntent().getStringExtra("id");
        courseTitle = getIntent().getStringExtra("courseTitle");

        VideoID = getIntent().getStringExtra("position");

        simpleExoPlayerView = findViewById(R.id.exoplayerview);
        // rotate = findViewById(R.id.rotate);
        youtubePLayer_background = (LinearLayout) findViewById(R.id.youtubePLayer_background);
        poll_system = findViewById(R.id.poll_system);

        poll_1_linear = (LinearLayout) findViewById(R.id.poll_1_linear);
        poll_2_linear = (LinearLayout) findViewById(R.id.poll_2_linear);
        poll_3_linear = (LinearLayout) findViewById(R.id.poll_3_linear);
        poll_4_linear = (LinearLayout) findViewById(R.id.poll_4_linear);

        poll_1 = findViewById(R.id.poll_1);
        poll_2 = findViewById(R.id.poll_2);
        poll_3 = findViewById(R.id.poll_3);
        poll_4 = findViewById(R.id.poll_4);

        poll_1_progress = findViewById(R.id.poll_1_progress);
        poll_2_progress = findViewById(R.id.poll_2_progress);
        poll_3_progress = findViewById(R.id.poll_3_progress);
        poll_4_progress = findViewById(R.id.poll_4_progress);

        poll_1_percentage = findViewById(R.id.poll_1_percentage);
        poll_2_percentage = findViewById(R.id.poll_2_percentage);
        poll_3_percentage = findViewById(R.id.poll_3_percentage);
        poll_4_percentage = findViewById(R.id.poll_4_percentage);

        live_chat = findViewById(R.id.live_chat);
        enter = findViewById(R.id.enter);
        exit = findViewById(R.id.exit);
        screenShots = findViewById(R.id.screenShots);

        ////Lesson Live Chat
        liveChatRecyclerView = findViewById(R.id.liveChatRecyclerView);
        lessonCommentBox = (EditText) findViewById(R.id.smsbox12);
        lessonCommentSentBtn = findViewById(R.id.sendbtn);
        lessonLiveWatching = findViewById(R.id.lesson_live_watching);
        setLessonComments();
        liveWatching();
        ////Lesson Live Chat

        ////Lesson Score
        dialogLessonScore = new Dialog(this);
        dialogLessonScore.setContentView(R.layout.dilog_lessons_score);
        dialogLessonScore.setCancelable(true);
        dialogLessonScore.getWindow().setBackgroundDrawable(getDrawable(R.drawable.banner_slider_baground));
        dialogLessonScore.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pollScoreBtn = findViewById(R.id.poll_score);
        lessonScoreRecyclerView = dialogLessonScore.findViewById(R.id.lesson_score_recyclerView);
        pollScore();
        ////Lesson Score

        youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        screenShots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                live_chat.setVisibility(View.VISIBLE);
                enter.setVisibility(View.GONE);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                live_chat.setVisibility(View.GONE);
                enter.setVisibility(View.VISIBLE);
            }
        });

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (!lessonYoutubeVideo.equals("")) {
                    youTubePlayerView.setVisibility(View.VISIBLE);
                    simpleExoPlayerView.setVisibility(View.GONE);
                    youtubePLayer_background.setVisibility(View.VISIBLE);
                    youtubePLayer_background.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                    String videoId = lessonYoutubeVideo;
                    youTubePlayer.loadVideo(videoId, 0);

                } else {
                    youtubePLayer_background.setVisibility(View.GONE);
                    simpleExoPlayerView.setVisibility(View.VISIBLE);
                    youTubePlayerView.setVisibility(View.GONE);
                    prepareexoplayer(getApplication(), lessonVideo);
                }
            }
        });

        Thread time = new Thread() {
            public void run() {
                try {
                    sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                            .child(lessonId)
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String poll = snapshot.child("poll").getValue(String.class);

                                    if (poll.equals("ON")) {
                                        poll_system.setVisibility(View.VISIBLE);
                                        pollSystem();

                                        Thread time = new Thread() {
                                            public void run() {
                                                try {
                                                    sleep(10000);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                } finally {
                                                    Map<String, Object> update = new HashMap<>();
                                                    update.put("poll", "OFF");

                                                    FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                                                            .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                }
                                                            });
                                                }
                                            }
                                        };
                                        time.start();
                                    } else {
                                        poll_system.setVisibility(View.GONE);
                                        Map<String, Object> update = new HashMap<>();
                                        update.put("totalPoll", String.valueOf(0));
                                        update.put("poll_1", String.valueOf(0));
                                        update.put("poll_2", String.valueOf(0));
                                        update.put("poll_3", String.valueOf(0));
                                        update.put("poll_4", String.valueOf(0));
                                        update.put("correctPoll", String.valueOf(0));
                                        FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                                                .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        progress();
                                                    }
                                                });
                                        poll_1_progress.setProgress(0);
                                        poll_2_progress.setProgress(0);
                                        poll_3_progress.setProgress(0);
                                        poll_4_progress.setProgress(0);

                                        poll_1_percentage.setText("");
                                        poll_2_percentage.setText("");
                                        poll_3_percentage.setText("");
                                        poll_4_percentage.setText("");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                }
            }
        };
        time.start();

    }

    private void pollScore() {
        pollScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLessonScore.show();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lessonScoreRecyclerView.setLayoutManager(layoutManager);
        lessonScoreModel = new ArrayList<>();

        lessonScoreAdapter = new LessonScoreAdapter(lessonScoreModel, this);
        lessonScoreRecyclerView.setAdapter(lessonScoreAdapter);

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue(String.class);
                        String image = snapshot.child("profileImage").getValue(String.class);
                        String uid = snapshot.child("uid").getValue(String.class);

                        FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                .child(lessonId)
                                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String lessonUid = snapshot.child("uid").getValue(String.class);
                                        if (uid.equals(lessonUid)) {

                                        } else {
                                            LessonScoreModel lessonScoreModel = new LessonScoreModel(name, image, uid, "0", "0", "0", "0");
                                            FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                                    .child(lessonId)
                                                    .child(FirebaseAuth.getInstance().getUid())
                                                    .setValue(lessonScoreModel)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                lessonScoreAdapter.notifyDataSetChanged();

                                                            } else {
                                                                String error = task.getException().getMessage();
                                                                Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                .child(lessonId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lessonScoreModel.clear();

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            lessonScoreModel.add(dataSnapshot1.getValue(LessonScoreModel.class));
                        }
                        Collections.sort(lessonScoreModel, LessonScoreModel.score);
                        lessonScoreAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void liveWatching() {
        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                .child(lessonId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String liveWatching = snapshot.child("liveWatching").getValue(String.class);
                        Map<String, Object> update = new HashMap<>();
                        update.put("liveWatching", String.valueOf(Long.parseLong(liveWatching) + 1));

                        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                                .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String liveWatching = snapshot.child("liveWatching").getValue(String.class);

                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                .child(lessonId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String liveWatching = snapshot.child("liveWatching").getValue(String.class);
                        lessonLiveWatching.setText(liveWatching);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void setLessonComments() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        liveChatRecyclerView.setLayoutManager(layoutManager);
        lessonCommentsModels = new ArrayList<>();

        lessonCommentsAdapter = new LessonCommentsAdapter(this, lessonCommentsModels);
        liveChatRecyclerView.setAdapter(lessonCommentsAdapter);

        FirebaseDatabase.getInstance().getReference().child("LessonsComments")
                .child(lessonId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        lessonCommentsModels.clear();

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            lessonCommentsModels.add(dataSnapshot1.getValue(LessonCommentsModel.class));
                        }
                        liveChatRecyclerView.hasPendingAdapterUpdates();
                        Collections.sort(lessonCommentsModels, LessonCommentsModel.latestChat);
                        lessonCommentsAdapter.notifyDataSetChanged();

                        liveChatRecyclerView.smoothScrollToPosition(liveChatRecyclerView.getAdapter().getItemCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        FirebaseDatabase.getInstance().getReference().child("users")
                .child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = snapshot.child("name").getValue(String.class);
                        String image = snapshot.child("profileImage").getValue(String.class);

                        lessonCommentSentBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String randomStr = UUID.randomUUID().toString().replace("-", "");
                                String timeStamp = String.valueOf(System.currentTimeMillis());
                                LessonCommentsModel lessonCommentsModel = new LessonCommentsModel(image, name, lessonCommentBox.getText().toString(), randomStr, FirebaseAuth.getInstance().getUid(), timeStamp);
                                FirebaseDatabase.getInstance().getReference().child("LessonsComments")
                                        .child(lessonId)
                                        .child(randomStr)
                                        .setValue(lessonCommentsModel)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    lessonCommentsAdapter.notifyDataSetChanged();
                                                    lessonCommentBox.setText("");
                                                } else {
                                                    String error = task.getException().getMessage();
                                                    Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void pollSystem() {
        FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                .child(lessonId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String poll1 = snapshot.child("poll_1").getValue(String.class);
                        String poll2 = snapshot.child("poll_2").getValue(String.class);
                        String poll3 = snapshot.child("poll_3").getValue(String.class);
                        String poll4 = snapshot.child("poll_4").getValue(String.class);
                        String totalPoll = snapshot.child("totalPoll").getValue(String.class);
                        String correctPoll = snapshot.child("correctPoll").getValue(String.class);

                        poll_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> lessonScoreUpdate = new HashMap<>();
                                lessonScoreUpdate.put("select_poll", "A");
                                FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                        .child(lessonId)
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .updateChildren(lessonScoreUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                updatePollScore(correctPoll);
                                            }
                                        });

                                Map<String, Object> update = new HashMap<>();
                                update.put("totalPoll", String.valueOf(Integer.parseInt(totalPoll) + 1));
                                update.put("poll_1", String.valueOf(Integer.parseInt(poll1) + 1));
                                FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                                        .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progress();
                                            }
                                        });
                            }
                        });
                        poll_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> lessonScoreUpdate = new HashMap<>();
                                lessonScoreUpdate.put("select_poll", "B");
                                FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                        .child(lessonId)
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .updateChildren(lessonScoreUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                updatePollScore(correctPoll);
                                            }
                                        });


                                Map<String, Object> update = new HashMap<>();
                                update.put("totalPoll", String.valueOf(Integer.parseInt(totalPoll) + 1));
                                update.put("poll_2", String.valueOf(Integer.parseInt(poll2) + 1));
                                FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                                        .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progress();
                                            }
                                        });
                            }
                        });
                        poll_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> lessonScoreUpdate = new HashMap<>();
                                lessonScoreUpdate.put("select_poll", "C");
                                FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                        .child(lessonId)
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .updateChildren(lessonScoreUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                updatePollScore(correctPoll);
                                            }
                                        });


                                Map<String, Object> update = new HashMap<>();
                                update.put("totalPoll", String.valueOf(Integer.parseInt(totalPoll) + 1));
                                update.put("poll_3", String.valueOf(Integer.parseInt(poll3) + 1));
                                FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                                        .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progress();
                                            }
                                        });
                            }
                        });
                        poll_4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Map<String, Object> lessonScoreUpdate = new HashMap<>();
                                lessonScoreUpdate.put("select_poll", "D");
                                FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                        .child(lessonId)
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .updateChildren(lessonScoreUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                updatePollScore(correctPoll);
                                            }
                                        });


                                Map<String, Object> update = new HashMap<>();
                                update.put("totalPoll", String.valueOf(Integer.parseInt(totalPoll) + 1));
                                update.put("poll_4", String.valueOf(Integer.parseInt(poll4) + 1));
                                FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                                        .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progress();
                                            }
                                        });
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void progress() {
        FirebaseDatabase.getInstance().getReference().child("LessonsPoll")
                .child(lessonId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String poll1 = snapshot.child("poll_1").getValue(String.class);
                        String poll2 = snapshot.child("poll_2").getValue(String.class);
                        String poll3 = snapshot.child("poll_3").getValue(String.class);
                        String poll4 = snapshot.child("poll_4").getValue(String.class);
                        String totalPoll = snapshot.child("totalPoll").getValue(String.class);

                        poll_1_progress.setMax(Integer.parseInt(totalPoll));
                        poll_2_progress.setMax(Integer.parseInt(totalPoll));
                        poll_3_progress.setMax(Integer.parseInt(totalPoll));
                        poll_4_progress.setMax(Integer.parseInt(totalPoll));

                        poll_1_progress.setProgress(Integer.parseInt(String.valueOf(poll1)));
                        poll_2_progress.setProgress(Integer.parseInt(String.valueOf(poll2)));
                        poll_3_progress.setProgress(Integer.parseInt(String.valueOf(poll3)));
                        poll_4_progress.setProgress(Integer.parseInt(String.valueOf(poll4)));

                        poll_1_percentage.setText(poll1);
                        poll_2_percentage.setText(poll2);
                        poll_3_percentage.setText(poll3);
                        poll_4_percentage.setText(poll4);

                        poll_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        poll_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        poll_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        poll_4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public void prepareexoplayer(Application application, String videourl) {
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(application, trackSelector);

            Uri videoURI = Uri.parse(videourl);
            // Toast.makeText(VideoPlayerActivity.this, String.valueOf(videoURI), Toast.LENGTH_SHORT).show();

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);

        } catch (Exception ex) {
            Log.d("Explayer Creshed", ex.getMessage().toString());
        }
//        rotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//
//                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//                }
//            }
//        });
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        poll_1_progress.setProgress(0);
        poll_2_progress.setProgress(0);
        poll_3_progress.setProgress(0);
        poll_4_progress.setProgress(0);

        poll_1_percentage.setText("");
        poll_2_percentage.setText("");
        poll_3_percentage.setText("");
        poll_4_percentage.setText("");

        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                .child(lessonId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String liveWatching = snapshot.child("liveWatching").getValue(String.class);
                        Map<String, Object> update = new HashMap<>();
                        update.put("liveWatching", String.valueOf(Long.parseLong(liveWatching) - 1));

                        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                                .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String liveWatching = snapshot.child("liveWatching").getValue(String.class);

                                            lessonLiveWatching.setText(liveWatching);
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void updatePollScore(String correctPoll1) {
        FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                .child(lessonId)
                .child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String total_poll = snapshot.child("total_poll").getValue(String.class);
                        String correct_poll = snapshot.child("correct_poll").getValue(String.class);
                        String wrong_poll = snapshot.child("wrong_poll").getValue(String.class);
                        String select_poll = snapshot.child("select_poll").getValue(String.class);

                        Map<String, Object> update = new HashMap<>();
                        update.put("total_poll", String.valueOf(Integer.parseInt(total_poll) + 1));
                        if (correctPoll1.equals(select_poll)) {
                            update.put("correct_poll", String.valueOf(Integer.parseInt(correct_poll) + 1));
                        } else {
                            update.put("wrong_poll", String.valueOf(Integer.parseInt(wrong_poll) + 1));
                        }

                        FirebaseDatabase.getInstance().getReference().child("LessonsPollScore")
                                .child(lessonId)
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(update)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void screenShots1() {
        try {
            String mPath = this.getExternalFilesDir(null).getAbsolutePath().toString() + "/temp" + ".png";
            // create bitmap screenshot
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //or you can share to test the method fast

            Uri uriPath = FileProvider.getUriForFile(this, getPackageName() + ".sharing.provider", imageFile);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.setClipData(ClipData.newRawUri("", uriPath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uriPath);
            startActivity(Intent.createChooser(intent, "Sharing to..."));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                .child(lessonId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String liveWatching = snapshot.child("liveWatching").getValue(String.class);
                        Map<String, Object> update = new HashMap<>();
                        update.put("liveWatching", String.valueOf(Long.parseLong(liveWatching) - 1));

                        FirebaseDatabase.getInstance().getReference().child("LessonsLiveWatching")
                                .child(lessonId).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            String liveWatching = snapshot.child("liveWatching").getValue(String.class);

                                            lessonLiveWatching.setText(liveWatching);
                                        } else {
                                            String error = task.getException().getMessage();
                                            Toast.makeText(VideoPlayerActivity.this, error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}