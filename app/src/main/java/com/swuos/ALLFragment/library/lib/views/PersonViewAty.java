package com.swuos.ALLFragment.library.lib.views;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.swuos.ALLFragment.library.lib.MyItemDecoration;
import com.swuos.ALLFragment.library.lib.adapters.RecyclerAdapterPerson;
import com.swuos.ALLFragment.library.lib.utils.LibTools;
import com.swuos.ALLFragment.library.lib.utils.MetricUtils;
import com.swuos.swuassistant.BaseActivity;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

import java.util.List;

/**
 * Created by : youngkaaa on 2016/9/4.
 * Contact me : 645326280@qq.com
 */
public class PersonViewAty extends BaseActivity {
    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerAdapterPerson recyclerAdapter;
    private List<String> keys;
    private List<String> vals;
    private Toolbar toolbar;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private int lastYOffset;
    private FloatingActionButton fabIcon;
    private boolean isUp = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(PersonViewAty.this, "获取数据成功！", Toast.LENGTH_SHORT).show();
                    recyclerAdapter = new RecyclerAdapterPerson(keys, vals);
                    recyclerView.setAdapter(recyclerAdapter);
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_person_view);

        appBarLayout = (AppBarLayout) findViewById(R.id.appbarLayoutLibPerson);
        toolbar = (Toolbar) findViewById(R.id.toolBarLibPersonView);
        //        Drawable drawable = getResources().getDrawable(R.drawable.lib_back_person);
        toolbar.setNavigationIcon(R.drawable.toolbar_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        fabIcon = (FloatingActionButton) findViewById(R.id.fabPersonViewIcon);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayoutLibPerson);
        dynamicAddView(collapsingToolbarLayout, "CollapsingToolbarLayoutcontent", R.color.colorPrimary);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPersonViewMain);
        recyclerView.addItemDecoration(new MyItemDecoration(this));
        recyclerView.setNestedScrollingEnabled(true);
        collapsingToolbarLayout.setTitle("个人详情");
        getData();

        fabIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAni();
                getData();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int screenWIdth = (int) MetricUtils.getScrWidth(PersonViewAty.this);
                int width = (int) (screenWIdth - MetricUtils.dp2px(PersonViewAty.this, 28));
                int height = (int) MetricUtils.dp2px(PersonViewAty.this, 208);  //208dp
                if (verticalOffset < lastYOffset) {
                    isUp = true;
                } else {
                    isUp = false;
                }
                lastYOffset = verticalOffset;
                if (isUp && verticalOffset <= -120) {
                    SALog.d("kklog","addOnOffsetChangedListener fabIcon.hide()");
                    SALog.d("kklog","addOnOffsetChangedListener isUp verticalOffset==>"+verticalOffset);
                    fabIcon.hide();
                } else if ((!isUp) && verticalOffset >= -30 && verticalOffset!=0) {
                    SALog.d("kklog","addOnOffsetChangedListener !isUp verticalOffset==>"+verticalOffset);
                    SALog.d("kklog","addOnOffsetChangedListener fabIcon.show()");
                    fabIcon.show();
                }
            }
        });

        imageView = (ImageView) findViewById(R.id.imageViewPersonViewTop);
        imageView.setImageResource(R.drawable.lib_person_bg);

        keys = LibTools.getPersonKeys();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fabIcon.show();
    }

    private void startAni() {
        Animation animation= AnimationUtils.loadAnimation(PersonViewAty.this,R.anim.lib_person_refresh);
        fabIcon.startAnimation(animation);
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                vals = LibTools.getPersonVals();
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
