package itlwy.com.o2omall.register;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseMVPViewActivity;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.view.LoadingPage;

public class RegisterActivity extends BaseMVPViewActivity implements RegisterContract.IRegisterView {


    @Bind(R.id.username_edit)
    EditText mUsernameEdit;
    @Bind(R.id.password_edit)
    EditText mPasswordEdit;
    @Bind(R.id.nickname_edit)
    EditText mNicknameEdit;
    @Bind(R.id.mail_edit)
    EditText mMailEdit;
    @Bind(R.id.phone_edit)
    EditText mPhoneEdit;
    private RegisterContract.IRegisterPresenter presenter;

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return new LoadingPage.ReLoadListener() {
            @Override
            public void reLoad() {
                presenter.subscribe(null);
            }
        };
    }

    @Override
    protected View createSuccessView() {
        View view = View.inflate(this, R.layout.activity_register, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initContent(Bundle savedInstanceState) {
        UserRepository userRepository = new UserRepository();
        // Create the presenter
        this.presenter = RegisterPresenter.newInstance(this, userRepository);

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
    public void setPresenter(RegisterContract.IRegisterPresenter presenter) {
        this.presenter = presenter;
    }


    @OnClick(R.id.register_btn)
    public void onClick() {
        String userName = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();
        String nickName = mNicknameEdit.getText().toString();
        String mail = mMailEdit.getText().toString();
        String phone = mPhoneEdit.getText().toString();
        JSONObject submitJson = new JSONObject();
        try {
            submitJson.put("userName", userName);
            submitJson.put("password", password);
            submitJson.put("nickName", nickName);
            submitJson.put("mail", mail);
            submitJson.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
            showToast(e.toString());
            return;
        }
        presenter.register(submitJson.toString());
    }
}