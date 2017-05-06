package com.example.cheng.videopush;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cheng.videopush.jni.PushNative;
import com.example.cheng.videopush.listener.LiveStateChangeListener;
import com.example.cheng.videopush.pusher.LivePusher;

public class MainActivity extends AppCompatActivity implements LiveStateChangeListener {
    static final String URL = "rtmp://119.23.26.123/live/chengguo";
    private LivePusher livePusher;


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PushNative.CONNECT_FAILED:
                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
//                    Log.d("jason", "连接失败..");
                    break;
                case PushNative.INIT_FAILED:
                    Toast.makeText(MainActivity.this, "初始化失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface);
        //相机图像的预览
        livePusher = new LivePusher(surfaceView.getHolder());
    }

    /**
     * 开始直播
     *
     * @param view
     */
    public void startLive(View view) {
        Button btn = (Button) view;
        if (btn.getText().equals("开始直播")) {
            livePusher.startPush(URL);
            btn.setText("停止直播");
        } else {
            livePusher.stopPush();
            btn.setText("开始直播");
        }
    }

    /**
     * 切换直播
     *
     * @param view
     */
    public void switchCamera(View view) {
        livePusher.switchCamera();
    }

    //改方法执行仍然在子线程中，发送消息到UI主线程
    @Override
    public void onError(int code) {
        handler.sendEmptyMessage(code);
    }

}
