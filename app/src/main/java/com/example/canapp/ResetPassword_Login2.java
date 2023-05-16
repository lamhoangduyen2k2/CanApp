package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword_Login2 extends AppCompatActivity {

    EditText edt_pass,edt_pass_again;
    TextView tv_noti,tv_noti_pass_again;
    Button btn_reset;
    ImageView img_back;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_login2);
        AnhXa();
        DieuKhienNoti();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset_Pass();
            }
        });
    }
    public void AnhXa(){
        edt_pass = findViewById(R.id.edt_password);
        edt_pass_again = findViewById(R.id.edt_password_again);
        tv_noti=findViewById(R.id.tv_noti);
        btn_reset = findViewById(R.id.btn_reset);
        img_back=findViewById(R.id.img_back);
        tv_noti_pass_again=findViewById(R.id.tv_noti_pass_again);
    }
   public void DieuKhienNoti(){

        tv_noti.setVisibility(View.INVISIBLE);
        edt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex = "^.{8,13}$";
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti.setVisibility(View.VISIBLE);
                    edt_pass.setY(300);
                    edt_pass_again.setY(450);
                    tv_noti_pass_again.setVisibility(View.INVISIBLE);
                }
                else{
                    tv_noti.setVisibility(View.INVISIBLE);
                    edt_pass.setY(212);
                    edt_pass_again.setY(374);
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
                else{
                    tv_noti_pass_again.setVisibility(View.INVISIBLE);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void Reset_Pass(){

    }
}