package com.example.shudong10.spalsh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shudong10.Internet;
import com.example.shudong10.MainActivity;
import com.example.shudong10.MsgEntity;
import com.example.shudong10.R;
import com.example.shudong10.databinding.FragmentLoginBinding;

import static com.example.shudong10.Internet.*;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentLoginBinding binding;
    private Handler handler;
    private  MsgEntity msgEntity;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        binding=FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.arg1){
                    case 0:
                        Toast.makeText(getContext(),"账号或密码错误，请重新输入！", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        binding.login.setText("登录成功，跳转中。。。");
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                requireActivity().startActivity(mainIntent);
                                requireActivity().finish();
                            }
                        },2000);


                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + msg.arg1);
                }
                return false;
            }
        });


        binding.toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller= Navigation.findNavController(v);
                controller.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //演示模拟
                /*binding.login.setText("登录成功，跳转中。。。");
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
                        requireActivity().startActivity(mainIntent);
                        requireActivity().finish();
                    }
                },2000);
*/

                msgEntity=MsgEntity.getMsgEntity();
                msgEntity.setUser(binding.user.getText().toString());
                msgEntity.setPwd(binding.pwd.getText().toString());
                msgEntity.setType(1);

                Internet.sendUdpData(MsgEntity.toJsonString(msgEntity), handler, new onMsgReturnedListener() {
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