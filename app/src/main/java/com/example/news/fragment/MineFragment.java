package com.example.news.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.news.R;

import com.example.news.utils.XUtilsDate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    private Button myButton;
    private EditText register_phone;
    private TextView save_user;
    private EditText register_password;
    private EditText register_username;
    private EditText register_repassword;
    private EditText register_yzm;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_register, container, false);

        initView();
        return view;
    }

    @Override
    public View initView() {
        myButton = view.findViewById(R.id.btn_code);
        save_user = view.findViewById(R.id.save_user);
        register_password = view.findViewById(R.id.register_password);
        register_username = view.findViewById(R.id.register_username);
        register_repassword = view.findViewById(R.id.register_repassword);
        register_yzm = view.findViewById(R.id.register_yzm);
        myButton.setOnClickListener(this);
        save_user.setOnClickListener(this);
        register_phone = view.findViewById(R.id.register_phone);
        return view;
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_code:
                String mobile = register_phone.getText().toString().trim();
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(mActivity, "手机号不能是空", Toast.LENGTH_SHORT).show();
                } else {
                    String data = "name=";
                    //  initListData1(data);
                    XUtilsDate xUtils = new XUtilsDate();
                    xUtils.onSmsPost(view, mobile);
                }
                break;

            case R.id.save_user:
                String moblie =register_phone.getText().toString().trim();
                String password = register_password.getText().toString();
                String username = register_username.getText().toString().trim();
                String code = register_yzm.getText().toString().trim();


                break;

            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }


    }


}
