package com.swuos.mobile.ui;

import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;

import com.swuos.mobile.R;
import com.swuos.mobile.app.App;
import com.swuos.mobile.app.BaseActivity;
import com.swuos.mobile.models.user.UserModel;
import com.swuos.mobile.ui.tab.CourseTableFragment;
import com.swuos.mobile.ui.tab.MineFragment;
import com.swuos.mobile.ui.tab.ScoreFragment;
import com.swuos.mobile.utils.CommonUtils;
import com.swuos.mobile.utils.injector.Model;
import com.swuos.mobile.view.TabItemView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements TabItemView.OnTabItemStateWillChangeDelegate  {

    private static final long EXIT_TIME_INTERVAL = 2000;

    private long mBackPressedTime = System.currentTimeMillis() - EXIT_TIME_INTERVAL;

    private Toolbar toolbar;

    private TabItemView tabCourseTable;

    private TabItemView tabScore;

    private TabItemView tabMine;
    /**
     * 底部选项卡列表
     */
    private List<TabItemView> tabs = new ArrayList<>();

    @Model
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tabCourseTable = findViewById(R.id.tab_course_table);
        tabScore = findViewById(R.id.tab_score);
        tabMine = findViewById(R.id.tab_mine);
        tabCourseTable.setFragmentClass(CourseTableFragment.class);
        tabScore.setFragmentClass(ScoreFragment.class);
        tabMine.setFragmentClass(MineFragment.class);
        tabCourseTable.setDelegate(this);
        tabScore.setDelegate(this);
        tabMine.setDelegate(this);
        tabs.clear();
        tabs.add(tabCourseTable);
        tabs.add(tabScore);
        tabs.add(tabMine);
        setIndexWithoutException(0);
    }

    /**
     * 选中某项标签
     */
    private void setIndexWithoutException(@IntRange(from = 0) int index) {
        if (App.isDebug()) {
            if (index >= tabs.size() || index < 0) {
                throw new RuntimeException("index >= tabs.size() || index < 0, current index = " + index);
            }
        }
        try {
            setIndex(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换底部选项卡
     *
     * @param index
     * @throws Exception
     */
    private void setIndex(int index) throws Exception {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < tabs.size(); i++) {
            TabItemView tab = tabs.get(i);
            Class<? extends Fragment> fragmentClass = tab.getFragmentClass();
            String tag = fragmentClass.getName();
            Fragment fragmentByTag = fragmentManager.findFragmentByTag(tag);

            if (i == index) {
                tab.setItemSelected(true);
                // 添加tab
                if (fragmentByTag == null) {
                    fragmentTransaction.add(R.id.fragment_container, fragmentClass.newInstance(), tag);
                } else {
                    if (fragmentByTag.isDetached()) {
                        fragmentTransaction.attach(fragmentByTag);
                    }
                }
                toolbar.setTitle(tabs.get(index).getText());
            } else if (tab.isItemSelected()) {
                tab.setItemSelected(false);
                // 移除tab
                if (fragmentByTag != null) {
                    fragmentTransaction.detach(fragmentByTag);
                }
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean shouldChangeTabItemState(TabItemView tabItemView) {
        if (tabItemView.isItemSelected()) {
            // 本身就是选中的，不改变
            return false;
        }
        return true;
    }

    @Override
    public void onTabItemStatChanged(TabItemView tabItemView) {
        int index = 0;
        for (int i = 0; i < tabs.size(); i++) {
            if (tabs.get(i) == tabItemView) {
                index = i;
                break;
            }
        }
        setIndexWithoutException(index);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mBackPressedTime <= EXIT_TIME_INTERVAL) {//退到手机系统首页
            CommonUtils.startHome(getContext());
        } else {
            mBackPressedTime = currentTime;
            showToast(R.string.exit_application);
        }
    }

}
