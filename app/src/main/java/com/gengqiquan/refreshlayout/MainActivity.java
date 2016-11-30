package com.gengqiquan.refreshlayout;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.gengqiquan.layout.SampleRefreshLayout;
import com.gengqiquan.layout.interfaces.LoadMoreListener;
import com.gengqiquan.layout.interfaces.RefreshListener;
import com.sunshine.adapterlibrary.adapter.SBAdapter;
import com.sunshine.adapterlibrary.interfaces.Converter;
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
        refresh.pageCount(20)
                .refreshEnable(true)
                .loadMoreEnable(true)
                .showTopView(true)
                .adapter(new SBAdapter<String>(this)
                        .layout(android.R.layout.simple_list_item_1)
                        .bindViewData(new Converter<String>() {
                            @Override
                            public void convert(Holder holder, String item) {
                                holder.setText(android.R.id.text1, item);
                            }
                        }))
                .refresh(new RefreshListener() {
                    @Override
                    public void onRefresh() {
                        load(true);
                    }

                })
                .loadMore(new LoadMoreListener() {
                    @Override
                    public void LoadMore() {
                        load(false);
                    }
                })
                .doRefresh();

    }

    private void load(final boolean b) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    if (b)
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
                        refresh.hasMoreData(true);
                        if (b)
                            refresh.refreshComplete(list);
                        else
                            refresh.loadMoreComplete(list);

                    }
                });
            }
        }).start();
    }
}
