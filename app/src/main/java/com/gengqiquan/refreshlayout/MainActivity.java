package com.gengqiquan.refreshlayout;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.gengqiquan.layout.SampleRefreshLayout;
import com.sunshine.adapterlibrary.adapter.SBAdapter;
import com.sunshine.adapterlibrary.interfaces.Holder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SampleRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (SampleRefreshLayout) findViewById(R.id.refresh);
        //SampleRefreshLayout特有的方法需放在链式调用的前面
        refresh.refreshEnable(true)
                .loadMoreEnable(true)
                .showTopView(true)
                .noDataLable("暂时没有订单数据")
                .noDataImg(R.drawable.message_default)
                .pageCount(20)
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
}
