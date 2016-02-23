package itlwy.com.o2omall.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Administrator on 2015/12/22.
 * Data->数据源类型
 * K->输出结果类型
 */
public abstract class BaseHolder<Data,K> {
    private Context context;
    private View contentView;
    private Data data;
    public BaseHolder(Context ctx){
        this.context = ctx;
        contentView=initView();
        contentView.setTag(this);
    }

    public Context getContext() {
        return context;
    }

    /** 创建界面*/
    public  abstract View initView();
    public View getContentView() {
        return contentView;
    }
    public void setData(Data data){
        this.data=data;
        refreshView(data);
    }
    /** 根据数据刷新界面*/
    public abstract void refreshView(Data data);

    /**
     * 输出结果
     */
    protected K getResult(){
        return null;
    }
}
