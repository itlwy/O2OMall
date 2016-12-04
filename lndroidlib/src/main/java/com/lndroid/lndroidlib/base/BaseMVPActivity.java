package com.lndroid.lndroidlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.lndroid.lndroidlib.R;
import com.lndroid.lndroidlib.utils.UIManager;


/**
 * Created by mac on 16/10/2.
 */

public abstract class BaseMVPActivity extends AppCompatActivity {


    //    @Bind(R.id.comm_title_tv)
//    TextView commTitleTv;
    Toolbar toolbar;
    FrameLayout baseContent;
    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        baseContent = (FrameLayout) findViewById(R.id.base_content);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        toolbar.setTitleTextColor(Color.WHITE);
        //设置当前的控件可用
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //设置actionbar的图片
//        actionBar.setHomeAsUpIndicator(R.drawable.selector_arrow_up);
        init(savedInstanceState);
    }


    public int getFragmentContain() {
        return R.id.base_content;
    }

    public void changeFragment(BaseMVPFragment fragment, boolean isAddBackStack) {
        UIManager.getInstance().changeFragment(this, getFragmentContain(), fragment, isAddBackStack, null);
    }

    public void setTitle(String title) {
        actionBar.setTitle(title);
//        commTitleTv.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract void init(Bundle savedInstanceState);
}
