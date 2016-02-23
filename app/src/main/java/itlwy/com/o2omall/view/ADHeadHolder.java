package itlwy.com.o2omall.view;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import itlwy.com.o2omall.utils.CPResourceUtil;
import itlwy.com.o2omall.utils.DensityUtil;


/**
 * Created by Administrator on 2016/1/6.
 */
public class ADHeadHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private Context context;
    private View contentView;
    private ViewPager viewPager;
    private List<ImageView> imgList;
    private LinearLayout pointGroup;
    private int previousPosition;
    private boolean isStop;

    public ADHeadHolder(View itemView, Context context) {
        super(itemView);
        contentView = itemView;
        this.context = context;
        init();
    }

    private void init() {
//        viewPager = (ViewPager) contentView.findViewById(R.id.viewPager);
//        pointGroup = (LinearLayout) contentView.findViewById(R.id.point_group);
        initView();
        setListener();
    }

    private void setListener() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                ((CheckBox)pointGroup.getChildAt(position % imgList.size())).setChecked(true);
                ((CheckBox)pointGroup.getChildAt(previousPosition)).setChecked(false);
                previousPosition = position % imgList.size();
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int type = event.getAction();
                switch (type) {
                    case MotionEvent.ACTION_DOWN: {
                        runTask.stop();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        runTask.start();
                }
                return false;
            }
        });
    }

    private void initView() {
        CheckBox pointView;
        LinearLayout.LayoutParams params;
        imgList = new ArrayList<ImageView>();// 将要分页显示的View装入数组中
        for (int i = 1; i < 5; i++) {
            String imgName = String.format("a%s", i);
            ImageView imgWidget = new ImageView(context);
            ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.MATCH_PARENT);
            imgWidget.setLayoutParams(params1);
            imgWidget.setBackgroundResource(CPResourceUtil.getDrawableId(context, imgName));
            imgList.add(imgWidget);
            //加点
            pointView = new CheckBox(context);
            int size = DensityUtil.dip2px(context, 5);
            params = new LinearLayout.LayoutParams(size, size);
            params.leftMargin = DensityUtil.dip2px(context, 6);
            pointView.setLayoutParams(params);
//            pointView.setEnabled(false);
//            pointView.setBackgroundResource(R.drawable.selector_vpad_spot);
//            pointView.setTag(i);
//            pointView.setOnClickListener(this);
            pointGroup.addView(pointView);
        }
        viewPager.setAdapter(new MyPagerAdapter());
        int item = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % imgList.size());
        viewPager.setCurrentItem(item);
        ((CheckBox)pointGroup.getChildAt(previousPosition)).setChecked(true);
    }

    private AuToRunTask runTask;


    public class AuToRunTask implements Runnable{

        @Override
        public void run() {
            if(isStop){
                handler.removeCallbacks(this);
                int currentItem = viewPager.getCurrentItem();
                currentItem++;
                viewPager.setCurrentItem(currentItem,true);
                //  延迟执行当前的任务
                handler.postDelayed(this,2000);// 递归调用
            }
        }
        public void start(){
            if(!isStop){
                handler.removeCallbacks(this);
                isStop=true;
                handler.postDelayed(this,2000);// 递归调用
            }
        }
        public  void stop(){
            if(isStop){
                isStop=false;
                handler.removeCallbacks(this);
            }
        }

    }
    private Handler handler = new Handler();

    public void startRun() {
        if (runTask == null)
            runTask = new AuToRunTask();
        runTask.start();
    }

    public void stopRun(){
        if (runTask != null)
            runTask.stop();
    }

    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if ((ViewGroup) imgList.get(position % imgList.size()).getParent() != null) {
                container.removeAllViews();
            }
            container.addView(imgList.get(position % imgList.size()));
            return imgList.get(position % imgList.size());
        }
    }

    public View getContentView() {
        return contentView;
    }

    @Override
    public void onClick(View v) {
//        viewPager.setCurrentItem(viewPager.getCurrentItem()+2,false);
//        int i = (int) v.getTag();
//        int goPostion = i -1;
//        System.out.println("响应了 :"+goPostion);
//        System.out.println("当前:"+viewPager.getCurrentItem());
//        int start = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % imgList.size());
////        final int result = item+goPostion;
////                viewPager.setCurrentItem(result);
//        int curItem = viewPager.getCurrentItem()%imgList.size();
//        int realCurItem = viewPager.getCurrentItem();
//        if (curItem == goPostion){
//            return;
//        }else{
//            int result = goPostion - curItem;
//            if (result > 0 )
//                realCurItem += result;
//            else
//                realCurItem -=result;
//            System.out.println("最终:"+realCurItem);
//            viewPager.setCurrentItem(realCurItem);
//        }
    }
}
