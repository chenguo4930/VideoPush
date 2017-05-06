/*
 * LivePusher    2017-03-27
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.cheng.videopush.pusher;

import android.hardware.Camera;
import android.view.SurfaceHolder;

import com.example.cheng.videopush.jni.PushNative;
import com.example.cheng.videopush.params.AudioParam;
import com.example.cheng.videopush.params.VideoParam;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-27
 */
public class LivePusher implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private VideoPusher videoPusher;
    private AudioPusher audioPusher;
    private PushNative pushNative;

    public LivePusher(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        surfaceHolder.addCallback(this);
        prepare();
    }

    /**
     * 预览准备
     */
    private void prepare() {
        pushNative = new PushNative();

        //实例化视频推流器
        VideoParam videoParam = new VideoParam(640, 480, Camera.CameraInfo.CAMERA_FACING_BACK);
        videoPusher = new VideoPusher(surfaceHolder, videoParam, pushNative);

        AudioParam audioParam = new AudioParam();
        audioPusher = new AudioPusher(audioParam, pushNative);
    }

    /**
     * 切换摄像头
     */
    public void switchCamera() {
        videoPusher.switchCamera();
    }

    /**
     * 开始推流
     */
    public void startPush(String url) {
        videoPusher.startPush();
        audioPusher.startPush();
        pushNative.startPush(url);
    }

    /**
     * 停止推流
     */
    public void stopPush() {
        videoPusher.stopPush();
        audioPusher.stopPush();
        pushNative.stopPush();
    }

    /**
     * 释放资源
     */
    private void release() {
        videoPusher.release();
        audioPusher.release();
        pushNative.release();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPush();
        release();
    }
}