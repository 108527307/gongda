package com.example.administrator.gongda.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.gongda.R;
import com.example.administrator.gongda.Utils.Cachecontrol;
import com.example.administrator.gongda.Utils.WeekUtil;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


public class EntryActivity extends Activity
{

    @Bind(R.id.iv_entry)
    ImageView mSplashImage;

    private static final int ANIMATION_TIME = 2000;

    private static final float SCALE_END = 1.13F;

    private static final int[] IMAGES = {
                                R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
                       };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);

        Random random = new Random(SystemClock.elapsedRealtime());
        mSplashImage.setImageResource(IMAGES[random.nextInt(IMAGES.length)]);

        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {

                    @Override
                    public void call(Long aLong) {

                        startAnim();
                    }
                });
    }

    private void startAnim()
    {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mSplashImage, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mSplashImage, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation) {

                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                SharedPreferences sharedPreferences=EntryActivity.this.getSharedPreferences("appinfo",EntryActivity.MODE_PRIVATE);
                Boolean isFirst=sharedPreferences.getBoolean("isFirst",true);
                if(isFirst){
                startActivity(new Intent(EntryActivity.this, LoginActivity.class));
                    EntryActivity.this.finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//activity转场动画
                }
                else {
                    if(!new Cachecontrol().UpdateWeek(EntryActivity.this, WeekUtil.getCurrentDate())){
                        new Cachecontrol().finishAllInfo(EntryActivity.this);
                        Toast.makeText(EntryActivity.this,"个人信息填写错误或失效，请重新登录",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(EntryActivity.this, LoginActivity.class));
                    EntryActivity.this.finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//activity转场动画
                     }else {
                    startActivity(new Intent(EntryActivity.this, MainActivity.class));
                    EntryActivity.this.finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);//activity转场动画
                }
                }

            }
        });
    }

}
