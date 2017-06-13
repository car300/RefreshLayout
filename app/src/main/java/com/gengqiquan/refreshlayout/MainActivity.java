package com.gengqiquan.refreshlayout;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.gengqiquan.library.RefreshLayout;
import com.sunshine.adapterlibrary.adapter.RBAdapter;
import com.sunshine.adapterlibrary.adapter.RPAdapter;
import com.sunshine.adapterlibrary.interfaces.Holder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RefreshLayout refresh;
    RBAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (RefreshLayout) findViewById(R.id.refresh);
        //SampleRefreshLayout特有的方法需放在链式调用的前面
//        refresh.refreshEnable(true)
//                .loadMoreEnable(true)
//                .showTopView(true)
//                .noDataLable("暂时没有订单数据")
//                .noDataImg(R.drawable.message_default)
//                .pageCount(20)
        adapter = new RBAdapter<String>(this)
                .layout(R.layout.item_main_list)
                .bindViewData(this::bindViewData);
        refresh.adapter(adapter)
                .refresh(() -> load(true))
                  .loadMore(() -> load(false))
                .doRefresh();
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.img_no_message));
        adapter.addHeaderView(imageView);
//        ImageView foot = new ImageView(this);
//        foot.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_replace));
//        adapter.addFooterView(foot);

    }

    public void bindViewData(Holder holder, String item) {
        holder.setText(R.id.text, item);
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
                Log.e("isrefresh","isrefresh");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> list = new ArrayList();
                        for (int i = 0; i < 20; i++) {
                            list.add("           " + i);
                        }
//                        if (isrefresh)
//                            refresh.refreshComplete(list);
//                        else
//                            refresh.loadMoreComplete(list);
                       refresh.loadFailure();
                    }
                });

            }
        }).start();
    }
}
