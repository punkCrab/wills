package com.wills.help.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wills.help.R;
import com.wills.help.assist.ui.AssistFragment;
import com.wills.help.home.ui.HomeFragment;
import com.wills.help.person.ui.PersonFragment;
import com.wills.help.release.ui.MainReleaseFragment;
import com.wills.help.utils.ToastUtils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private FrameLayout frameLayout;
    private BottomNavigationBar bottomNavigationBar;
    private static ArrayList<Fragment> fragments;
    private long mExitTime = 0;
    private static String[] tags ;
    private Fragment currentView;
    private HomeFragment homeFragment;
    private MainReleaseFragment mainReleaseFragment;
    private AssistFragment assistFragment;
    private PersonFragment personFragment;
//    private BottomNavigationItem msgItem;
    @Override
    protected void initViews(Bundle savedInstanceState) {
        setNoActionBarView(R.layout.activity_main);
        frameLayout = (FrameLayout) findViewById(R.id.fl_content);
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        tags = new String[]{getString(R.string.tab_home), getString(R.string.tab_release) , getString(R.string.tab_assist), getString(R.string.tab_person)};
        initBottomNavigationBar();
        stateCheck(savedInstanceState);
    }

    private void initBottomNavigationBar(){
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setTabSelectedListener(this);
//        msgItem = new BottomNavigationItem(R.drawable.tab_msg, R.string.tab_msg).setActiveColorResource(R.color.colorPrimaryDark);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.tab_home , R.string.tab_home).setActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.tab_release, R.string.tab_release).setActiveColorResource(R.color.colorPrimaryDark))
                .addItem(new BottomNavigationItem(R.drawable.tab_assist, R.string.tab_assist).setActiveColorResource(R.color.colorPrimaryDark))
//                .addItem(msgItem)
                .addItem(new BottomNavigationItem(R.drawable.tab_person, R.string.tab_person).setActiveColorResource(R.color.colorPrimaryDark))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
//        AddBadge(5);
    }

    private void setDefaultFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fl_content,fragments.get(0),tags[0]);
        transaction.commit();
        currentView = fragments.get(0);
    }

    private void stateCheck(Bundle savedInstanceState){
        if (savedInstanceState == null){
            setDefaultFragment();
        }else{
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(tags[0]);
            mainReleaseFragment = (MainReleaseFragment) getSupportFragmentManager().findFragmentByTag(tags[1]);
            assistFragment = (AssistFragment) getSupportFragmentManager().findFragmentByTag(tags[2]);
            personFragment = (PersonFragment) getSupportFragmentManager().findFragmentByTag(tags[3]);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.hide(mainReleaseFragment).hide(assistFragment).hide(personFragment).show(homeFragment).commit();
        }
    }

    private ArrayList<Fragment> getFragments(){
        ArrayList<Fragment> fragments = new ArrayList<>();
        homeFragment = HomeFragment.newInstance();
        fragments.add(homeFragment);
        mainReleaseFragment = MainReleaseFragment.newInstance();
        fragments.add(mainReleaseFragment);
        assistFragment = AssistFragment.newInstance();
        fragments.add(assistFragment);
//        fragments.add(MessageFragment.newInstance());
        personFragment = PersonFragment.newInstance();
        fragments.add(personFragment);
        return fragments;
    }

    private void switchView(Fragment to , int position){
        if (currentView != to){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()){
                transaction.hide(currentView).add(R.id.fl_content,to,tags[position]).commitAllowingStateLoss();
            }else {
                transaction.hide(currentView).show(to).commitAllowingStateLoss();
            }
        }
        currentView = to;
    }

//    private void AddBadge(int number){
//        BadgeItem numberBadgeItem = new BadgeItem()
//                .setBorderWidth(5)
//                .setBackgroundColorResource(R.color.red)
//                .setText(number+"")
//                .setHideOnSelect(true);
//        msgItem.setBadgeItem(numberBadgeItem);
//        bottomNavigationBar.initialise();
//    }

    @Override
    public void onTabSelected(int position) {
        if (fragments !=null){
            if (position<fragments.size()){
                Fragment fragment = fragments.get(position);
                switchView(fragment,position);
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {
//        if (fragments != null) {
//            if (position < fragments.size()) {
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Fragment fragment = fragments.get(position);
//                ft.remove(fragment);
//                ft.commitAllowingStateLoss();
//            }
//        }
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            if ((System.currentTimeMillis() - mExitTime) > 2000){
                ToastUtils.toast(R.string.back_exit);
                mExitTime = System.currentTimeMillis();
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
