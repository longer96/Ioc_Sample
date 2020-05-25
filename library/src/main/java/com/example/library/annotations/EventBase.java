package com.example.library.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)// 该注解作用在另一个注解上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {
    //3个相同点
    // 1：setxxxxLister();
    String listenerSetter();

    // 2:监听的对象 new View.OnClickListener
    Class<?> listenerType();

    // 3：回调方法   public void onClick(View v)
    String callBackListener();
}
