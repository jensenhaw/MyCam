package com.hb.mycam;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ThermalExpert.ThermalExpert;


public class MainActivity extends AppCompatActivity{
    ThermalExpert te;
    ImageView mImageView;
    Button mCalibrationButton;
    Button mColorButton;
    Button mPlayButton;
    int mColorMode = 0;
    boolean mIsPlay = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.image_preview);
        mCalibrationButton = (Button) findViewById(R.id.calibration_button);
        mCalibrationButton.setOnClickListener(listener );
        mColorButton = (Button) findViewById(R.id.color_button);
        mColorButton.setOnClickListener(listener );
        mPlayButton = (Button) findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(listener );
        te = new ThermalExpert(thermalExpertListener, mImageView);
        te.Initial(getApplicationContext());

    }

    private ThermalExpert.ThermalExpertListener thermalExpertListener = new ThermalExpert.ThermalExpertListener() {
        @Override
        public void onFlashReadFinished() {
            mImageView.setScaleType(ImageView.ScaleType.CENTER);
            te.StartReceive();

        }

        @Override
        public void onUsbConnected() {

        }

        @Override
        public void onUsbDisconnected() {

        }

        @Override
        public void onOneFrameFinished() {
            mImageView.setBackground(new BitmapDrawable(te.GetImage()));
//te.GetData();

        }

        @Override
        public void onCalibrationFinished() {
            mCalibrationButton.setEnabled(true);
        }

        @Override
        public void onRecogNumber() {

        }

    };
    Button.OnClickListener listener = new Button.OnClickListener() {//创建监听对象
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.calibration_button:
                    mCalibrationButton.setEnabled(false);
                    te.CalibrationImage();
                    break;
                case R.id.color_button:
                    Toast.makeText(getApplicationContext(), "默认Toast样式",
                            Toast.LENGTH_SHORT).show();


                    te.SetColorMap((++mColorMode) % 4);
                    break;
                case R.id.play_button:
                    if (mIsPlay) {
                        te.StopReceive();
                        mIsPlay = false;
                    } else {
                        te.StartReceive();
                        mIsPlay = true;
                    }
                    break;
            }

        }
    };
    public boolean onTouchEvent(MotionEvent event) {
        te.GetPointTemperature((int) event.getX(), (int) event.getY());
        return super.onTouchEvent(event);
    }



}

