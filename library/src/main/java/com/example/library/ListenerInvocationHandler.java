package com.example.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ListenerInvocationHandler implements InvocationHandler {

    // 拦截的对象 MainActivity中 本应该执行的OnClick方法
    private Object taget;
    // 键值对 key：onclick , value :开发者自定义的方法show(View view)
    private HashMap<String, Method> methodHashMap = new HashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (taget != null) {
            String name = method.getName();
            // 如果集合中有需要拦截的方法名
            method = methodHashMap.get(name);
            if (method != null) {
                return method.invoke(taget, args);
            }

        }
        return null;
    }

    public ListenerInvocationHandler(Object taget) {
        this.taget = taget;
    }

    /**
     * 拦截的方法名  和需要替换成的方法
     *
     * @param methodName 拦截本应该执行的onclick方法
     * @param method     之定义的方法  show(View view)
     */
    public void addMethod(String methodName, Method method) {
        methodHashMap.put(methodName, method);
    }
}
