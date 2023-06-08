package com.example.tot_educational.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.tot_educational.Fragment.SigninFragment;
import com.example.tot_educational.R;

public class Registration extends AppCompatActivity {

    FrameLayout frameLayout;
    public static  boolean onresetPassword=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        frameLayout=findViewById(R.id.frameLayout);

        setDefaultFregment(new SigninFragment());
    }

    private void setDefaultFregment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (onresetPassword) {
                onresetPassword =false;
                setFragment(new SigninFragment());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    public void setFragment(SigninFragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}