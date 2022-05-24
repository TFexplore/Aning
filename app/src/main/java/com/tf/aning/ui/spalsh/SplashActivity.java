package com.tf.aning.ui.spalsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.tf.aning.MainActivity;
import com.tf.aning.R;
import com.tf.aning.databinding.ActivitySplashBinding;


public class SplashActivity extends AppCompatActivity {
    private int isFisrst;
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        load();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(true){//isFisrst==1
                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                save();
                SplashActivity.this.startActivity(mainIntent);}
               else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
               }
               SplashActivity.this.finish();
            }
        },2000);

    }
    void load() {
        SharedPreferences shp=getSharedPreferences("my_data", Context.MODE_PRIVATE);
        isFisrst=shp.getInt("isfisrst_open",0);
        Log.d("TAG", "load: "+isFisrst);

    }
    void save(){
        SharedPreferences shp=getApplication().getSharedPreferences("my_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        editor.putInt("isfisrst_open",1);
        editor.apply();
    }

}
