package com.futureinternet.cysmile.lte;

/**
 * Created by cysmile on 2015/6/18.
 */
public class Data {

    private static String timeTitle = ""; //文件开头时间
    private static String timeLine = ""; //文件每一行时间
    private static String getTime = ""; //当前时间
    private static String kind = ""; //测试类型
    private static String rxspeed = "0.00"; //下载速度
    private static String txspeed = "0.00"; //上传速度
    private static String totalspeed = "0.00"; //总速度
    private static String gpsspeed = "0.00"; //GPS速度
    private static int FLAG = 1;
    private static int viewFlag = 0;

    public void setTimeTitle(String timeTitle) {
        this.timeTitle = timeTitle;
    }

    public static String getTimeTitle() {
        return timeTitle;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public static String getTimeLine() {
        return timeLine;
    }
    public void setgetTime(String getTime) {
        this.getTime = getTime;
    }

    public static String getGetTime() {
        return getTime;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public static String getKind() {
        return kind;
    }


    public void setRxspeed(String rxspeed) {
        this.rxspeed = rxspeed;
    }

    public static String getRxspeed() {
        return rxspeed;
    }

    public void setTxspeed(String txspeed) {
        this.txspeed = txspeed;
    }

    public static String getTxspeed() {
        return txspeed;
    }

    public void setTotalspeed(String totalspeed) {
        this.totalspeed = totalspeed;
    }

    public static String getTotalspeed() {
        return totalspeed;
    }

    public void setGpsspeed(String gpsspeed) {
        this.gpsspeed = gpsspeed;
    }

    public static String getGpsspeed() {
        return gpsspeed;
    }

    public void setViewFlag(int viewFlag) {
        this.viewFlag = viewFlag;
    }

    public static int getViewFlag() {
        return viewFlag;
    }


}
