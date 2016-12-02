# RefreshLayout
通用列表组件，让你半小时以内完成一个列表界面

默认提供一个SampleRefreshLayout。提供一些默认的样式

下拉刷新采用的谷歌的SwipeRefreshLayout

列表目前是listview，后续会增加其他类型列表的切换

可以继承RefreshLayout抽象类实现自己的样式



# 使用
## xml
```xml
  
    <com.gengqiquan.layout.SampleRefreshLayout
        android:id="@+id/refresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></com.gengqiquan.layout.SampleRefreshLayout>
```
## activity初始化
```java
     refresh = (SampleRefreshLayout) findViewById(R.id.refresh);
     //SampleRefreshLayout特有的方法需放在链式调用的前面
        refresh .noDataLable("暂时没有订单数据")//无数据文本，默认暂无数据
                .noDataImg(R.drawable.message_default)//无数据图片
                .pageCount(20)//分页数量，默认20
                .refreshEnable(true)//允许手动刷新，默认true
                .loadMoreEnable(true)//允许分页，默认true
                .showTopView(true)//显示一键回到顶部按钮，默认false
                .adapter(new SBAdapter<String>(this)
                        .layout(android.R.layout.simple_list_item_1)//item布局
                        .bindViewData(new Converter<String>() {
                            @Override
                            public void convert(Holder holder, String item) {//数据绑定
                                holder.setText(android.R.id.text1, item);
                            }
                        }))//通用适配器
                .refresh(new RefreshListener() {//刷新回调
                    @Override
                    public void onRefresh() {
                        load(true);
                    }

                })
                .loadMore(new LoadMoreListener() {//加载更多回调
                    @Override
                    public void LoadMore() {
                        load(false);
                    }
                })
                .doRefresh();//主动调用刷新加载初始数据
```

## lambda写法
```java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (SampleRefreshLayout) findViewById(R.id.refresh);
       //SampleRefreshLayout特有的方法需放在链式调用的前面
        refresh .noDataLable("暂时没有订单数据")
                .noDataImg(R.drawable.message_default)
                .pageCount(20)
                .refreshEnable(true)
                .loadMoreEnable(true)
                .showTopView(true)
                .adapter(new SBAdapter<String>(this)
                        .layout(android.R.layout.simple_list_item_1)
                        .bindViewData(this::bindViewData))
                .refresh(()->load(true))
                .loadMore(()-> load(false))
                .doRefresh();

    }
    public void bindViewData(Holder holder, String item) {
        holder.setText(android.R.id.text1, item);
    }
```
## load()请求

```java
 private void load(final boolean isrefresh) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    if (isrefresh)
                    Thread.sleep(2000);
                    else
                        Thread.sleep(500);
                } catch (Exception e) {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList();
                        for (int i = 0; i < 20; i++) {
                            list.add("           " + i);
                        }
                        if (isrefresh)
                            refresh.refreshComplete(list);
                        else
                            refresh.loadMoreComplete(list);
                       //请求失败调用 refresh.loadFailure();
                    }
                });

            }
        }).start();
    }
```
## 继承RefreshLayout实现自己布局样式

```  java
 public class MyRefreshLayout extends RefreshLayout {
 
    //添加自定义加载更多界面
    public SampleRefreshLayout FooterLayout(FooterLayout layout) {
        mLoadMoreView = layout;
        return this;
    }

//添加自定义无数据界面，需实现NoDataLayout接口
    @Override
    NoDataLayout onCreateNoDataView() {
        return new SimpleNoDataLayout(mContext);
    }
//添加自定义加载更多布局，需实现FooterLayout接口
    @Override
    FooterLayout onCreateLoadMoreView() {
        return mLoadMoreView;
    }
//添加自定义回到顶部按钮布局
    @Override
    View onCreateTopView() {
        return mTopView;
    }
//添加自定义请求失败布局
    @Override
    View onCreateFailureView() {
        return mFailureView;
    }

    public SampleRefreshLayout(Context context) {
        this(context, null);
    }

    public SampleRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SampleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        build();//记得这一句
    }
```


# 依赖添加
## maven
```xml
<dependency>
  <groupId>com.gengqiquan.refresh-layout</groupId>
  <artifactId>layout</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```
## gralde 
```
compile 'com.gengqiquan.refresh-layout:layout:1.0.1'
```
## lvy
```xml
<dependency org='com.gengqiquan.refresh-layout' name='layout' rev='1.0.1'>
  <artifact name='layout' ext='pom' ></artifact>
</dependency>
```
