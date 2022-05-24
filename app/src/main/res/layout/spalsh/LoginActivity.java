package com.example.shudong10.spalsh;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.shudong10.R;

public class LoginActivity extends AppCompatActivity {
    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_login);
        controller= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,controller);

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
