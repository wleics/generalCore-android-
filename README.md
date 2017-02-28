# generalCore-android 安卓应用核心类库封装
##支持compile操作
具体步骤

1. 在应用的主bulid.gradle（Project）中加入**maven {url "https://jitpack.io"}**

```
allprojects {
    repositories {
        jcenter()
        maven {url "https://jitpack.io"}
    }
}
```

2. 在需要引入类库的app中加入如下代码:**compile 'com.github.wleics:generalCore-android-:v1.0'**

```
dependencies {
    .....
    compile 'com.github.wleics:generalCore-android-:v1.0'
}
```

##1.0版本提供的内容有
1. 网络图片加载(ImageLoader)
2. 网络gif图片加载(GifLoader)
3. 统一的http请求封装(HttpOperateHelper)
4. 提供了自定义导航卡（navbar）
5. 提供了封装有导航栏并支持侧滑关闭的基础Activity框架(BaseNavActivity)
6. 常用帮助类库（SystemTools）
