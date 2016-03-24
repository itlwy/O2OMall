package itlwy.com.o2omall.Activity;


import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseActivity;
import itlwy.com.o2omall.utils.ViewUtils;

/**
 * Created by Administrator on 2016/2/18.
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.login_et_account)
    EditText loginEtAccount;
    @Bind(R.id.login_et_password)
    EditText loginEtPassword;
    @Bind(R.id.login_btn_login)
    Button loginBtnLogin;
    @Bind(R.id.login_btn_register)
    Button loginBtnRegister;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public void initActionBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);
//        toolbar.setTitleTextColor(R.color.white);
        //设置actionbar的标题
//        actionBar.setTitle("Frame框架Frame框架Frame框架");
        //设置当前的控件可用
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //设置actionbar的图片
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_search:
//                UIManager.getInstance().changeFragment(this, FragmentFactory.createFragment
//                        (this,"APPFRAGMENT"),true,null);
                break;
            case android.R.id.home:
                Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.login_btn_login)
    public void login(View v) {
        String userName = loginEtAccount.getText().toString();
        String userPassword = loginEtPassword.getText().toString();
    }

    @OnClick(R.id.login_btn_register)
    public void register(View v) {
        ViewUtils.showSnack(v, "注册");
    }

}
