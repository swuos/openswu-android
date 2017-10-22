package com.swuos.ALLFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.swuos.ALLFragment.card.view.EcardFragmentImp;
import com.swuos.ALLFragment.charge.ChargeFragment;
import com.swuos.ALLFragment.find_lost.FindLostFragment;
import com.swuos.ALLFragment.library.library.fragment.LibFragment;
import com.swuos.ALLFragment.study_materials.StudyMaterialsFragment;
import com.swuos.ALLFragment.swujw.grade.GradesNewFragment;
import com.swuos.ALLFragment.swujw.schedule.ScheduleFragment;
import com.swuos.ALLFragment.wifi.WifiFragment;
import com.swuos.swuassistant.Constant;
import com.swuos.swuassistant.R;
import com.swuos.util.SALog;

/**
 * Created by 张孟尧 on 2016/4/6.
 */
public class FragmentControl {


    /*课程表界面布局*/
    private ScheduleFragment scheduleFragment;
    private EcardFragmentImp cardfragment;
    /*成绩界面布局*/
    private GradesNewFragment gradesNewFragment;

    /*学习资料界面布局*/
    private StudyMaterialsFragment studyMaterialsFragment;
    /*图书馆界面布局*/
    private LibFragment libraryFragment;
    /*水电费界面布局*/
    private ChargeFragment chargeFragment;
    /*失物找寻界面布局*/
    private FindLostFragment findLostFragment;
    private WifiFragment wifiFragment;
    private FragmentManager fragmentManager;

    public FragmentControl(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    private void hideFragments(FragmentTransaction fragmentTransaction) {


        if (gradesNewFragment != null) {
            SALog.d("MainActity", "HideGrades");
            fragmentTransaction.hide(gradesNewFragment);
        }
        if (scheduleFragment != null) {
            SALog.d("MainActity", "Hideschedule");
            fragmentTransaction.hide(scheduleFragment);
        }
        if (studyMaterialsFragment != null) {
            fragmentTransaction.hide(studyMaterialsFragment);
        }
        if (wifiFragment != null) {
            SALog.d("MainActity", "HideWifi");

            fragmentTransaction.hide(wifiFragment);
        }
        if (libraryFragment != null) {
            SALog.d("MainActity", "HideLibrary");
            fragmentTransaction.hide(libraryFragment);
        }
        if (cardfragment != null) {
            SALog.d("MainActity", "Hidecardfragment");

            fragmentTransaction.hide(cardfragment);
        }

        if (chargeFragment != null) {
            SALog.d("MainActity", "HidechargeFragment");
            fragmentTransaction.hide(chargeFragment);
            chargeFragment.setHidden(true);
        }
        if (findLostFragment != null) {
            fragmentTransaction.hide(findLostFragment);
        }

    }

    public void fragmentSelection(int id) {
        FragmentTransaction transaction;
        transaction = fragmentManager.beginTransaction();

        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (id) {
            case R.id.nav_schedule:
                if (scheduleFragment == null) {
                    scheduleFragment = new ScheduleFragment();
                    transaction.add(R.id.content, scheduleFragment, Constant.FRAGMENTTAG[1]);
                } else {
                    transaction.show(scheduleFragment);
                }

                break;
            case R.id.nav_grades:
                if (gradesNewFragment == null) {
                    gradesNewFragment = new GradesNewFragment();
                    transaction.add(R.id.content, gradesNewFragment, Constant.FRAGMENTTAG[2]);
                } else {
                    transaction.show(gradesNewFragment);
                }
                break;
            case R.id.nav_library:

                if (libraryFragment == null) {
                    libraryFragment = new LibFragment();
                    transaction.add(R.id.content, libraryFragment, Constant.FRAGMENTTAG[6]);
                } else {
                    transaction.show(libraryFragment);
                }
                break;
            case R.id.nav_wifi:
                if (wifiFragment == null) {
                    wifiFragment = new WifiFragment();
                    transaction.add(R.id.content, wifiFragment, Constant.FRAGMENTTAG[7]);
                } else {
                    transaction.show(wifiFragment);
                }
                break;
            case R.id.nav_ecard:
                if (cardfragment == null) {
                    cardfragment = new EcardFragmentImp();
                    transaction.add(R.id.content, cardfragment, Constant.FRAGMENTTAG[8]);
                } else {
                    transaction.show(cardfragment);
                }
                break;
            case R.id.nav_charge:
                if (chargeFragment == null) {
                    chargeFragment = new ChargeFragment();
                    transaction.add(R.id.content, chargeFragment, Constant.FRAGMENTTAG[5]);
                } else {
                    transaction.show(chargeFragment);
                }

                break;
            /*case R.id.nav_study_materials:

                if (studyMaterialsFragment == null) {
                    // 如果studyMaterialsFragment为空，则创建一个并添加到界面上
                    studyMaterialsFragment = new StudyMaterialsFragment();
                    transaction.add(R.id.content, studyMaterialsFragment, Constant.FRAGMENTTAG[3]);
                } else {
                    // 如果studyMaterialsFragment不为空，则直接将它显示出来
                    transaction.show(studyMaterialsFragment);
                }
                break;
            case R.id.nav_find_lost:


                if (findLostFragment == null) {
                    // 如果findLostFragment为空，则创建一个并添加到界面上
                    findLostFragment = new FindLostFragment();
                    transaction.add(R.id.content, findLostFragment, Constant.FRAGMENTTAG[4]);
                } else {
                    // 如果findLostFragment不为空，则直接将它显示出来
                    transaction.show(findLostFragment);
                }
                break;
          */
            default:
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    public void initFragment(FragmentManager fragmentManager) {
        FragmentTransaction transaction;
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();


        scheduleFragment = new ScheduleFragment();
        transaction.add(R.id.content, scheduleFragment, Constant.FRAGMENTTAG[1]);


        cardfragment = new EcardFragmentImp();
        transaction.add(R.id.content, cardfragment, Constant.FRAGMENTTAG[3]);

        studyMaterialsFragment = new StudyMaterialsFragment();
        transaction.add(R.id.content, studyMaterialsFragment, Constant.FRAGMENTTAG[3]);

        findLostFragment = new FindLostFragment();
        transaction.add(R.id.content, findLostFragment, Constant.FRAGMENTTAG[4]);

        chargeFragment = new ChargeFragment();
        transaction.add(R.id.content, chargeFragment, Constant.FRAGMENTTAG[5]);


        libraryFragment = new LibFragment();
        transaction.add(R.id.content, libraryFragment, Constant.FRAGMENTTAG[6]);
        transaction.commit();
        hideFragments(transaction);
    }

    public void fragmentStateCheck(Bundle saveInstanceState, FragmentManager fragmentManager, int fragmentPosition) {

        if (saveInstanceState.get("scheduleFragment") != null) {
            scheduleFragment = new ScheduleFragment();
            scheduleFragment = (ScheduleFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[1]);
            SALog.d("FragmentControl", "saveInstanceState存在数据,SchedulFragment");

        }
        if (saveInstanceState.get("gradesFragment") != null) {
            //            gradesFragment = new GradesFragment();
            //            gradesFragment = (GradesFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[2]);
            gradesNewFragment = new GradesNewFragment();
            gradesNewFragment = (GradesNewFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[2]);
            SALog.d("FragmentControl", "saveInstanceState存在数据,gradesFragment");

        }
        if (saveInstanceState.get("cardfragment") != null) {
            cardfragment = new EcardFragmentImp();
            cardfragment = (EcardFragmentImp) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[8]);
            SALog.d("FragmentControl", "saveInstanceState存在数据,cardFragment");

        }
        if (saveInstanceState.get("chargeFragment") != null) {
            chargeFragment = new ChargeFragment();
            chargeFragment = (ChargeFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[5]);
            SALog.d("FragmentControl", "saveInstanceState存在数据,chargeFragment");

        }
        if (saveInstanceState.get("wifiFragment") != null) {
            wifiFragment = new WifiFragment();
            wifiFragment = (WifiFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[7]);
            SALog.d("FragmentControl", "saveInstanceState存在数据,wifiragment");

        }
        if (saveInstanceState.get("libraryFragment") != null) {
            libraryFragment = new LibFragment();
            libraryFragment = (LibFragment) fragmentManager.findFragmentByTag(Constant.FRAGMENTTAG[6]);
            SALog.d("FragmentControl", "saveInstanceState存在数据,findLibraryFragment");

        }


    }
}


