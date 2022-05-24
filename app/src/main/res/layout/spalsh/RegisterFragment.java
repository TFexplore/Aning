package com.example.shudong10.spalsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shudong10.ClientEntity;
import com.example.shudong10.Internet;
import com.example.shudong10.MainActivity;
import com.example.shudong10.MsgEntity;
import com.example.shudong10.R;
import com.example.shudong10.databinding.FragmentRegisterBinding;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentRegisterBinding binding;
    private Handler handler;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentRegisterBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler=new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message msg) {
                        switch (msg.arg1){
                            case 0:
                                Toast.makeText(getContext(),"账号已存在，请重新输入！", Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                binding.register.setText("注册成功，登录中。。。");
                                new android.os.Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                        getActivity().startActivity(mainIntent);
                                        getActivity().finish();
                                    }
                                },2000);


                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + msg.arg1);
                        }
                        return false;
                    }
                });


         binding.toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //演示模拟
                binding.register.setText("注册成功，登录中。。。");
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ClientEntity clientEntity=new ClientEntity();
                        clientEntity.setName("爱吃橘子");
                        SharedPreferences shp=requireActivity().getSharedPreferences("my_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shp.edit();
                        editor.putString("my_data",new Gson().toJson(clientEntity));
                        editor.apply();


                        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                        getActivity().startActivity(mainIntent);
                        getActivity().finish();
                    }
                },2000);


                MsgEntity msgEntity=MsgEntity.getMsgEntity();
                msgEntity.setName(binding.userName.getText().toString());
                msgEntity.setUser(binding.user.getText().toString());
                msgEntity.setPwd(binding.pwd.getText().toString());
                msgEntity.setEmail(binding.email.getText().toString());
                msgEntity.setType(0);

                Log.d(TAG, "onClickregister: "+MsgEntity.toJsonString(msgEntity));
                Internet.sendUdpData(MsgEntity.toJsonString(msgEntity), handler, new Internet.onMsgReturnedListener() {
                    @Override
                    public void onMsgReturned(MsgEntity msgEntity) {

                        SharedPreferences shp=requireActivity().getSharedPreferences("my_data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=shp.edit();

                        editor.putString("my_data",msgEntity.getMsg());
                        editor.apply();
                    }

                    @Override
                    public void onError(Exception ex) {

                    }
                });


            }
        });
    }
}