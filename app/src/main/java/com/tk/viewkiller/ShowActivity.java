package com.tk.viewkiller;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.tk.viewkiller.partical.ParticalHelper;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton refresh;
    private ImageView image;

    private boolean lock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        refresh = (ImageButton) findViewById(R.id.refresh);
        image = (ImageView) findViewById(R.id.image);
        refresh.setOnClickListener(this);
        image.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refresh:
                if (lock) {
                    return;
                }
                image.setVisibility(View.VISIBLE);
                break;
            case R.id.image:
                if (lock) {
                    return;
                }
                lock = true;
                int flag = getIntent().getIntExtra("flag", 0);
                switch (flag) {
                    case 0:
                        ParticalHelper.with(this, image, false, new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                lock = false;
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                                lock = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        break;
                    case 1:

                        break;
                    default:
                        break;
                }
                break;
        }

    }

}
