# SimpleAdapter

通用列表适配器

|版本号|更新时间|更新人|更新内容|
|---|---|---|---|
|1.0.1|2020-06-12|胡晟昊|废弃RefreshLayout，添加BRVAH适配器|


RecyclerView

- RB/PAdapter，不支持多布局
- [BRVAH](http://www.recyclerview.org/)适配器 [Github](https://github.com/CymChad/BaseRecyclerViewAdapterHelper) [中文文档](https://github.com/CymChad/BaseRecyclerViewAdapterHelper/blob/master/readme/0-BaseRecyclerViewAdapterHelper.md)

ListView

- SB/PAdapter


⚠ 不再推荐使用~~RefreshLayout及其所有相关视图组件~~

## 如何使用

```gradle
// 添加仓库地址
maven { url 'http://172.16.0.236:8081/repository/maven-public/' }

// ⚠ 删除如下依赖
implementation "com.car300.android_modules:refreshlayout:x.x.x"

// 添加新的依赖
implementation "com.che300.basic:simple-adapter:1.0.0"
```

