package com.example.tot_educational;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.tot_educational.Activity.BookmarkActivity;
import com.example.tot_educational.Activity.EditprofileActivity;
import com.example.tot_educational.Fragment.CommunityFragment;
import com.example.tot_educational.Fragment.HomeFragment;
import com.example.tot_educational.Fragment.PdfFragment;
import com.example.tot_educational.Fragment.VideosFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    BottomNavigationView navigationView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("token",s);
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map);
                    }
                });

        if (isconnected()) {
        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            // Setting Alert Dialog Title
            alertDialogBuilder.setTitle("NO INTERNET CONNECTION");
            // Icon Of Alert Dialog
            alertDialogBuilder.setIcon(R.drawable.wifi);
            // Setting Alert Dialog Message
            alertDialogBuilder.setMessage("Please check your network connection");
            alertDialogBuilder.setCancelable(false);

            alertDialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav=(NavigationView)findViewById(R.id.navmenu);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        navigationView=(BottomNavigationView) findViewById(R.id.bottomNavigationView);
//        nav.getMenu().getItem(0).setChecked(true);

        frameLayout=findViewById(R.id.frameLayout);
        setFragment(new HomeFragment());

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_my_home:
                        setFragment(new HomeFragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_my_book_mark:
                        startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_my_profile:
                        startActivity(new Intent(getApplicationContext(), EditprofileActivity.class));
                        break;

                    case R.id.nav_my_rate_us:
                        Intent intent1 = new Intent(Intent.ACTION_VIEW);
                        intent1.setData(Uri.parse(
                                "https://play.google.com/store/apps/details?id=com.example.bibhutisirclasses"));
                        intent1.setPackage("com.android.vending");
                        startActivity(intent1);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_my_about:
                        Toast.makeText(getApplicationContext(), "My Order", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_my_send:
                        try {
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Bibhuti sir classes");
                            String sharesms = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                            intent.putExtra(Intent.EXTRA_TEXT, sharesms);
                            startActivity(Intent.createChooser(intent, "Share by"));
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_my_customer_support:
                        String[] addresses={"bibhutisirclasses@gmail.com"};
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                      //  intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                        startActivity(Intent.createChooser(intent,"Email"));

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }

                return true;
            }
        });

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
//                        video_fregment fm=new video_fregment();
//                        FragmentTransaction tranjaction = getSupportFragmentManager().beginTransaction();
//                        tranjaction.replace(R.id.fremLayout,fm);
//                        tranjaction.commit();
                        setFragment(new HomeFragment());
                        item.setChecked(true);
                        break;
                    case R.id.community:
                        setFragment(new CommunityFragment());
                        item.setChecked(true);
                        break;
                    case R.id.pdf:
                        setFragment(new PdfFragment());
                        item.setChecked(true);
                        break;
                    case R.id.videos:
                        setFragment(new VideosFragment());
                        item.setChecked(true);
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id== R.id.main_refresh){
            startActivity(new Intent(MainActivity.this,MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private boolean isconnected(){
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() !=null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
