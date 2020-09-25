package com.example.library.build;

// 构建者模式
public class Retrofit {
    private  String baseUrl;
    private int callFactory;

    private Retrofit(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.callFactory = builder.callFactory;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getCallFactory() {
        return callFactory;
    }

    public static  class Builder{

        private  String baseUrl;
        private int callFactory;

        public Builder baseUrl(String baseUrl) {
            //为空判断
            if(baseUrl == null){
                throw new NullPointerException("baseUrl == null");
            }
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder callFactory(int callFactory) {
            if(callFactory == -1){
                throw new ExceptionInInitializerError("zero");
            }
            this.callFactory = callFactory;
            return this;
        }

        public Retrofit builder(){
            //为空判断
            if(baseUrl == null){
                throw new NullPointerException("baseUrl == null");
            }
            // 初始化 赋值  默认值
            if(callFactory == -1){
                this.callFactory = 0;
            }
            return new Retrofit(this);
        }
    }
}
