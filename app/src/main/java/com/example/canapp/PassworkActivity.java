package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;
import com.example.canapp.model.UserLogin;
import com.example.canapp.model.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassworkActivity extends AppCompatActivity {

    private View passwordLayout;
    private float deltaY;
    EditText edt_pass,edt_pass_again;
    TextView tv_noti_pass,tv_noti_pass_again,tv_login;
    ImageView img_back;
    ApiService apiService;
    UserRegister userRegister;

    Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwork);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        String text = "<font color=#001E6C>Bạn đã có tài khoản CAN?</font> <font color=#FFAA4C>Đăng nhập ngay</font>";
        AnhXa();
        SetThongBao();
        tv_login.setText(Html.fromHtml(text));
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassworkActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dangky();
            }
        });
        passwordLayout.setOnTouchListener(new View.OnTouchListener() {
            private float startY;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY(); // Lấy tọa độ Y ban đầu của ngón tay
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        deltaY = event.getRawY() - startY;
                        // Áp dụng hiệu ứng chuyển động dựa trên khoảng cách chuyển động
                        passwordLayout.setTranslationY(deltaY);

                        // Áp dụng hiệu ứng động lực (acceleration) cho chuyển động
                        passwordLayout.animate()
                                .setInterpolator(new AccelerateInterpolator())
                                .setDuration(2000)
                                .translationY(0f)
                                .start();
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(deltaY) > 500) {
                            finish();
                        }
                        return true;
                    default:
                        return false;
                }

            }
        });
    }
    public void AnhXa(){

        passwordLayout = findViewById(R.id.passwordLayout);
        edt_pass=findViewById(R.id.edt_password);
        edt_pass_again=findViewById(R.id.edt_password_again);
        tv_noti_pass=findViewById(R.id.tv_noti_pass);
        tv_noti_pass_again=findViewById(R.id.tv_noti_pass_again);
        img_back=findViewById(R.id.img_back);
        tv_login = findViewById(R.id.tv_login);
        btn_register=findViewById(R.id.btn_register);
    }
    public void Dangky(){
        Bundle bundle = getIntent().getExtras();
        User user1 = (User) bundle.getSerializable("user1");
        String pass = edt_pass.getText().toString();
        user1.setPassword(pass);
        //Toast.makeText(getApplicationContext(), user1.getPassword().toString(), Toast.LENGTH_SHORT).show();
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        //Thuc hien API register
        Call<UserRegister> call = apiService.register(user1.getName().toString(),user1.getEmail().toString(),
                user1.getPassword().toString(),user1.getAddress(), user1.getMajor().toString(),
                user1.getPhone().toString(), user1.getBirthday().toString());
        call.enqueue(new Callback<UserRegister>() {
            @Override
            public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                try {
                    if (response.isSuccessful()) {
                        userRegister = response.body();
                        if (! userRegister.isError()){
                            Toast.makeText(PassworkActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PassworkActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(PassworkActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserRegister> call, Throwable t) {

            }
        });
    }
    public void SetThongBao(){
        edt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*[a-zA-Z]).{8,13}$";
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti_pass.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_pass.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_pass_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                if (string.length() == 0 || !string.equals(edt_pass)){
                    tv_noti_pass_again.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_pass_again.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}