package com.tf.aning.ui.spalsh;

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


import com.google.gson.Gson;
import com.tf.aning.Aning;
import com.tf.aning.MainActivity;
import com.tf.aning.MyOkhttp;
import com.tf.aning.R;
import com.tf.aning.databinding.FragmentRegisterBinding;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
                                new Handler().postDelayed(new Runnable() {
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
                String email=binding.email.getText().toString();
                String pwd=binding.pwd.getText().toString();
                String name=binding.userName.getText().toString();
                Aning aning=new Aning();
                aning.setName(name);
                aning.setEmail(email);
                aning.setPassword(pwd);
                Gson gson=new Gson();
                MyOkhttp.getINSTANCE().register(gson.toJson(aning));
                MyOkhttp.getINSTANCE().getCall().enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d("login", "onFailure: "+e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String s=response.body().string();
                        Map<Object,String> map=gson.fromJson(s,Map.class);
                        if (((String)map.get("code")).equals("200")){
                            binding.register.setText("注册成功，登录中。。。");
                            requireActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), (String) map.get("msg"), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                            getActivity().startActivity(mainIntent);
                            getActivity().finish();

                        }
                        else requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), (String) map.get("msg"), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });
    }
}