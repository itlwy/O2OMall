package itlwy.com.o2omall.factory;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.utils.CPResourceUtil;

/**
 * Created by Administrator on 2015/12/22.
 */
public class FragmentFactory {
    private static Map<String, BaseMVPFragment> mFragments = new HashMap<String, BaseMVPFragment>();

    public static BaseMVPFragment createFragment(Context ctx, String function) {
        BaseMVPFragment fragment = null;
        fragment = mFragments.get(function);  //在集合中取出来Fragment
        if (fragment == null) {  //如果再集合中没有取出来 需要重新创建
            int strId = CPResourceUtil.getStringId(ctx, function);
            if (strId > 0) {
                String function_action = ctx.getResources().getString(strId);
                try {
                    Class clazz = Class.forName(function_action);
                    Constructor constructor = clazz.getConstructor();
                    fragment = (BaseMVPFragment) constructor.newInstance();
                    if (fragment != null) {
                        mFragments.put(function, fragment);// 把创建好的Fragment存放到集合中缓存起来
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return fragment;
    }

    public static void remove(String flag) {
        mFragments.remove(flag);
    }

    public static BaseMVPFragment get(String flag) {
        return mFragments.get(flag);
    }
}
