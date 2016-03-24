package itlwy.com.o2omall.Activity;


import android.content.res.Configuration;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.PatchManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseActivity;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BaseFragment;
import itlwy.com.o2omall.bean.AppPatch;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.utils.ThreadManager;
import itlwy.com.o2omall.utils.ViewUtils;
import support.utils.FileUtils;
import support.utils.HttpClientUtils;
import support.utils.StringTools;

/**
 * Created by Administrator on 2015/12/22.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

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
    @Bind(R.id.bottomBarllt)
    LinearLayout bottomBarllt;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.home_viewPager)
    ViewPager homeViewPager;
    private String toolBarType = ConstantValue.HOMEFRAGMENT; //标题栏类型
    private List<BaseFragment> fragmentList;
    private List<ImageButton> btns;
    private int currentFragment; //当前fragment
    private HttpClientUtils httpUtils;
    public static String categoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();

    @Override
    public void initActionBar() {
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
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            System.out.println("当前屏幕为横屏");
        }else{
            System.out.println("当前屏幕为横屏");
        }
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
                if (0 == position){
                    toolbar.setVisibility(View.GONE);
                }else if (1 == position){
                    toolbar.setVisibility(View.VISIBLE);
                    title.setText("商品分类");
                }else if (2 == position){
                    toolbar.setVisibility(View.VISIBLE);
                    title.setText("购物车");
                }else if (3 == position){
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
    public void init() {
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(FragmentFactory.createFragment(this, ConstantValue.HOMEFRAGMENT));
        fragmentList.add(FragmentFactory.createFragment(this, ConstantValue.CATEGORYFRAGMENT));
        fragmentList.add(FragmentFactory.createFragment(this, ConstantValue.SHOPCARFRAGMENT));
        fragmentList.add(FragmentFactory.createFragment(this, ConstantValue.MYFRAGMENT));

        checkPatch();
    }

    /**
     * check the patchs from server
     */
    private void checkPatch() {
        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
            @Override
            public void run() {
                if (httpUtils == null)
                    httpUtils = new HttpClientUtils();
                String s = httpUtils.sendGet("http://192.168.1.196:8080/patch/patch.txt","utf-8");
                ArrayList<String> list = new ArrayList<String>();
                try {
                    Gson gson = new Gson();
                    AppPatch appPatch = gson.fromJson(s, AppPatch.class);
                    //取之前存的
                    File cacheFile = MainActivity.this.getFileStreamPath("appPatch.json");
                    String cacheStr = null;
                    if (cacheFile.exists())
                        cacheStr = FileUtils.file2string(cacheFile.getAbsolutePath());
                    AppPatch appCache = null;
                    if (!TextUtils.isEmpty(cacheStr))
                        appCache = gson.fromJson(cacheStr, AppPatch.class);

                    List<AppPatch.Patch> patchs = appPatch.getPatch();
                    for (int i = 0; i < patchs.size(); i++) {
                        AppPatch.Patch patch = patchs.get(i);
                        String fileName = patch.getName();
                        if (appCache != null && appCache.isContain(patch)){
                            continue;
                        }
                        String fileUri = patch.getUri();
                        String filePath = categoryPath+"/O2OMall/"+fileName;
                        httpUtils.downloadFile(fileUri,filePath);
                        list.add(filePath);
                    }
                    PatchManager manager = ((BaseApplication) getApplication()).getPatchManager();
                    for (String item:list){
                        try {
                            manager.addPatch(item);
                            FileUtils.deleteFile(item);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (list != null&& list.size() > 0)
                        FileUtils.write2app_file(MainActivity.this,"appPatch.json", StringTools.String2InputStream(s));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
//                UIManager.getInstance().changeFragment(this, FragmentFactory.createFragment
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
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_home:
                // 切换首页选项卡
//                UIManager.getInstance().changeFragment(this, FragmentFactory.createFragment
//                        (this, ConstantValue.HOMEFRAGMENT), true, null);
                homeViewPager.setCurrentItem(0);
                break;
            case R.id.main_btn_category:
                // 切换商品分类选项卡
//                UIManager.getInstance().changeFragment(this, FragmentFactory.createFragment
//                        (this, ConstantValue.CATEGORYFRAGMENT), true, null);
                homeViewPager.setCurrentItem(1);
                break;
            case R.id.main_btn_scan:
                // 切换扫描选项卡
                ViewUtils.showSnack(v, "扫描");
                break;
            case R.id.main_btn_cart:
                // 切换购物车选项卡
//                UIManager.getInstance().changeFragment(this, FragmentFactory.createFragment
//                        (this, ConstantValue.SHOPCARFRAGMENT), true, null);
                homeViewPager.setCurrentItem(2);
                break;
            case R.id.main_btn_my:
                // 切换我的信息选项卡
//                UIManager.getInstance().changeFragment(this, FragmentFactory.createFragment
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
