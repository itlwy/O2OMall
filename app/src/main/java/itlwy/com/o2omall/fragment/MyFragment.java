package itlwy.com.o2omall.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.Activity.LoginActivity;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.utils.UIManager;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MyFragment extends BaseFragment<Void, String> {
    @Override
    public String load() {
        return "";
    }

    @Override
    public View createSuccessView() {
        MyHolder myHolder = new MyHolder(getActivity());
        return myHolder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.MYFRAGMENT;
    }


    public class MyHolder extends BaseHolder<String, Void> implements View.OnClickListener {

        @Bind(R.id.my_ib_login)
        ImageButton myIbLogin;
        @Bind(R.id.my_tv_username)
        TextView myTvUsername;

        public MyHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View v = View.inflate(getContext(), R.layout.fragment_my, null);
            ButterKnife.bind(this, v);
            myIbLogin.setOnClickListener(this);
            return v;
        }

        @Override
        public void refreshView(String aVoid) {

        }

        @Override
        public void onClick(View v) {
//            ViewUtils.showSnack(v, "前往登录/注册页面");
            UIManager.getInstance().changeActivity(getActivity(), LoginActivity.class,null);
        }
    }
}
