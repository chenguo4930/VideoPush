/*
 * PushNative    2017-03-27
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.cheng.videopush.jni;

import com.example.cheng.videopush.listener.LiveStateChangeListener;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-27
 */
public class PushNative {

    public static final int CONNECT_FAILED = 101;
    public static final int INIT_FAILED = 102;
    LiveStateChangeListener liveStateChangeListener;

    public void setLiveStateChangeListener(LiveStateChangeListener liveStateChangeListener) {
        this.liveStateChangeListener = liveStateChangeListener;
    }

    public void removeLiveStateChangeListener(){
        this.liveStateChangeListener = null;
    }

    /**
     * 接收Native层抛出的错误
     * @param code
     */
    public void throwNativeError(int code){
        if(liveStateChangeListener != null){
            liveStateChangeListener.onError(code);
        }
    }

    public native void startPush(String url);

    public native void stopPush();

    public native void release();

    /**
     * 设置视频参数
     * @param width
     * @param height
     * @param bitrate
     * @param fps
     */
    public native void setVideoOptions(int width, int height, int bitrate, int fps);

    /**
     * 设置音频参数
     * @param sampleRateInHz
     * @param channel
     */
    public native void setAudioOptions(int sampleRateInHz, int channel);

    /**
     * 发送视频数据
     * @param data
     */
    public native void fireVideo(byte[] data);

    /**
     * 发送音频数据
     * @param data
     * @param len
     */
    public native void fireAudio(byte[] data, int len);

    static {
        System.loadLibrary("livelib");
    }

}