package com.gengqiquan.refreshlayout;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.gengqiquan.library.RefreshLayout;
import com.sunshine.adapterlibrary.adapter.RBAdapter;
import com.sunshine.adapterlibrary.interfaces.Holder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RefreshLayout refresh;
    RBAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        //SimpleRefreshLayout特有的方法需放在链式调用的前面
        adapter = new RBAdapter<String>(this)
                .layout(R.layout.item_main_list)
                .bindViewData(this::bindViewData);
        refresh.adapter(adapter)
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

    private void load(final boolean isrefresh) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (isrefresh)
                        Thread.sleep(2000);
                    else
                        Thread.sleep(500);
                } catch (Exception e) {
                }
                Log.e("isrefresh", "isrefresh");
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
                        //refresh.loadFailure();
                    }
                });

            }
        }).start();
    }
}
