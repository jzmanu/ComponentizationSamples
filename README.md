
前面两篇文章分别介绍了 Android 组件化基础知识以及 Android 组件化过程 Application 的相关知识，在阅读本文之前可以先阅读下面两篇文章：

- [Android组件化基础](https://mp.weixin.qq.com/s?__biz=MzU3NTA3MDU1OQ==&mid=2247484171&idx=1&sn=f6cc9203c36f9edc9a19986c66451fe0&chksm=fd298b3bca5e022d5afe72a422cf1386dafe112db3873339cd1906f6e9266d30b19da1f31730&token=710521150&lang=zh_CN#rd)

- [Android组件化之Application](https://mp.weixin.qq.com/s?__biz=MzU3NTA3MDU1OQ==&mid=2247484177&idx=1&sn=36039f0a013c583098fb2434637e8318&chksm=fd298b21ca5e023720fbb4155b6fe9a2b9600cceb5ae608c8d09cef4eec932fe283d38975f47&token=710521150&lang=zh_CN#rd)


ARouter 是 albaba 团队开源的一个 Android App 组件化改造的一个框架，支持模块之间的路由、通信、拦截功能，相比原生跳转来说更能适应组件化开发，本文主要通过实例总结一下 Arouter 的常用功能，具体如下：

1. ARouter的配置
2. 应用内跳转
3. 应用内携带参数跳转
4. Activity返回结果处理
5. 通过Uri跳转及参数解析
6. Module之间的跳转
7. 服务调用
8. 显示效果

#### ARouter的配置

在对应的 build.gradle 文件中配置 ARouter 的相关依赖如下：

```java
android {
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
}

dependencies {
    //api与compiler匹配使用，使用最新版可以保证兼容
    compile 'com.alibaba:arouter-api:1.4.0'
    annotationProcessor 'com.alibaba:arouter-compiler:1.2.1'
    ...
}
```

可以选择配置路由表自动加载，在项目下面的 build.gradle 文件中进行配置，配置方式如下：


```java
// 路由表自动加载插件
apply plugin: 'com.alibaba.arouter'

buildscript {
    
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.0.1'
        //ARouter
        classpath "com.alibaba:arouter-register:1.0.2"
    }
}
```

此外，还需在 Application 中初始化 ARouter，如下：


```java
@Override
public void onCreate() {
    super.onCreate();
    // 必须在初始化ARouter之前配置
    if (BuildConfig.DEBUG){
        // 日志开启
        ARouter.openLog();
        // 调试模式开启，如果在install run模式下运行，则必须开启调试模式
        ARouter.openDebug();
    }
    ARouter.init(this);
}
```

#### 应用内跳转

使用 ARouter 进行应用内跳转非常简单，只需要在要跳转的 Activity 上添加 @Route 注解即可，具体如下：


```java
// 配置的path至少需要两级，如/xx/xxx
@Route(path = FirstActivity.PATH)
public class FirstActivity extends AppCompatActivity {

    public static final String PATH = "/test/firstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }
}
```

然后使用 ARouter 提供的跳转方式来进行应用内部之间的跳转，具体如下：


```java
// 应用内跳转
ARouter.getInstance()
        .build(FirstActivity.PATH)
        .navigation();
```

#### 应用内携带参数跳转

 ARouter 通过 withString 等一系列 with 开头的方法设置与之对应的参数来进行参数传递，具体如下：


```java
// 应用内携带参数跳转
ARouter.getInstance()
        .build(SecondActivity.PATH)
        .withString(SecondActivity.PARAM, "这是跳转携带的参数")
        .navigation();
```

然后使用 Intent 在跳转到的 Activity 中使用 Intent 获取传递过来的参数，具体如下：


```java
@Route(path = SecondActivity.PATH)
public class SecondActivity extends AppCompatActivity {

    public static final String PATH = "/test/secondActivity";
    public static final String PARAM = "param";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        if (intent!=null){
            String param = intent.getStringExtra(PARAM);
            Toast.makeText(this, param, Toast.LENGTH_SHORT).show();
        }
    }
}
```

#### Activity返回结果处理

Activity 返回结果处理和原生几乎一致，即在跳转时携带 requestCode，具体如下：


```java
// Activity返回结果处理
ARouter.getInstance()
        .build(ThreeActivity.PATH)
        .navigation(this, 100);
```

然后，在 Activity 返回的时候使用 Intent 携带参数 setResult 即可，具体如下：



```java
@Route(path = ThreeActivity.PATH)
public class ThreeActivity extends AppCompatActivity {

    public static final String PATH = "/test/threeActivity";
    public static final String PARAM_RESULT = "param_result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        Intent intent = getIntent();
        //setResult
        intent.putExtra(PARAM_RESULT,"这是返回携带的参数");
        setResult(RESULT_OK,intent);
    }
}
```

#### 通过Uri跳转及参数解析

ARouter 还支持通过 Uri 进行跳转，首先创建一个无界面的 Activity 用于监听 Scheme 事件，由该 Activity 进行统一转发 Uri，所有的 Uri 都要通过这里然后进行分发跳转，可以很好的进行 Uri 的控制，一定程度上提高了使用 Uri 跳转的安全性，实行一个无界面的 Activiry 如下：


```java
public class SchemeFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri uri = getIntent().getData();
        // 统一外部跳转的Uri，实现路由器统一分发，减少只依靠Intent属性匹配带来的安全风险
        ARouter.getInstance().build(uri).navigation(this, new NavCallback() {
            @Override
            public void onArrival(Postcard postcard) {
                finish();
            }
        });
    }
}
```

在 AndroidManifest 文件中配置 host、scheme 以及 Action，具体如下：

```xml
<activity android:name=".SchemeFilterActivity">
    <intent-filter>
        <data
            android:host="test.manu.com"
            android:scheme="arouter" />

        <action android:name="com.manu.route" />
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.BROWSABLE" />
        <category android:name="android.intent.category.DEFAULT" />
    </intent-filter>
</activity>
```

然后，在 assets 文件夹中创建一个 html 文件，通过点击跳转链接完成 Uri 的跳转，html 内容如下：


```html
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <h2>跳转测试</h2>
        <h2>自定义Scheme</h2>
        <p>
            <!--不带参数-->
            <a href="arouter://test.manu.com/test/fiveActivity">arouter://test111.manu.com/test/fiveActivity</a>
        </p>
        <p>
            <!--携带参数-->
            <a href="arouter://test.manu.com/test/sixActivity?name=alex&age=18&score=%7B%22score%22:%2290%22,%22rank%22:%222%22%7D">arouter://test111.manu.com/test/sixActivity?name=alex&age=18&score={"score":"90","rank":"2"}</a>
        </p>
    </body>
</html>
```

具体效果查看运行效果图。

然后，使用 WebView 加载该 Html，就可以跳转到对应的 Activity 了，也就是链接中的 fiveActivity 和 SixActivity，两个 Activity 分别如下：


```java
// FiveActivity
@Route(path = FiveActivity.PATH)
public class FiveActivity extends AppCompatActivity {

    public static final String PATH = "/test/fiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
    }
}

// SixActivity
@Route(path = SixActivity.PATH)
public class SixActivity extends AppCompatActivity {
    public static final String PATH = "/test/sixActivity";
    @Autowired
    public String name;
    @Autowired
    public int age;
    @Autowired
    // 如果要在Uri中传递自定义对象，在参数中要使用json字符串(encodeURI转码)传递，创建一个实现了SerializationService接口的类完成json的解析
    public ScoreBean score;
    @BindView(R.id.tvData)
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        ButterKnife.bind(this);
        // 参数自动依赖注入
        ARouter.getInstance().inject(this);
        String info = "name=" + name + ",age=" + age + ",score=" + score;
        tvData.setText(info);
        Log.i("SixActivity", info);
    }
}
```

#### Module之间的跳转

主 module 和子 module 和子 module 之间的跳转也非常容易，如主 module 跳转子 module，当然主 module 和子 module 都在配置 ARouter 才可以进行进行跳转，可在主 module 中创建一个接口管理要跳转的子 module 的路径，具体如下：


```java
// 管理跳转路径
public interface Module {
    String MODULE_ONE = "/module1/module-one";
    String MODULE_TWO = "/module2/module-two";
}
```

然后，直接进行跳转，具体如下：


```java
//跳转Module-one
ARouter.getInstance()
        .build(Module.MODULE_ONE)
        .navigation();
```

#### 服务调用

ARouter 里面的服务调用不能和 Android 里面的 Service 相混淆，ARouter 的里面的服务调用实际上是对某个业务的封装，通过 ARouter 这一层的统一封装，使得调用起来更方便，只需知道路径和名称就可以随意调用，实现 IProvider 创建一个 Service 如下：


```java
@Route(path = "/service/singleService")
public class SingleService implements IProvider {
    public static final String PATH = "/service/singleService";
    private Context mContext;

    //具体服务
    public void showMessage() {
        Toast.makeText(mContext, "这是对外提供的服务", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void init(Context context) {
        this.mContext = context;
        Log.i("SingleService", "SingleService has init");
    }
}
```

然后就可以调用了，调用方式如下：


```java
// 通过服务类class调用
ARouter.getInstance().navigation(SingleService.class).showMessage();
// 通过服务类Path调用
((SingleService) ARouter.getInstance()
        .build(SingleService.PATH)
        .navigation())
        .showMessage();
```

此外，还可以使用依赖注入的方式完成服务的调用，这种方式便于多个服务进行管理，创建一个服务管理类如下：

```java
// 服务管理类
public class ServiceManage {
    @Autowired
    SingleService singleService;

    public ServiceManage(){
        //通过依赖注入的方式获取服务
        ARouter.getInstance().inject(this);
    }

    public void getService(){
        singleService.showMessage();
    }
}
```

然后通过服务管理类调用具体的服务如下：


```java
//服务管理，通过依赖注入的方式获取服务
ServiceManage manage = new ServiceManage();
manage.getService();
```

#### 显示效果

上述实现的测试效果如下图所示：

![Android组件化之ARouter的使用](https://github.com/jzmanu/ComponentizationSamples/blob/master/arouter.gif?raw=true)

ARouter 功能比较全面，使用起来也非常简单，上面的内容也是最常使用到的，如其他功能如拦截器、降级策略、转场动画、映射关系分组等用到直接参考官方文档实践即可。





