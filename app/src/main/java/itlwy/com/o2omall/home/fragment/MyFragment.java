package itlwy.com.o2omall.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.login.LoginActivity;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.home.contract.MyContract;
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
//            getActivity().startActivity(intent);
            UIManager.getInstance().changeActivity(getActivity(), LoginActivity.class, null);
        }
    }
}
