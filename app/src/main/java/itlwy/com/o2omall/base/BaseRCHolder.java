package itlwy.com.o2omall.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2016/2/19.
 */
public abstract class BaseRCHolder<T> extends RecyclerView.ViewHolder {

    private View contentView;

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public BaseRCHolder(View itemView) {
        super(itemView);
        contentView = itemView;
    }

    public abstract void bindDatas(T data);
}
