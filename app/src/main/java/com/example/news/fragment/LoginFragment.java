package com.example.news.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.news.R;
import com.example.news.model.UserVo;
import com.example.news.utils.AESUtil;
import com.example.news.utils.GsonUtil;
import com.example.news.utils.PrefUtilS;
import com.example.news.utils.ThemeUtil;
import com.example.news.utils.XUtilsDate;
import com.example.news.utils.XmlParserUtils;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;


public class LoginFragment extends BaseFragment implements View.OnClickListener,XUtilsDate.LisData {
    private View view;
    private Button myButton;

    private Button check_user;
    private EditText login_mobile;
    private EditText login_password;
    private String url = "http://172.16.2.94:8080/wcmInf/";
    private TextView user_forget;
    private LinearLayout fm_regist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ThemeUtil.setBaseTheme(getContext());

        view = inflater.inflate(R.layout.activity_login, container, false);
        initView();
        return view;
    }

    @Override
    public View initView() {




        user_forget = view.findViewById(R.id.user_forget);
        login_mobile = view.findViewById(R.id.login_mobile);
        login_password = view.findViewById(R.id.login_password);
        myButton = view.findViewById(R.id.btn_register);
        check_user = view.findViewById(R.id.check_user);
        myButton.setOnClickListener(this);
        check_user.setOnClickListener(this);
        user_forget.setOnClickListener(this);
        fm_regist = view.findViewById(R.id.fm_regist);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.check_user:
                String mobile = login_mobile.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                XUtilsDate xUtils = new XUtilsDate();
                String IMEI= XmlParserUtils.getMacFromHardware();
                UserVo userVo = new UserVo();
                userVo.setIMEI(IMEI);
                userVo.setMoblie(mobile);
                userVo.setPassword(AESUtil.encrypt(password));
                if (mobile .equals("")) {
                    Toast.makeText(mActivity, "请填写手机号", Toast.LENGTH_SHORT).show();
                }if (password .equals("")) {
                Toast.makeText(mActivity, "请填写密码", Toast.LENGTH_SHORT).show();
            }
                if (!mobile.equals("")&&!password.equals("")) {
                    login(userVo);
                }

                break;
            case R.id.btn_register:
                RegistFragment registFragment = new RegistFragment();
                showFragment(LoginFragment.this, registFragment);
                break;
            case R.id.user_forget:
                UpdatePswFragment updatePswFragment=new UpdatePswFragment();
                showFragment(LoginFragment.this, updatePswFragment);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }


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

    @Override
    public void start() {

    }


    public void login(final UserVo userVo) {

        String path = url + "loginAndroid";
        RequestParams params = new RequestParams(path);

        params.addParameter("mobile", userVo.getMoblie());
        params.addParameter("password", userVo.getPassword());

        // params.addParameter("password", "123");
        x.http().request(HttpMethod.POST, params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Map map = GsonUtil.GsonToMaps(result);
                String message = map.get("message").toString();
                String username = map.get("username").toString().trim();
                if (message.equals("登录成功")) {
                    PrefUtilS.putUserBoolean(mActivity, "is_user", true);
                    PrefUtilS.putUser(mActivity, "username", username);
                    PrefUtilS.putUser(mActivity, "mobile", userVo.getMoblie());
                    PrefUtilS.putUser(mActivity, "password", userVo.getPassword());
                    PrefUtilS.putUser(mActivity, "IMEI", userVo.getIMEI());
                    System.out.println();
                  /*  Handler handler = new Handler();
                    Message msg = Message.obtain();
                    msg.what = SUCESS;


                    //发送一条消息
                    SignInManager signInManager = new SignInManager();
                    signInManager.handler.sendMessage(msg);*/
                    MineFragment mineFragment=new MineFragment();
                    showFragment(LoginFragment.this,mineFragment );
                    Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();


                } else {

                    Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
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
     * @param root 最外层的View
     * @param scrollToView 不想被遮挡的View,会移动到这个Veiw的可见位置
     */
    private int scrollToPosition=0;
    private void autoScrollView(final View root, final View scrollToView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect rect = new Rect();

                        //获取root在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);

                        //获取root在窗体的不可视区域高度(被遮挡的高度)
                        int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;

                        //若不可视区域高度大于150，则键盘显示
                        if (rootInvisibleHeight > 150) {

                            //获取scrollToView在窗体的坐标,location[0]为x坐标，location[1]为y坐标
                            int[] location = new int[2];
                            scrollToView.getLocationInWindow(location);

                            //计算root滚动高度，使scrollToView在可见区域的底部
                            int scrollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;

                            //注意，scrollHeight是一个相对移动距离，而scrollToPosition是一个绝对移动距离
                            scrollToPosition += scrollHeight;

                        } else {
                            //键盘隐藏
                            scrollToPosition = 0;
                        }
                        root.scrollTo(0, scrollToPosition);

                    }
                });
    }

}