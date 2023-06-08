package com.example.tot_educational.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tot_educational.Model.QuestionModel;
import com.example.tot_educational.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
    public static final String FILE_NAME = "QUIZZER";
    public static final String KEY_NAME = "QUESTIONS";

    int count = 0;
    TextView questions, questions_indicator;
    AppCompatButton share_btn, next_btn;
    LinearLayout options_contaner;
    FloatingActionButton book_mark;
    public List<QuestionModel> list;
    int position = 0;
    int Score = 0;
    String subject;
    int setsNo;

    ArrayList<QuestionModel> bookmarkList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Gson gson;
    int matchQuestionPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        questions = findViewById(R.id.questions);
        questions_indicator = findViewById(R.id.question_indicator);
        share_btn = findViewById(R.id.share_btn);
        next_btn = findViewById(R.id.next_btn);
        options_contaner = findViewById(R.id.options_contaner);
        book_mark = findViewById(R.id.book_mark);

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();

        getBookmark();
        book_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelMatch()) {
                    bookmarkList.remove(matchQuestionPosition);
                    book_mark.setImageDrawable(getDrawable(R.drawable.book_mark));
                } else {
                    bookmarkList.add(list.get(position));
                    book_mark.setImageDrawable(getDrawable(R.drawable.bookmark_border));
                }
            }
        });
        subject = getIntent().getStringExtra("subject");
        setsNo = getIntent().getIntExtra("id", 1);


        list = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Questions").child(subject)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            QuestionModel questionModel = dataSnapshot1.getValue(QuestionModel.class);

                            if (questionModel.getSetsNo() == setsNo) {
                                list.add(questionModel);
                            }
                        }
                        if (list.size() > 0) {
                            for (int i = 0; i < 4; i++) {
                                options_contaner.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        checkAnswer((Button) v);
                                    }
                                });
                            }
                            playAnim(questions, 0, list.get(position).getQuestion());
                            next_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    next_btn.setEnabled(true);
                                    next_btn.setAlpha(0.7f);
                                    enableOption(true);
                                    position++;
                                    if (position == list.size()) {

                                        Map<String, Object> map = new HashMap<>();
                                        map.put("percentage", String.valueOf(Score)+"/"+String.valueOf(list.size()));
                                        FirebaseDatabase.getInstance().getReference().child("setsClicked")
                                                .child(subject)
                                                .child(String.valueOf(setsNo))
                                                .child(FirebaseAuth.getInstance().getUid())
                                                .updateChildren(map);

                                        //Score Acticity
                                        Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                                        intent.putExtra("total", list.size());
                                        intent.putExtra("score", Score);
                                        startActivity(intent);
                                        return;
                                    }
                                    count = 0;
                                    playAnim(questions, 0, list.get(position).getQuestion());
                                }
                            });
                            share_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String body = list.get(position).getQuestion() + "\n" +
                                            list.get(position).getOptionA() + "\n" +
                                            list.get(position).getOptionB() + "\n" +
                                            list.get(position).getOptionC() + "\n" +
                                            list.get(position).getOptionD();
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_SUBJECT, "Bibhuti sir classes quizzer challenge");
                                    intent.putExtra(Intent.EXTRA_TEXT, body);
                                    startActivity(Intent.createChooser(intent, "Share via"));
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "No Questions", Toast.LENGTH_SHORT).show();
                            Map<String, Object> map = new HashMap<>();
                            map.put("percentage", "");
                            FirebaseDatabase.getInstance().getReference().child("setsClicked")
                                    .child(subject)
                                    .child(String.valueOf(setsNo))
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(map);
                            finish();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmark();
    }

    private void playAnim(View view, int value, final String data) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        if (value == 0 && count < 4) {
                            String option = "";
                            if (count == 0) {
                                option = list.get(position).getOptionA();
                            } else if (count == 1) {
                                option = list.get(position).getOptionB();
                            } else if (count == 2) {
                                option = list.get(position).getOptionC();
                            } else if (count == 3) {
                                option = list.get(position).getOptionD();
                            }
                            playAnim(options_contaner.getChildAt(count), 0, option);
                            count++;
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value == 0) {
                            try {
                                ((TextView) view).setText(data);
                                questions_indicator.setText(position + 1 + "/" + list.size());
                                if (modelMatch()) {
                                    book_mark.setImageDrawable(getDrawable(R.drawable.bookmark_border));
                                } else {
                                    book_mark.setImageDrawable(getDrawable(R.drawable.book_mark));
                                }
                            } catch (ClassCastException e) {
                                ((Button) view).setText(data);
                            }
                            view.setTag(data);
                            playAnim(view, 1, data);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }

    private void checkAnswer(Button selectedOption) {
        enableOption(false);
        next_btn.setEnabled(true);
        next_btn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectANS())) {
            //correct Answer
            Score++;
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF04")));
        } else {
            //incorrect Answer
            selectedOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Button correctOption = (Button) options_contaner.findViewWithTag(list.get(position).getCorrectANS());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00FF04")));
        }
    }

    private void enableOption(boolean enable) {
        for (int i = 0; i < 4; i++) {
            options_contaner.getChildAt(i).setEnabled(enable);
            if (enable) {
                options_contaner.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#C8C8C8")));
            }
        }
    }

    private void getBookmark() {

        String json = preferences.getString(KEY_NAME, "");

        Type type = new TypeToken<List<QuestionModel>>() {
        }.getType();

        bookmarkList = gson.fromJson(json, type);

        if (bookmarkList == null) {
            bookmarkList = new ArrayList<>();
        }
    }

    private boolean modelMatch() {
        boolean matched = false;
        int i = 0;
        for (QuestionModel model : bookmarkList) {
            if (model.getQuestion().equals(list.get(position).getQuestion()) &&
                    model.getCorrectANS().equals(list.get(position).getCorrectANS())) {
                matched = true;
                matchQuestionPosition = i;
            }
            i++;
        }
        return matched;
    }

    private void storeBookmark() {
        String json = gson.toJson(bookmarkList);
        editor.putString(KEY_NAME, json);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (list.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("percentage", "Failed");
            FirebaseDatabase.getInstance().getReference().child("setsClicked")
                    .child(subject)
                    .child(String.valueOf(setsNo))
                    .child(FirebaseAuth.getInstance().getUid())
                    .updateChildren(map);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (list.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("percentage", "Failed");
            FirebaseDatabase.getInstance().getReference().child("setsClicked")
                    .child(subject)
                    .child(String.valueOf(setsNo))
                    .child(FirebaseAuth.getInstance().getUid())
                    .updateChildren(map);
        }
    }
}