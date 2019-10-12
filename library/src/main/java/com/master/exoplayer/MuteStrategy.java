package com.master.exoplayer;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Pankaj Sharma
 * MuteStrategy used with masterExoPlayerHelper used for playing video inside recyclerview
 * We can set
 * MuteStrategy.ALL - When this set single mute on single item will mute all other instances, just like instagram
 * MuteStrategy.INDIVIDUAL - When this set User have to manage individual mute status as per items in recyclerview.
 */
public class MuteStrategy {

    @IntDef({MuteStrategy.ALL, MuteStrategy.INDIVIDUAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Values {
    }

    public static final int ALL = 1;
    public static final int INDIVIDUAL = 2;
}