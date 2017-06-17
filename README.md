# RefreshLayout
通用列表组件，让你半小时以内完成一个列表界面

## 样式效果如图

![刷新](http://img.blog.csdn.net/20161008152441933)![无数据](http://img.blog.csdn.net/20161008152457761)![加载失败](http://img.blog.csdn.net/20161008152510715)


## 目前

 * 以RecyclerView作为列表控件，通过layoutManager切换布局形式
 * 集成了失败界面展示，没有数据界面，可以继承此类实现自己的失败界面、无数据界面等
 * 可以继承RefreshLayout样式设置自己应用的默认的样式及属性
 * 下拉刷新，加载更多
 * 悬浮按钮
 * 万能适配器 适配器添加header和footer
 * 下拉刷新采用的谷歌的SwipeRefreshLayout
 * 可以继承RefreshLayout样式设置自己应用的默认的样式及属性
 * 提供一个SimpleRefreshLayout，提供一些默认的样式及属性

## 计划
 * 支持切换下拉刷新控件
 * 控件上添加header 上滑隐藏header，下滑显示header
 * 添加更多预置模板和样式
 
 更多需求欢迎提建议
 


# 使用
## xml
```xml
  
   <com.gengqiquan.library.SimpleRefreshLayout
        android:id="@+id/refresh"
        style="@style/RefreshLayoutBase"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></com.gengqiquan.library.SimpleRefreshLayout>
```
## activity初始化
```java
   SimpleRefreshLayout refresh;
    RBAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (SimpleRefreshLayout) findViewById(R.id.refresh);
        //SimpleRefreshLayout特有的方法需放在链式调用的前面
        adapter = new RBAdapter<String>(this)
                .layout(R.layout.item_main_list)
                .bindViewData(this::bindViewData);
        refresh.setNoDataLableText("暂无数据")
                .setNoDataImgRes(R.drawable.message_default)
                .adapter(adapter)
                .refreshEnable(true)
                .refresh(() -> load(true))
                .loadMore(() -> load(false))
                .doRefresh();
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_no_message));
        adapter.addHeaderView(imageView);
    }

    public void bindViewData(Holder holder, String item) {
        holder.setText(R.id.text, item);
    }

```

## 带位置参数的adapter
```java

   new SPAdapter<String>(this)
    .layout(android.R.layout.simple_list_item_1)
    .bindPositionData(this::bindViewData);
                

   
    public void bindViewData(Holder holder, String item, int p) {
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
 
public class SimpleRefreshLayout extends RefreshLayout {
    SimpleRefreshLayout mSimpleRefreshLayout;

    public SimpleRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSimpleRefreshLayout = new SimpleRefreshLayout(context);
    }

    public SimpleRefreshLayout setNoDataLableText(String str) {
        mSimpleRefreshLayout.setNoDataLableText(str);
        return this;
    }

    public SimpleRefreshLayout setNoDataImgRes(int res) {
        mSimpleRefreshLayout.setNoDataImgRes(res);
        return this;
    }
```


# 依赖添加
## maven
```xml
<dependency>
  <groupId>com.gengqiquan</groupId>
  <artifactId>refreshlayout</artifactId>
  <version>1.0.7</version>
  <type>pom</type>
</dependency>
```
## gralde 
```
compile 'com.gengqiquan:refreshlayout:1.0.7'
```
## lvy
```xml
<dependency org='com.gengqiquan' name='refreshlayout' rev='1.0.7'>
  <artifact name='refreshlayout' ext='pom' ></artifact>
</dependency>
```
