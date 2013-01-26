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

    /** BASIC認証のIDです。 */
    private String basicAuthId;

    /** BASIC認証のパスワードです。 */
    private String basicAuthPassword;

    /** RSSのメッセージ書式のスクリプトです。 */
    private String messageFormatScript;

    /**
     * @return feedUrl
     */
    public String getFeedUrl() {
        return feedUrl;
    }

    /**
     * @param feedUrl
     *            feedUrl
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
     * @param cycleMinute
     *            cycleMinute
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
     * @return basicAuthId
     */
    public String getBasicAuthId() {
        return basicAuthId;
    }

    /**
     * @param basicAuthId
     */
    public void setBasicAuthId(String basicAuthId) {
        this.basicAuthId = basicAuthId;
    }

    /**
     * @return basicAuthPassword
     */
    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    /**
     * @param basicAuthPassword
     */
    public void setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
    }

    /**
     * @param messageFormatScript
     *            messageFormatScript
     */
    public void setMessageFormatScript(String messageFormatScript) {
        this.messageFormatScript = messageFormatScript;
    }
}
