package com.example.liliyou.mycircle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    // Views
    ImageView ivDrawable;
    Handler myHandler;
    TextView textView01;

    TextView btStyle0;
    TextView btStyle1;
    TextView btStyle2;
    int ramdrange = 49;
    //    Button btStyle3;
//    Button btStyle4;
    int mothod = 0;//
    boolean quack = false;
    private SharedPreferences settings;
    private static final String data = "DATA";
    private static final String nameField = "NAME";
    //呼叫案例方法
    CircularProgressDrawable drawable;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentAnimation != null) {
                currentAnimation.cancel();
            }
            Random ran = new Random();
            String s;
            switch (v.getId()) {
                case R.id.bt_style_0:
                    quack = true;
//                    if(mothod==0){
//                        s=String.valueOf(ran.nextInt(ramdrange));
//                        textView01.setText(s);
//                    }else {
//                        s=String.valueOf(ran.nextInt(2));
//                        if(ran.nextInt(2)==0)
//                        {
//                            textView01.setText("O");
//                        }
//                        else{
//
//                            textView01.setText("X");
//                        }
//                    }

//                    textView01.setText(s);
                    break;
                case R.id.bt_style_1:
                    mothod = 1;
                    quack = false;
                    textView01.setText("X");
                    break;
                case R.id.bt_style_2:
                    mothod = 0;
                    quack = false;
                    textView01.setText("0");
                    break;
                case R.id.iv_drawable:
                    if (quack) {

                        if (mothod == 0) {
                            s = String.valueOf(ran.nextInt(ramdrange));
                            textView01.setText(s);
                        } else {
                            s = String.valueOf(ran.nextInt(2));
                            if (ran.nextInt(2) == 0) {
                                textView01.setText("O");
                            } else {

                                textView01.setText("X");
                            }
                        }

                        currentAnimation = preparePulseAnimation();
                        currentAnimation.start();
                    } else {
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(600);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }

                                for (int i = 0; i < 18; i++) {

                                    try {
                                        Thread.sleep(100);
                                        Message message = new Message();
                                        message.what = 1;
                                        myHandler.sendMessage(message);

                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }

                            }
                        }).start();
                        currentAnimation = prepare5PulseAnimation();
                        currentAnimation.start();
                    }
                    break;
                default:
                    break;

            }

        }
    };

    Animator currentAnimation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        SeekBar sk = (SeekBar) findViewById(R.id.seekBar);
        ivDrawable = (ImageView) findViewById(R.id.iv_drawable);
        textView01 = (TextView) findViewById(R.id.textView);
        btStyle0 = (TextView) findViewById(R.id.bt_style_0);
        btStyle1 = (TextView) findViewById(R.id.bt_style_1);
        btStyle2 = (TextView) findViewById(R.id.bt_style_2);
        //取得資料
//        String PREFS_NAME = "test.this.activity";//一個標籤 可以設定為代表此APP的String Tag


        settings = getSharedPreferences(data, 0);
        ramdrange = settings.getInt(nameField, 49);
        sk.setProgress(ramdrange);
        textView01.setText(String.valueOf(ramdrange));
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                quack = false;
                if (progress != 0) {
                    ramdrange = progress;
                } else {
                    ramdrange = 1;
                }
                mothod = 0;
                textView01.setText(String.valueOf(progress));
                //存入資料

                settings = getSharedPreferences(data, 0);
                settings.edit()
                        .putInt(nameField, progress)
                        .commit();
            }
        });
        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:

                        Random ran = new Random();
                        String s;
                        if (mothod == 0) {
                            s = String.valueOf(ran.nextInt(ramdrange));
                            textView01.setText(s);
                        } else {
                            s = String.valueOf(ran.nextInt(2));
                            if (ran.nextInt(2) == 0) {
                                textView01.setText("O");
                            } else {

                                textView01.setText("X");
                            }
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();



        drawable = new CircularProgressDrawable.Builder()
                .setRingWidth(getResources().getDimensionPixelSize(R.dimen.drawable_ring_size))
                .setOutlineColor(getResources().getColor(android.R.color.darker_gray))
                .setRingColor(getResources().getColor(android.R.color.holo_green_light))
                .setCenterColor(getResources().getColor(android.R.color.holo_blue_dark))
                .create();
        ivDrawable.setImageDrawable(drawable);
        hookUpListeners();
    }

    private void hookUpListeners() {
        ivDrawable.setOnClickListener(listener);
        btStyle0.setOnClickListener(listener);
        btStyle1.setOnClickListener(listener);
        btStyle2.setOnClickListener(listener);

    }

    /**
     * This animation was intended to keep a pressed state of the Drawable
     *
     * @return Animation
     */
    private Animator preparePressedAnimation() {
        Animator animation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.65f);
        animation.setDuration(120);
        return animation;
    }

    private Animator prepare5PulseAnimation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 1f, 0f);
        progressAnimation.setDuration(1200);
        progressAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0.75f, 0.60f);
        innerCircleAnimation.setDuration(600);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation1 = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0.60f, 0.80f);
        innerCircleAnimation1.setDuration(600);
        innerCircleAnimation1.setStartDelay(600);
        innerCircleAnimation1.setInterpolator(new AnticipateInterpolator());

        ObjectAnimator invertedProgress = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0f, 1f);
        invertedProgress.setDuration(1200);
        invertedProgress.setStartDelay(2000);
        invertedProgress.setInterpolator(new OvershootInterpolator());

        Animator invertedCircle = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0.80f, 0.75f);
        invertedCircle.setDuration(1200);
        invertedCircle.setStartDelay(1200);
        invertedCircle.setInterpolator(new OvershootInterpolator());

        animation.playTogether(progressAnimation, innerCircleAnimation, innerCircleAnimation1, invertedProgress, invertedCircle);
        return animation;
    }

    /**
     * This animation will make a pulse effect to the inner circle
     *
     * @return Animation
     */
    private Animator preparePulseAnimation() {
        AnimatorSet animation = new AnimatorSet();

        Animator firstBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                drawable.getCircleScale(), 0.88f);
        firstBounce.setDuration(300);
        firstBounce.setInterpolator(new CycleInterpolator(1));
        Animator secondBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.83f);
        secondBounce.setDuration(300);
        secondBounce.setInterpolator(new CycleInterpolator(1));
        Animator thirdBounce = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY,
                0.75f, 0.80f);
        thirdBounce.setDuration(300);
        thirdBounce.setInterpolator(new CycleInterpolator(1));

        animation.playSequentially(firstBounce, secondBounce, thirdBounce);
        return animation;
    }

    /**
     * Style 1 animation will simulate a indeterminate loading while taking advantage of the inner
     * circle to provide a progress sense
     *
     * @return Animation
     */
    private Animator prepareStyle1Animation() {
        AnimatorSet animation = new AnimatorSet();

        final Animator indeterminateAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0, 3600);
        indeterminateAnimation.setDuration(3600);

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        innerCircleAnimation.setDuration(3600);
        innerCircleAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                drawable.setIndeterminate(true);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                indeterminateAnimation.end();
                drawable.setIndeterminate(false);
                drawable.setProgress(0);
            }
        });

        animation.playTogether(innerCircleAnimation, indeterminateAnimation);
        return animation;
    }

    /**
     * Style 2 animation will fill the outer ring while applying a color effect from red to green
     *
     * @return Animation
     */
    private Animator prepareStyle2Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY,
                0f, 0.5f);
        progressAnimation.setDuration(3600);
        progressAnimation.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(drawable, CircularProgressDrawable.RING_COLOR_PROPERTY,
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_green_light));
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setDuration(3600);

        animation.playTogether(progressAnimation, colorAnimator);
        return animation;
    }

    /**
     * Style 3 animation will turn a 3/4 animation with Anticipate/Overshoot interpolation to a
     * blank waiting - like state, wait for 2 seconds then return to the original state
     *
     * @return Animation
     */
    private Animator prepareStyle3Animation() {
        AnimatorSet animation = new AnimatorSet();

        ObjectAnimator progressAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 1f, 0f);
        progressAnimation.setDuration(1200);
        progressAnimation.setInterpolator(new AnticipateInterpolator());

        Animator innerCircleAnimation = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0.75f, 0f);
        innerCircleAnimation.setDuration(1200);
        innerCircleAnimation.setInterpolator(new AnticipateInterpolator());

        ObjectAnimator invertedProgress = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.PROGRESS_PROPERTY, 0f, 1f);
        invertedProgress.setDuration(1200);
        invertedProgress.setStartDelay(3200);
        invertedProgress.setInterpolator(new OvershootInterpolator());

        Animator invertedCircle = ObjectAnimator.ofFloat(drawable, CircularProgressDrawable.CIRCLE_SCALE_PROPERTY, 0f, 0.75f);
        invertedCircle.setDuration(1200);
        invertedCircle.setStartDelay(3200);
        invertedCircle.setInterpolator(new OvershootInterpolator());

        animation.playTogether(progressAnimation, innerCircleAnimation, invertedProgress, invertedCircle);
        return animation;
    }

    class myThread implements Runnable {
        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
            for (int i = 0; i < 5; i++) {
                Message message = new Message();
                message.what = 1;

                myHandler.sendMessage(message);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
//        }
    }
}
