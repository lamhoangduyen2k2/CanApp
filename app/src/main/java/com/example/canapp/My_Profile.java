package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;
import com.example.canapp.ulti.SharedPrefManager;

import retrofit2.Call;

public class My_Profile extends AppCompatActivity {

    Toolbar top_bar;
    ImageView img_menu;
    ImageView img_avatar;
    ConstraintLayout constrain_project;
    TextView tv_username, tv_email,tv_address,tv_major,tv_phone,tv_birthday;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        AnhXa();
        if(SharedPrefManager.getInstance(this).getUser() != null){
            user = SharedPrefManager.getInstance(this).getUser();
            tv_username.setText(user.getName());
            tv_phone.setText(user.getPhone());
            tv_major.setText(user.getMajor());
            tv_email.setText(user.getEmail());
            tv_address.setText(user.getAddress());
            Glide.with(this).load(user.getAvatar()).into(img_avatar);
        }
//        Toast.makeText(this, user.getAddress().toString(), Toast.LENGTH_SHORT).show();
        setSupportActionBar(top_bar);
        getSupportActionBar().setTitle("");
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu();
            }
        });
        GetEditProfile();
    }
    public void AnhXa(){
        top_bar = findViewById(R.id.top_bar);
        img_menu=findViewById(R.id.img_menu);
        constrain_project=findViewById(R.id.constraint_project);
        tv_address=findViewById(R.id.tv_address);
        tv_birthday=findViewById(R.id.tv_birthday);
        tv_email=findViewById(R.id.tv_email);
        tv_major=findViewById(R.id.tv_major);
        tv_phone=findViewById(R.id.tv_phone);
        tv_username=findViewById(R.id.tv_username);
        img_avatar=findViewById(R.id.img_update);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void popupMenu(){
        PopupMenu popup = new PopupMenu(this, this.img_menu);
        popup.inflate(R.menu.bottom_navigation);

        Menu menu = popup.getMenu();
        // Register Menu Item Click event.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        // Show the PopupMenu.
        popup.show();
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuReset:
                Intent intent = new Intent(My_Profile.this,ResetPassword_Login.class);
                startActivity(intent);
                break;
            case R.id.menuLogout:
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                break;
            case R.id.menuResetProfile:
                intent = new Intent(My_Profile.this,EditInformation.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void GetEditProfile(){
        constrain_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(My_Profile.this,AllMyProject.class);
                startActivity(intent);
            }
        });
    }


}