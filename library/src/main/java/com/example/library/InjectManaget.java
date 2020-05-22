package com.example.library;

import android.app.Activity;
import android.util.Log;

import com.example.library.annotations.BindView;
import com.example.library.annotations.ContentView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.content.ContentValues.TAG;

// 注解管理类
public class InjectManaget {

    public static void inject(Activity activity) {
        // 布局的注入
        injectLayout(activity);

        // 控件的注入
        injectWidget(activity);

        // TODO 点击事件的注入
    }

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
                    field.set(activity,view);

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
