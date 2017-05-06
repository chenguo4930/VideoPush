/*
 * VideoPysher    2017-03-27
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.cheng.videopush.pusher;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.cheng.videopush.jni.PushNative;
import com.example.cheng.videopush.params.VideoParam;

;import java.io.IOException;


/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-27
 */
public class VideoPusher extends Pusher implements SurfaceHolder.Callback, android.hardware.Camera.PreviewCallback {

    private SurfaceHolder surfaceHolder;
    private VideoParam videoParam;
    private PushNative pushNative;
    private Camera mCamera;
    private byte[] buffers;
    private boolean isPushing = false;

    public VideoPusher(SurfaceHolder surfaceHolder, VideoParam videoParam, PushNative pushNatvie) {
        this.surfaceHolder = surfaceHolder;
        this.videoParam = videoParam;
        this.pushNative = pushNatvie;
        surfaceHolder.addCallback(this);
    }

    @Override
    public void startPush() {
        //设置视频参数
        pushNative.setVideoOptions(videoParam.getWitdh(),
                videoParam.getHeight(),
                videoParam.getBitrate(),
                videoParam.getFps());
        isPushing = true;
    }

    @Override
    public void stopPush() {
        isPushing = false;
    }

    @Override
    public void release() {
        stopPreview();
    }

    /**
     * 停止预览
     */
    private void stopPreview() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, android.hardware.Camera camera) {
        if (mCamera != null) {
            mCamera.addCallbackBuffer(buffers);
        }
        if (isPushing) {
            //回调函数中获取图像数据，然后给Native代码编码
            pushNative.fireVideo(data);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopPreview();
    }

    /**
     * 切换摄像头
     */
    public void switchCamera(){
        if (videoParam.getCameraId() == Camera.CameraInfo.CAMERA_FACING_BACK){
            videoParam.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        }else {
            videoParam.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        //重新预览
        stopPreview();
        startPreview();
    }

    /**
     * 开始预览
     */
    private void startPreview() {
        try {
            Log.e("seven","------------------开始预览--1-");
            //SurfaceView初始化完成，开始相机预览
            mCamera = Camera.open(videoParam.getCameraId());
            Camera.Parameters parameters = mCamera.getParameters();
            //设置相机参数
            parameters.setPreviewFormat(ImageFormat.NV21);//YUV预览图像的像素格式
            parameters.setPreviewSize(videoParam.getWitdh(), videoParam.getHeight());//预览画面宽高
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(surfaceHolder);
            //获取预览图像数据
            buffers = new byte[videoParam.getWitdh() * videoParam.getHeight() * 4];
            mCamera.addCallbackBuffer(buffers);
            mCamera.setPreviewCallbackWithBuffer(this);
            mCamera.startPreview();
            Log.e("seven","------------------开始预览--2-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}