package com.example.tot_educational.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tot_educational.Adapter.BookmarkAdapter;
import com.example.tot_educational.MainActivity;
import com.example.tot_educational.Model.QuestionModel;
import com.example.tot_educational.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    public static final String FILE_NAME="QUIZZER";
    public static final String KEY_NAME="QUESTIONS";

    RecyclerView recyclerView;
    ImageView back;
    ArrayList<QuestionModel> list;
    ArrayList<QuestionModel> bookmarkList;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView=findViewById(R.id.recyclerView);
        back=findViewById(R.id.back);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        preferences=getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
        gson=new Gson();

        getBookmark();;

        BookmarkAdapter adapter=new BookmarkAdapter(bookmarkList);
        recyclerView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookmarkActivity.this, MainActivity.class));
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        storeBookmark();
    }
    private void getBookmark(){

        String json=preferences.getString(KEY_NAME,"");

        Type type=new TypeToken<List<QuestionModel>>() {}.getType();

        bookmarkList=gson.fromJson(json,type);

        if (bookmarkList==null){
            bookmarkList =new ArrayList<>();
        }
    }

    private void storeBookmark(){
        String json=gson.toJson(bookmarkList);
        editor.putString(KEY_NAME,json);
        editor.commit();
    }
}