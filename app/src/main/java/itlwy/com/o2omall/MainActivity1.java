package itlwy.com.o2omall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity1 extends AppCompatActivity {

    @Bind(R.id.activity_main1)
    RelativeLayout mActivityMain1;
    @Bind(R.id.main_edit)
    EditText mMainEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test)
    public void onClick() {
        Toast.makeText(this,"hehe",Toast.LENGTH_LONG).show();
    }
}
