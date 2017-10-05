package com.riven.lee.rivennews.ui.splash;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.riven.lee.rivennews.R;
import com.riven.lee.rivennews.base.SimpleActivity;
import com.riven.lee.rivennews.ui.main.MainActivity;
import com.riven.lee.rivennews.widget.CirclePageIndicator;
import com.riven.lee.rivennews.widget.ExtendedViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by rivenLee
 * 2017/9/28.
 * O -))))>
 */

public class SplashActivity extends SimpleActivity implements ViewPager.OnPageChangeListener {


    @BindView(R.id.vp_video)
    ExtendedViewPager vpVideo;
    @BindView(R.id.view_pager_indicator)
    CirclePageIndicator viewPagerIndicator;
    @BindView(R.id.tv_enter)
    TextView tvEnter;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vpVideo.setAdapter(viewPagerAdapter);
        vpVideo.setOffscreenPageLimit(4);
        viewPagerIndicator.setViewPager(vpVideo);
        vpVideo.addOnPageChangeListener(this);
    }


//    @OnClick(R.id.tv_enter)
//    public void onViewClicked() {
//        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        finish();
//    }

    public void next(int position) {
        int i = this.vpVideo.getCurrentItem();
        if (position == i) {
            position += 1;

            this.vpVideo.setCurrentItem(position, true);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((VideoItemFragment) (viewPagerAdapter.getItem(position))).play();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    @OnClick(R.id.tv_enter)
    public void onClick() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private final int[] videoRes; //视频资源
        private final int[] imgRes; //图片资源

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.videoRes = new int[]{R.raw.splash_1, R.raw.splash_2, R.raw.splash_3, R.raw.splash_4};
            this.imgRes = new int[]{R.drawable.slogan_1, R.drawable.slogan_2, R.drawable.slogan_3, R.drawable.slogan_4};
        }

        @Override
        public Fragment getItem(int position) {
            if (position < getCount()) {
                return VideoItemFragment.newInstance(position, videoRes[position], imgRes[position]);
            }
            throw new RuntimeException("Position out of range. Adapter has " + getCount() + " items");
        }

        @Override
        public int getCount() {
            return this.videoRes.length;
        }
    }

}
