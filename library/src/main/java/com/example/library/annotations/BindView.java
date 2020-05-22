package com.example.library.annotations;

        import java.lang.annotation.ElementType;
        import java.lang.annotation.Retention;
        import java.lang.annotation.RetentionPolicy;
        import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 作用在属性上
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    int value();// 用于获取属性值  R.id.btn
}
