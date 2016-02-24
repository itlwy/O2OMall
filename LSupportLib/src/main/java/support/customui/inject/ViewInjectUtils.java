package support.customui.inject;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import support.customui.inject.annotation.ContentView;
import support.customui.inject.annotation.EventBase;
import support.customui.inject.annotation.ViewInject;
import support.customui.inject.handler.DynamicHandler;
import support.utils.CPResourceUtil;

public class ViewInjectUtils {

	private static final String METHOD_SET_CONTENTVIEW = "setContentView";
	private static final String METHOD_FINDVIEWBYID = "findViewById";

	/**
	 * 注入xml布局文件  setContentView
	 * @param activity
	 * @author Lwy
	 * @date 2015-9-10 下午3:32:57
	 */
	private static void injectContentView(Activity activity){
		Class<? extends Activity> clazz = activity.getClass();
		//查询类上是否存在ContentView注解
		ContentView contentView = clazz.getAnnotation(ContentView.class);
		if(contentView != null){
			int contentViewLayoutId = contentView.value();
			try {
				Method method = clazz.getMethod(METHOD_SET_CONTENTVIEW, int.class);
				method.setAccessible(true);
				method.invoke(activity, contentViewLayoutId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	/**
	 * 注入控件 ----findViewById
	 * @param activity
	 * @author Lwy
	 * @date 2015-9-10 下午3:42:28
	 */
	private static void injectViews(Activity activity){
		Class<? extends Activity> clazz = activity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field :fields) {
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if(viewInject != null){
				int resId = viewInject.value();
				if(resId != -1){
					//初始化view
					try {
						Method method = clazz.getMethod(METHOD_FINDVIEWBYID, int.class);
						if(resId == -2){//使用无输入值情况(即没有注解R.id.XX)
							resId =CPResourceUtil.getId(activity, field.getName());
						}
						Object result = method.invoke(activity, resId);
						field.setAccessible(true);
						field.set(activity, result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
	/**
	 * 注入方法，针对Activity
	 * @param activity
	 * @author Lwy
	 * @date 2015-9-10 下午3:42:17
	 */
	public static void inject(Activity activity){
		injectContentView(activity);
		injectViews(activity);
		injectEvents(activity);  
	}
	/**
	 * 点击事件注入
	 * @param activity
	 * @author Lwy
	 * @date 2015-9-11 上午9:24:55
	 */
	private static void injectEvents(Activity activity) {
		 Class<? extends Activity> clazz = activity.getClass();  
	        Method[] methods = clazz.getMethods();  
	        //遍历所有的方法  
	        for (Method method : methods)  
	        {  
	            Annotation[] annotations = method.getAnnotations();  
	            //拿到方法上的所有的注解  
	            for (Annotation annotation : annotations)  
	            {  
	                Class<? extends Annotation> annotationType = annotation  
	                        .annotationType();  
	                //拿到注解上的注解  
	                EventBase eventBaseAnnotation = annotationType  
	                        .getAnnotation(EventBase.class);  
	                //如果设置为EventBase  
	                if (eventBaseAnnotation != null)  
	                {  
	                    //取出设置监听器的名称，监听器的类型，调用的方法名  
	                    String listenerSetter = eventBaseAnnotation   //setOnClickListener
	                            .listenerSetter();  
	                    Class<?> listenerType = eventBaseAnnotation.listenerType();   //View.OnClickListener.class
	                    String methodName = eventBaseAnnotation.methodName();    //onClick
	  
	                    try  
	                    {  
	                        //拿到Onclick注解中的value方法  
	                        Method aMethod = annotationType  
	                                .getDeclaredMethod("value");  
	                        //取出所有的viewId  
	                        int[] viewIds = (int[]) aMethod  
	                                .invoke(annotation, new Object());
	                        //通过InvocationHandler设置代理  
	                        DynamicHandler handler = new DynamicHandler(activity);  
	                        handler.addMethod(methodName, method);  
	                        Object listener = Proxy.newProxyInstance(  
	                                listenerType.getClassLoader(),  
	                                new Class<?>[] { listenerType }, handler);  
	                        //遍历所有的View，设置事件  
	                        for (int viewId : viewIds)  
	                        {  
	                            View view = activity.findViewById(viewId);  
	                            Method setEventListenerMethod = view.getClass()  
	                                    .getMethod(listenerSetter, listenerType);  
	                            setEventListenerMethod.invoke(view, listener);  
	                        }  
	  
	                    } catch (Exception e)  
	                    {  
	                        e.printStackTrace();  
	                    }  
	                }  
	  
	            }  
	        }  
	}
	
	/**
	 * 注入方法，针对fragment
	 * @author Lwy
	 * @date 2015-9-10 下午3:42:17
	 */
	public static void inject(Fragment fragment,View view){
		injectViews(fragment,view);
		injectEvents(fragment,view);  
	}
	
	private static void injectEvents(Fragment fragment, View view) {

		 Class<? extends Fragment> clazz = fragment.getClass();  
	        Method[] methods = clazz.getMethods();  
	        //遍历所有的方法  
	        for (Method method : methods)  
	        {  
	            Annotation[] annotations = method.getAnnotations();  
	            //拿到方法上的所有的注解  
	            for (Annotation annotation : annotations)  
	            {  
	                Class<? extends Annotation> annotationType = annotation  
	                        .annotationType();  
	                //拿到注解上的注解  
	                EventBase eventBaseAnnotation = annotationType  
	                        .getAnnotation(EventBase.class);  
	                //如果设置为EventBase  
	                if (eventBaseAnnotation != null)  
	                {  
	                    //取出设置监听器的名称，监听器的类型，调用的方法名  
	                    String listenerSetter = eventBaseAnnotation   //setOnClickListener
	                            .listenerSetter();  
	                    Class<?> listenerType = eventBaseAnnotation.listenerType();   //View.OnClickListener.class
	                    String methodName = eventBaseAnnotation.methodName();    //onClick
	  
	                    try  
	                    {  
	                        //拿到Onclick注解中的value方法  
	                        Method aMethod = annotationType  
	                                .getDeclaredMethod("value");  
	                        //取出所有的viewId  
	                        int[] viewIds = (int[]) aMethod  
	                                .invoke(annotation, new Object[0]);
	                        //通过InvocationHandler设置代理  
	                        DynamicHandler handler = new DynamicHandler(fragment);  
	                        handler.addMethod(methodName, method);  
	                        Object listener = Proxy.newProxyInstance(  
	                                listenerType.getClassLoader(),  
	                                new Class<?>[] { listenerType }, handler);  
	                        //遍历所有的View，设置事件  
	                        for (int viewId : viewIds)  
	                        {  
	                            View widget = view.findViewById(viewId);  
	                            Method setEventListenerMethod = widget.getClass()  
	                                    .getMethod(listenerSetter, listenerType);  
	                            setEventListenerMethod.invoke(widget, listener);  
	                        }  
	  
	                    } catch (Exception e)  
	                    {  
	                        e.printStackTrace();  
	                    }  
	                }  
	  
	            }  
	        }  
	}
	private static void injectViews(Fragment fragment, View view) {
		Class<? extends Fragment> clazz = fragment.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field :fields) {
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if(viewInject != null){
				int resId = viewInject.value();
				if(resId != -1){
					//初始化view
					try {
						if(resId == -2){//使用无输入值情况(即没有注解R.id.XX)
							resId =CPResourceUtil.getId(fragment.getActivity(), field.getName());
						}
						Object result = view.findViewById(resId);
						field.setAccessible(true);
						field.set(fragment, result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
