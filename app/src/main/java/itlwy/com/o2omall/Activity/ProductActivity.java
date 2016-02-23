package itlwy.com.o2omall.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseActivity;
import itlwy.com.o2omall.bean.CategoryTwo;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.utils.UIManager;

/**
 * Created by Administrator on 2016/2/23.
 */
public class ProductActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.product_contain)
    FrameLayout productContain;

    @Override
    public void init() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);
        CategoryTwo categoryTwo = getIntent().getParcelableExtra(CategoryTwo.Tag);
        Bundle bundle = new Bundle();
        bundle.putParcelable(CategoryTwo.Tag, categoryTwo);
        UIManager.getInstance().changeFragment(this, R.id.product_contain, FragmentFactory.
                createFragment(this, ConstantValue.PRODUCTLISTFRAGMENT), true, bundle);
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

}
