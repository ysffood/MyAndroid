package com.yk.demo.myandroid.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yk.demo.myandroid.R;
import com.yk.demo.myandroid.adapter.CustomeFrameAdapter;
import com.yk.demo.myandroid.logger.Logger;
import com.yk.demo.myandroid.test.fragment.TestFirstFragment;
import com.yk.demo.myandroid.test.fragment.TestSecondFragment;
import com.yk.demo.myandroid.test.fragment.TestThirdFragment;
import com.yk.demo.myandroid.ui.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

import static com.yk.demo.myandroid.R.id.fragment_alter;

public class HomeFragmentActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.tv_text)
    TextView tv_text;
    @BindView(R.id.view_page)
    ViewPager viewPager;

    private SparseArray<Fragment> fragments = new SparseArray<>();
    /** VIEWS */
    private ArrayList<View> views = new ArrayList<>();
    /** TITLES */
    private ArrayList<String> titles = new ArrayList<>();
    /** FRAGMENTS */
    private ArrayList<Fragment> viewFragment = new ArrayList<>();

    @Override
    protected void intPresenter() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        initFragment();
        initViewPage();
        initListener();

    }

    private void initViewPage(){
        initViewData();
//        CustomerViewPage customerViewPage = new CustomerViewPage(views,titles);
        CustomeFrameAdapter customerViewPage = new CustomeFrameAdapter(fragmentManager,viewFragment,titles);
        viewPager.setAdapter(customerViewPage);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() { // 添加官方切换动画

            private static final float MIN_SCALE = 0.75f;

            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    page.setAlpha(0);
                } else if (position <= 0) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    page.setAlpha(1);
                    page.setTranslationX(0);
                    page.setScaleX(1);
                    page.setScaleY(1);
                } else if (position <= 1) { // (0,1]
                    // Fade the page out.
                    page.setAlpha(1 - position);
                    // Counteract the default slide transition
                    page.setTranslationX(pageWidth * -position);
                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);
                }
            }
        });
    }

    private void initFragment(){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        TestFirstFragment firstFragment = new TestFirstFragment();
        fragments.put(0, firstFragment);
        transaction.add(fragment_alter,firstFragment,"ONE");
        transaction.commit();
    }

    private void initViewData(){
        LayoutInflater inflater = getLayoutInflater();
//        View view1 = inflater.inflate(R.layout.fragment_test_first,null);
//        View view2 = inflater.inflate(R.layout.fragment_test_second,null);
//        View view3 = inflater.inflate(R.layout.fragment_test_third,null);
//        views.add(view1);
//        views.add(view2);
//        views.add(view3);

        Fragment fragment1 = new TestFirstFragment();
        Fragment fragment2 = new TestSecondFragment();
        Fragment fragment3 = new TestThirdFragment();
        viewFragment.add(fragment1);
        viewFragment.add(fragment2);
        viewFragment.add(fragment3);

        titles.add("第一页");
        titles.add("第二页");
        titles.add("第三页");
    }
    private void initListener(){
        tv_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_text){
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.hide(fragments.get(0));
            ft.add(fragment_alter,new TestSecondFragment(),"TWO");
            ft.addToBackStack(null); //添加回退栈
            ft.commit();
            tv_text.setText("点击了fragment的text");
            Logger.d("test call child fragment method",((TestFirstFragment)fragmentManager.findFragmentByTag("ONE")).getTestText());
        }
    }

    class CustomerViewPage extends PagerAdapter{

        private ArrayList<View> mViews;

        private ArrayList<String> mTitles;

        public CustomerViewPage(ArrayList<View> views, ArrayList<String> titles){
            this.mViews = views;
            this.mTitles = titles;
        }

        @Override
        public int getCount() {
            return this.mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }
}
