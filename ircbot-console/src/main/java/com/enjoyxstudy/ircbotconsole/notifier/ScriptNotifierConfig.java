package com.enjoyxstudy.ircbotconsole.notifier;

import java.io.Serializable;

/**
 * スクリプトによる通知処理の設定内容を表すクラスです。
 *
 * @author onozaty
 */
public class ScriptNotifierConfig implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** スクリプトの種別：周期です。 */
    public static final int TYPE_CYCLIC = 0;

    /** スクリプトの種別：毎日です。 */
    public static final int TYPE_DAILY = 1;

    /** 種別です。 */
    private int type;

    /** 周期(分)です。 */
    private int cyclicSpan;

    /** 毎日(時)です。 */
    private int dailyHour;

    /** 毎日(分)です。 */
    private int dailyMinute;

    /** スクリプト内容です。 */
    private String scriptText;

    /**
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return cyclicSpan
     */
    public int getCyclicSpan() {
        return cyclicSpan;
    }

    /**
     * @param cyclicSpan cyclicSpan
     */
    public void setCyclicSpan(int cyclicSpan) {
        this.cyclicSpan = cyclicSpan;
    }

    /**
     * @return dailyHour
     */
    public int getDailyHour() {
        return dailyHour;
    }

    /**
     * @param dailyHour dailyHour
     */
    public void setDailyHour(int dailyHour) {
        this.dailyHour = dailyHour;
    }

    /**
     * @return dailyMinute
     */
    public int getDailyMinute() {
        return dailyMinute;
    }

    /**
     * @param dailyMinute dailyMinute
     */
    public void setDailyMinute(int dailyMinute) {
        this.dailyMinute = dailyMinute;
    }

    /**
     * @return scriptText
     */
    public String getScriptText() {
        return scriptText;
    }

    /**
     * @param scriptText scriptText
     */
    public void setScriptText(String scriptText) {
        this.scriptText = scriptText;
    }

}
