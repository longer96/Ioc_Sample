package com.example.library;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.example.library.annotations.BindView;
import com.example.library.annotations.ContentView;
import com.example.library.annotations.EventBase;
import com.example.library.annotations.OnClick;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static android.content.ContentValues.TAG;

// 注解管理类
public class InjectManaget {

    public static void inject(Activity activity) {
        // 布局的注入
        injectLayout(activity);

        // 控件的注入
        injectWidget(activity);

        // 点击事件的注入
        injectEvents(activity);
    }

    // 点击事件的注入
    private static void injectEvents(Activity activity) {
        //获取类 -》 的所有方法
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        // 通过遍历 筛选带注解的方法
        for (Method method : methods) {
            //获取方法上的注解  方法上可以有很多注解
            Annotation[] annotations = method.getAnnotations();
            //遍历每个方法的 每个注解
            for (Annotation annotation : annotations) {
                // 获取 onclick 的注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //获取 注解的注解
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);

                    if (eventBase != null) {
                        // setOnClickListener
                        String listenerSetter = eventBase.listenerSetter();
                        // View.OnClickListener.class
                        Class<?> listenerType = eventBase.listenerType();
                        // onClick
                        String callBackListener = eventBase.callBackListener();

                        //得到注解的方法
                        try {
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            // 执行value 方法，获取onclick注解的值
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethod(callBackListener, method);
                            // 代理的方式 ，外面是什么注解，就匹配对应的监听和回调方法
                            // Aop 动态代理 拦截本应该执行的onClick方法 去执行开发者定义的方法
                            // 我们将invoke拦截
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);

                            // btn 可能没有复制，我们先给他复制
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
                                // 找到 setOnClickListener 方法
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                setter.invoke(view, listener);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            // 获取到 Onclick 注解的值 就是控件id  R.id.tv  注意是数组
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] value = onClick.value();
            }
        }

    }

    // 控件的注入
    private static void injectWidget(Activity activity) {
        //获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类里面的成员变量  用的是 getDeclareFields()方法 ，获取当前类的所有方法 包括private
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 获取带注解（widgetView）的成员变量
            BindView annotation = field.getAnnotation(BindView.class);
            // 不为空
            if (annotation != null) {
                Log.d(TAG, "injectWidget: 不为空！");
                int viewId = annotation.value();
                // 通过反射  找到 findviewbyid 方法
                try {
                    // 调用 findviewbyid 方法
                    Method method = clazz.getMethod("findViewById", int.class);
                    Object view = method.invoke(activity, viewId);

                    field.setAccessible(true);//将私有属性设置可以访问
                    field.set(activity, view);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static void injectLayout(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();
        // 获取类之上的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 获取 注解的值
            int layoutId = contentView.value();

            try {
                // 通过反射获取 setContentView()  方法  ， 也可以直接通过activity 调用
                // 注意是 getMethod() 方法 因为是父类的方法，  getDecloMethod()方法拿不到
                Method method = clazz.getMethod("setContentView", int.class);
                // 执行方法
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

}
