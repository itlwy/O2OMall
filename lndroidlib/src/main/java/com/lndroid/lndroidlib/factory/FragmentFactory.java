package com.lndroid.lndroidlib.factory;

import com.lndroid.lndroidlib.base.BaseMVPFragment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2015/12/22.
 */
public class FragmentFactory {
    private static Map<String, BaseMVPFragment> mFragments = new HashMap<String, BaseMVPFragment>();

    public static BaseMVPFragment createFragment(String fragmentRefPath, boolean isCache)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        BaseMVPFragment fragment = null;
        if (isCache) {
            fragment = mFragments.get(fragmentRefPath);  //在集合中取出来Fragment
        }
        if (fragment == null) {  //如果再集合中没有取出来 需要重新创建
            Class clazz = Class.forName(fragmentRefPath);
            Constructor constructor = clazz.getConstructor();
            fragment = (BaseMVPFragment) constructor.newInstance();
            if (fragment != null && isCache) {
                mFragments.put(fragmentRefPath, fragment);// 把创建好的Fragment存放到集合中缓存起来
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
