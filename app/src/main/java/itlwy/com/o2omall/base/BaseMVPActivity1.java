package itlwy.com.o2omall.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.utils.ViewUtils;
import itlwy.com.o2omall.view.LoadingPage;

/**
 * Created by mac on 16/10/3.
 */

public abstract class BaseMVPActivity1 extends AppCompatActivity {

    @Bind(R.id.comm_title_tv)
    TextView commTitleTv;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.base_content)
    FrameLayout baseContent;
    private ActionBar actionBar;
    private LoadingPage loadingPage;
    private View successView;

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


    public void setTitle(String title) {
//        actionBar.setTitle(title);
        commTitleTv.setText(title);
    }

    public void showLoadingView() {
        loadingPage.setState(LoadingPage.STATE_LOADING);
        loadingPage.showPage();
    }

    public void showEmptyView() {
        loadingPage.setState(LoadingPage.STATE_EMPTY);
        loadingPage.showPage();
    }

    public void showErrorView() {
        loadingPage.setState(LoadingPage.STATE_ERROR);
        loadingPage.showPage();
    }

    public void showSuccessView() {
        loadingPage.setState(LoadingPage.STATE_SUCCESS);
        loadingPage.showPage();
    }

    protected abstract void initContent(Bundle savedInstanceState);
}
