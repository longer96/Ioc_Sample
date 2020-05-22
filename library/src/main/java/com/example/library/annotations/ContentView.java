package com.example.library.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 该注解只能作用在什么上面  MEthOD  类之上   TYPE：类之上
@Retention(RetentionPolicy.RUNTIME) // jvm 在运行时通过反色的技术  获取值
public @interface ContentView {
    // 返回运行时 注解的值
    int value();
}
