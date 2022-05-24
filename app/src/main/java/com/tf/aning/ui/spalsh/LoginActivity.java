package com.tf.aning.ui.spalsh;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.tf.aning.R;


public class LoginActivity extends AppCompatActivity {
    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //状态栏文字颜色随主题色变化
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_login);
        controller= Navigation.findNavController(this,R.id.fragment);

    }

    @Override
    public boolean onSupportNavigateUp() {
        if (controller.getCurrentDestination().getId() == R.id.registerFragment) {
             controller.navigate(R.id.action_registerFragment_to_loginFragment);

        }
        else System.exit(0);
        return super.onSupportNavigateUp();

    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
