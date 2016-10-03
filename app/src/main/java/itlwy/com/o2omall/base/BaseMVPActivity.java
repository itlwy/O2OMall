package itlwy.com.o2omall.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;

/**
 * Created by mac on 16/10/2.
 */

public abstract class BaseMVPActivity extends AppCompatActivity {


    @Bind(R.id.comm_title_tv)
    TextView commTitleTv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.base_content)
    FrameLayout baseContent;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        toolbar.setTitleTextColor(Color.WHITE);
        //设置当前的控件可用
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        //设置actionbar的图片
//        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        init(savedInstanceState);
    }


    public int getFragmentContain() {
        return R.id.base_content;
    }

    public void setTitle(String title) {
//        actionBar.setTitle(title);
        commTitleTv.setText(title);
    }


    protected abstract void init(Bundle savedInstanceState);
}
