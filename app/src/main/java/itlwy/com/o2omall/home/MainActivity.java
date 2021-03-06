package itlwy.com.o2omall.home;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.data.product.ProductRepository;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.home.fragment.CategoryFragment;
import itlwy.com.o2omall.home.fragment.HomeFragment;
import itlwy.com.o2omall.home.fragment.MyFragment;
import itlwy.com.o2omall.home.fragment.ShopCarFragment;
import itlwy.com.o2omall.home.presenter.CategoryPresenter;
import itlwy.com.o2omall.home.presenter.HomePresenter;
import itlwy.com.o2omall.home.presenter.MyPresenter;
import itlwy.com.o2omall.home.presenter.ShopCarPresenter;

import static com.lndroid.lndroidlib.factory.FragmentFactory.createFragment;

/**
 * Created by Administrator on 2015/12/22.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.main_btn_home)
    ImageButton mainBtnHome;
    @Bind(R.id.main_btn_category)
    ImageButton mainBtnCategory;
    @Bind(R.id.main_btn_scan)
    ImageButton mainBtnScan;
    @Bind(R.id.main_btn_cart)
    ImageButton mainBtnCart;
    @Bind(R.id.main_btn_my)
    ImageButton mainBtnMy;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.home_viewPager)
    ViewPager homeViewPager;
    private String toolBarType = ConstantValue.HOMEFRAGMENT; //标题栏类型
    private List<BaseMVPFragment> fragmentList;
    private List<ImageButton> btns;
    private int currentFragment; //当前fragment


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("当前屏幕为横屏");
        } else {
            System.out.println("当前屏幕为横屏");
        }
        Intent intent = new Intent();
        intent.setClassName("bpowe", "adsa");

    }


    public void init() {
        try {
            ProductRepository productRepository = new ProductRepository();
            fragmentList = new ArrayList<>();
            HomeFragment homeFragment = (HomeFragment) createFragment(ConstantValue.HOMEFRAGMENT, true);
            HomePresenter.newInstance(homeFragment, productRepository);
            fragmentList.add(homeFragment);
            CategoryFragment categoryFragment = (CategoryFragment) createFragment(ConstantValue.CATEGORYFRAGMENT, true);
            CategoryPresenter.newInstance(categoryFragment, productRepository);
            fragmentList.add(categoryFragment);
            ShopCarFragment shopCarFragment = (ShopCarFragment) FragmentFactory.createFragment(ConstantValue.SHOPCARFRAGMENT, true);
            ShopCarPresenter.newInstance(shopCarFragment);
            fragmentList.add(shopCarFragment);
            MyFragment myFragment = (MyFragment) createFragment(ConstantValue.MYFRAGMENT, true);
            MyPresenter.newInstance(myFragment, new UserRepository());
            fragmentList.add(myFragment);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 改变标题栏
     *
     * @param type
     */
    public void changeToolBar(String type) {
        toolBarType = type;
//        invalidateOptionsMenu();
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
//                UIManager.getInstance().changeFragment( FragmentFactory.createFragment
//                        (this,"APPFRAGMENT"),true,null);
                break;
            case android.R.id.home:
                Toast.makeText(this, "menu", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置标题
     *
     * @param value
     */
    public void setTitle(String value) {
        title.setText(value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        String point = null;
//        point.contains("");
//        actionBar.setDisplayShowTitleEnabled(false);
//        toolbar.setTitleTextColor(R.color.white);
        //设置actionbar的标题
//        actionBar.setTitle("Frame框架Frame框架Frame框架");
        //设置当前的控件可用
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        //设置actionbar的图片
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
//        toolbar.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void initView() {

        if (btns == null) {
            btns = new ArrayList<ImageButton>();
            mainBtnCart.setOnClickListener(this);
            mainBtnCategory.setOnClickListener(this);
            mainBtnHome.setOnClickListener(this);
            mainBtnMy.setOnClickListener(this);
            mainBtnScan.setOnClickListener(this);
            btns.add(mainBtnHome);
            btns.add(mainBtnCategory);
            btns.add(mainBtnCart);
            btns.add(mainBtnMy);
        }
        ((TransitionDrawable) btns.get(0).getDrawable())
                .startTransition(200);
        homeViewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager()));
        homeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (currentFragment == position)
                    return;
                if (0 == position) {
                    toolbar.setVisibility(View.GONE);
                } else if (1 == position) {
                    toolbar.setVisibility(View.VISIBLE);
                    title.setText("商品分类");
                } else if (2 == position) {
                    toolbar.setVisibility(View.VISIBLE);
                    title.setText("购物车");
                } else if (3 == position) {
                    toolbar.setVisibility(View.VISIBLE);
                    title.setText("我的");
                }
                ((TransitionDrawable) btns.get(currentFragment).getDrawable())
                        .reverseTransition(200);
                currentFragment = position;
                ((TransitionDrawable) btns.get(position).getDrawable())
                        .startTransition(200);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_home:
                // 切换首页选项卡
//                UIManager.getInstance().changeFragment(FragmentFactory.createFragment
//                        (this, ConstantValue.HOMEFRAGMENT), true, null);
                homeViewPager.setCurrentItem(0);
                break;
            case R.id.main_btn_category:
                // 切换商品分类选项卡
//                UIManager.getInstance().changeFragment( FragmentFactory.createFragment
//                        (this, ConstantValue.CATEGORYFRAGMENT), true, null);
                homeViewPager.setCurrentItem(1);
                break;
            case R.id.main_btn_scan:
                // 切换扫描选项卡
                ViewUtils.showSnack(v, "扫描");
                break;
            case R.id.main_btn_cart:
                // 切换购物车选项卡
//                UIManager.getInstance().changeFragment( FragmentFactory.createFragment
//                        (this, ConstantValue.SHOPCARFRAGMENT), true, null);
                homeViewPager.setCurrentItem(2);
                break;
            case R.id.main_btn_my:
                // 切换我的信息选项卡
//                UIManager.getInstance().changeFragment( FragmentFactory.createFragment
//                        (this, ConstantValue.MYFRAGMENT), true, null);
                homeViewPager.setCurrentItem(3);
                break;
        }
    }


    private class HomeViewPagerAdapter extends FragmentPagerAdapter {
        public HomeViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
