package com.enjoyxstudy.ircbotconsole.notifier;

import java.io.Serializable;

/**
 * RSS通知の設定内容を表すクラスです。
 *
 * @author onozaty
 */
public class RssNotifierConfig implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** RSSフィードのURLです。 */
    private String feedUrl;

    /** 周期(分)です。 */
    private int cycleMinute;

    /** RSSのメッセージ書式のスクリプトです。 */
    private String messageFormatScript;

    /**
     * @return feedUrl
     */
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     * @param feedUrl feedUrl
     */
    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    /**
     * @return cycleMinute
     */
    public int getCycleMinute() {
        return cycleMinute;
    }

    /**
     * @param cycleMinute cycleMinute
     */
    public void setCycleMinute(int cycleMinute) {
        this.cycleMinute = cycleMinute;
    }

    /**
     * @return messageFormatScript
     */
    public String getMessageFormatScript() {
        return messageFormatScript;
    }

    /**
     * @param messageFormatScript messageFormatScript
     */
    public void setMessageFormatScript(String messageFormatScript) {
        this.messageFormatScript = messageFormatScript;
    }
}
