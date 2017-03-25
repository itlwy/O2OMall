package itlwy.com.o2omall.user.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.view.LoadingPage;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.register.RegisterActivity;

/**
 * Created by mac on 16/10/2.
 */

public class LoginFragment extends BaseMVPFragment implements LoginContract.ILoginView<LoginContract.ILoginPresenter> {

    private static final int REGISTER = 11;
    @Bind(R.id.login_et_account)
    EditText loginEtAccount;
    @Bind(R.id.login_et_password)
    EditText loginEtPassword;
    private LoginContract.ILoginPresenter presenter;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected String getFragmentKey() {
        return ConstantValue.LOGINFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

    @Override
    protected void inits(Bundle savedInstanceState) {

    }


    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(null);
    }


    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                String userName = loginEtAccount.getText().toString();
                String userPassword = loginEtPassword.getText().toString();
                presenter.login(userName, userPassword);
                break;
            case R.id.login_btn_register:
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent,REGISTER);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REGISTER == requestCode && resultCode == Activity.RESULT_OK) {
            getActivity().finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
