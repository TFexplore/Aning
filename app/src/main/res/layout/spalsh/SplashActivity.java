package com.example.shudong10.spalsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shudong10.MainActivity;
import com.example.shudong10.R;
import com.example.shudong10.UITool.textTool;
import com.example.shudong10.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private int isFisrst;
    private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySplashBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        String s1="寻找专属于你的树洞";
        String s2="--诉说你心底的秘密";
        textTool tool1=new textTool(binding.textView4,s1,100,0);
       binding.ctx.setText(s2, AnimationUtils.loadAnimation(this, R.anim.myanim), 150);

        load();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(true){//isFisrst==1
                Intent mainIntent = new Intent(SplashActivity.this,LoginActivity.class);
                save();
                SplashActivity.this.startActivity(mainIntent);}
               else {
                    Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
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
