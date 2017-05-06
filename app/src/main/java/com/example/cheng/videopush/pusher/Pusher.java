/*
 * Pusher    2017-03-27
 * Copyright(c) 2017 Chengguo Co.Ltd. All right reserved.
 *
 */
package com.example.cheng.videopush.pusher;

/**
 * class description here
 *
 * @author cheng
 * @version 1.0.0
 * @since 2017-03-27
 */
public abstract class Pusher {
    public abstract void startPush();

    public abstract void stopPush();

    public abstract void release();
}