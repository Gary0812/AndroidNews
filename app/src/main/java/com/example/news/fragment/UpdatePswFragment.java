package com.example.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.news.R;
import com.example.news.model.NewsVo;
import com.example.news.model.UserVo;
import com.example.news.utils.AESUtil;
import com.example.news.utils.GsonUtil;
import com.example.news.utils.XUtilsDate;

import org.w3c.dom.Text;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.Map;

import static com.example.news.fragment.RegistFragment.isMobileNO;


public class UpdatePswFragment extends BaseFragment implements View.OnClickListener{

    private String url = "http://172.16.2.94:8080/wcmInf/";
    private View view;
    private Button btn_code;
    private TextView save_user;
    private EditText register_phone;
    private int countSeconds = 120;//倒计时秒数
    private Handler mCountHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (countSeconds > 0) {
                --countSeconds;
                btn_code.setText("(" + countSeconds + ")后获取验证码");
                mCountHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                countSeconds = 120;
                btn_code.setText("请重新获取验证码");
            }
        }
    };
    private EditText register_yzm;
    private EditText newPassword;
    private String mobile;
 private ImageView image_drawer_home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.pwd_forget, container, false);
        initView();
        return view;
    }

    @Override
    public View initView() {
        register_yzm = view.findViewById(R.id.register_yzm);
        newPassword = view.findViewById(R.id.newPassword);
        btn_code = view.findViewById(R.id.btn_code);
        save_user = view.findViewById(R.id.save_user);
        register_phone = view.findViewById(R.id.register_phone);
        btn_code.setOnClickListener(this);
        save_user.setOnClickListener(this);
        image_drawer_home = (ImageView) view.findViewById(R.id.image_drawer_home);
        image_drawer_home.setOnClickListener(this);
        return null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_code:
                mobile = register_phone.getText().toString().trim();
                if (countSeconds == 120) {
                    getMobiile(mobile);
                } else {
                    new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("不能重复发送").setPositiveButton("确定", null).setCancelable(true).show();

                }
                break;
            case R.id.save_user:
                updatePsw();
                break;
            case R.id.image_drawer_home:
                getFragmentManager().popBackStack();
                break;
        }

    }



    //获取验证码信息，判断是否有手机号码
    public void getMobiile(String mobile) {
        if ("".equals(mobile)) {
            Log.e("tag", "mobile=" + mobile);
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("手机号码不能为空").setPositiveButton("确定", null).setCancelable(true).show();
        } else if (isMobileNO(mobile) == false) {
            new AlertDialog.Builder(getActivity()).setTitle("提示").setMessage("请输入正确的手机号码").setPositiveButton("确定", null).setCancelable(true).show();
        } else {
            Toast.makeText(mActivity, "发送成功", Toast.LENGTH_SHORT).show();
            //获取验证码信息
            String data = "name=";
            //  initListData1(data);
            XUtilsDate xUtils = new XUtilsDate();

            xUtils.onSmsPost(view, mobile);
        }
    }

    public  void updatePsw()
    {
        String password= newPassword.getText().toString().trim();
        String code=  register_yzm.getText().toString().trim();
        mobile = register_phone.getText().toString().trim();

        String path = url + "updatePsw";
        RequestParams params = new RequestParams(path);

        params.addParameter("mobile", mobile);
        params.addParameter("code", code);
        params.addParameter("password", AESUtil.encrypt(password));

        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if (result.equals("修改成功\r\n")) {

                    LoginFragment loginFragment=new LoginFragment();
                    showFragment(UpdatePswFragment.this,loginFragment );
                    Toast.makeText(mActivity, "修改成功请重新登陆", Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(mActivity, result, Toast.LENGTH_SHORT).show();
                }

                //发送一条消息
                //  LoginFragment.handler.sendMessage(msg);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }


    /**
     * 公共方法： 从碎片fragment1跳转到碎片fragment2
     *
     * @param fragment1 当前fragment
     * @param fragment2 跳转后的fragment
     */
    public void showFragment(Fragment fragment1, Fragment fragment2) {
        // 获取 FragmentTransaction  对象
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        //如果fragment2没有被添加过，就添加它替换当前的fragment1
        if (!fragment2.isAdded()) {
            transaction.replace(R.id.lr, fragment2)
                    //加入返回栈，这样你点击返回键的时候就会回退到fragment1了
                    .addToBackStack(null)
                    // 提交事务
                    .commitAllowingStateLoss();

        } else { //如果已经添加过了的话就隐藏fragment1，显示fragment2
            transaction
                    // 隐藏fragment1，即当前碎片
                    .hide(fragment1)
                    // 显示已经添加过的碎片，即fragment2
                    .show(fragment2)
                    // 加入返回栈
                    .addToBackStack(null)
                    // 提交事务
                    .commitAllowingStateLoss();
        }

    }
}
