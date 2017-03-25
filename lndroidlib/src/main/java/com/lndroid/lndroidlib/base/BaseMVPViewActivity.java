package com.lndroid.lndroidlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.lndroid.lndroidlib.R;
import com.lndroid.lndroidlib.utils.ViewUtils;
import com.lndroid.lndroidlib.view.LoadingPage;


/**
 * Created by mac on 16/10/3.
 */

public abstract class BaseMVPViewActivity extends AppCompatActivity {

//    TextView commTitleTv;
    Toolbar toolbar;
    FrameLayout baseContent;
    private ActionBar actionBar;
    private LoadingPage loadingPage;
    private View successView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        baseContent = (FrameLayout) findViewById(R.id.base_content);
//        commTitleTv = (TextView) findViewById(R.id.comm_title_tv);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
//        toolbar.setTitleTextColor(Color.WHITE);
        //设置当前的控件可用
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);
        addContentView();
        initContent(savedInstanceState);
    }

    private void addContentView() {
        if (loadingPage == null) {
            loadingPage = new LoadingPage(this);
            if (successView == null) {
                successView = createSuccessView();
                loadingPage.setSuccessView(successView);
                loadingPage.addView(successView, new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            }
            loadingPage.setReLoadListener(getReloadListener());
        } else {
            ViewUtils.removeParent(loadingPage);// 移除frameLayout之前的爹
        }
        baseContent.addView(loadingPage, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    protected abstract LoadingPage.ReLoadListener getReloadListener();

    protected abstract View createSuccessView();


    public void showToast(String messaga) {
        Toast.makeText(this, messaga, Toast.LENGTH_LONG).show();
    }

    public void setTitle(String title) {
        actionBar.setTitle(title);
//        commTitleTv.setText(title);
    }

//    public void showLoadingView() {
//        loadingPage.setState(LoadingPage.STATE_LOADING);
//        loadingPage.showPage();
//    }

    public void showEmptyView() {
        loadingPage.setState(LoadingPage.STATE_EMPTY);
        loadingPage.showPage();
    }

    public void showErrorView(String error) {
        loadingPage.setState(LoadingPage.STATE_ERROR);
        loadingPage.setErrorViewTips(error);
        loadingPage.showPage();
    }

    public void showSuccessView() {
        loadingPage.setState(LoadingPage.STATE_SUCCESS);
        loadingPage.showPage();
    }

    public void killMyself() {
        onBackPressed();
    }

    protected abstract void initContent(Bundle savedInstanceState);
}
