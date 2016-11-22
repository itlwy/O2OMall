package itlwy.com.o2omall.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.user.model.UserModel;
import itlwy.com.o2omall.home.contract.MyContract;
import itlwy.com.o2omall.login.LoginActivity;
import itlwy.com.o2omall.utils.UIManager;
import itlwy.com.o2omall.view.LoadingPage;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MyFragment extends BaseMVPFragment implements MyContract.IMyView {
    private MyHolder myHolder;
    private MyContract.IMyPresenter presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myHolder = new MyHolder(getActivity());
        return myHolder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.MYFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

    @Override
    protected void inits() {

    }

    @Override
    public void bindViewDatas(String result) {
        myHolder.setData(result);
    }


    @Override
    public void setPresenter(MyContract.IMyPresenter presenter) {
        this.presenter = presenter;
    }


    public class MyHolder extends BaseHolder<String, Void> {

        @Bind(R.id.my_ib_login)
        ImageButton myIbLogin;
        //        @Bind(R.id.my_tv_username)
//        TextView myTvUsername;
        @Bind(R.id.before_login_rlt)
        RelativeLayout mBeforeLoginRlt;
        @Bind(R.id.my_logo_ib)
        ImageView mMyLogoIb;
        @Bind(R.id.my_username_tv)
        TextView mMyUsernameTv;
        @Bind(R.id.my_nickname_tv)
        TextView mMyNicknameTv;
        @Bind(R.id.my_mail_tv)
        TextView mMyMailTv;
        @Bind(R.id.my_phone_tv)
        TextView mMyPhoneTv;
        @Bind(R.id.after_login_rlt)
        RelativeLayout mAfterLoginRlt;
        @Bind(R.id.my_vip_tv)
        TextView mMyVipTv;

        public MyHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View v = View.inflate(getContext(), R.layout.fragment_my, null);
            ButterKnife.bind(this, v);
//            myIbLogin.setOnClickListener(this);
            return v;
        }

        @Override
        public void refreshView(String aVoid) {
            UserModel userModel = ClientKernal.getInstance().getUserModel();
            if (userModel != null) {
                mAfterLoginRlt.setVisibility(View.VISIBLE);
                mBeforeLoginRlt.setVisibility(View.GONE);
                mMyUsernameTv.setText("姓名:" + userModel.getUserName());
                mMyNicknameTv.setText("昵称:" + userModel.getNickName());
                mMyMailTv.setText("email:" + userModel.getEmail());
                mMyPhoneTv.setText("电话:" + userModel.getPhone());
                mMyVipTv.setText("vip:" + userModel.getVipLevel());
                ImageLoader.getInstance().displayImage(userModel.getLogo(), mMyLogoIb,
                        ((BaseApplication) BaseApplication.getApplication()).getOptions());
            } else {
                mBeforeLoginRlt.setVisibility(View.VISIBLE);
                mAfterLoginRlt.setVisibility(View.GONE);
            }
        }

        @OnClick({R.id.my_ib_login, R.id.my_logo_ib})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.my_ib_login:
                    UIManager.getInstance().changeActivity(getActivity(), LoginActivity.class, null);
                    break;
                case R.id.my_logo_ib:

                    break;
                case R.id.my_address_tv:

                    break;
            }
        }

    }
}
