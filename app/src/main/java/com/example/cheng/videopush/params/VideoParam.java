package com.example.cheng.videopush.params;

/**
 * 视频参数
 * Created by cheng on 2017/3/27.
 */

public class VideoParam {
    private int witdh;
    private int height;
    //码率480kbps
    private int bitrate = 480000;
    //帧内默认25帧/s
    private int fps = 25;
    private int cameraId;

    public VideoParam(int witdh, int height, int cameraId) {
        super();
        this.witdh = witdh;
        this.height = height;
        this.cameraId = cameraId;
    }

    public int getWitdh() {
        return witdh;
    }

    public void setWitdh(int witdh) {
        this.witdh = witdh;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }
}
