package com.enjoyxstudy.ircbotconsole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.enjoyxstudy.ircbotconsole.notifier.ScriptNotifierConfig;

/**
 * @author onozaty
 */
public class Config implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** ニックネームです。 */
    private String nick = "ircbot";

    /** IRCサーバ名です。 */
    private String serverName;

    /** IRCサーバのポート番号です。 */
    private int serverPort = 6667;

    /** IRCサーバのパスワードです。 */
    private String serverPassword;

    /** JOINするチャンネル名のリストです。 */
    private ArrayList<String> channels = new ArrayList<String>();

    /** IRCサーバのエンコーディングです。 */
    private String encoding = "UTF-8";

    /** ログ出力ディレクトリです。 */
    private String logDirectory = "./irclog";

    /** 作業ディレクトリです。 */
    private String workDirectory = "./work";

    /** RSS通知を行うチャンネル名とRSSフィードリストのMapです。 */
    private HashMap<String, ArrayList<String>> rssNotifierConfig = new HashMap<String, ArrayList<String>>();

    /** チャンネル名とスクリプト通知設定のMapです。 */
    private HashMap<String, ArrayList<ScriptNotifierConfig>> scriptNotifierConfig = new HashMap<String, ArrayList<ScriptNotifierConfig>>();

    /** チャンネル名とメッセージ受信スクリプトのMapです。 */
    private HashMap<String, ArrayList<String>> scriptProcessorConfig = new HashMap<String, ArrayList<String>>();

    /** HTTPでのメッセージ送信を許可するかを表すフラグです。 */
    private boolean isAllowHttpMessage;

    /**
     * コンストラクタです。
     */
    public Config() {
        //
    }

    /**
     * @return nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @param serverName serverName
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * @return serverPort
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * @param serverPort serverPort
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    /**
     * @return serverPassword
     */
    public String getServerPassword() {
        return serverPassword;
    }

    /**
     * @param serverPassword serverPassword
     */
    public void setServerPassword(String serverPassword) {
        this.serverPassword = serverPassword;
    }

    /**
     * @return channels
     */
    public ArrayList<String> getChannels() {
        return channels;
    }

    /**
     * @param channels channels
     */
    public void setChannels(ArrayList<String> channels) {
        this.channels = channels;
    }

    /**
     * @return encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * @param encoding encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return logDirectory
     */
    public String getLogDirectory() {
        return logDirectory;
    }

    /**
     * @param logDirectory logDirectory
     */
    public void setLogDirectory(String logDirectory) {
        this.logDirectory = logDirectory;
    }

    /**
     * @return workDirectory
     */
    public String getWorkDirectory() {
        return workDirectory;
    }

    /**
     * @param workDirectory workDirectory
     */
    public void setWorkDirectory(String workDirectory) {
        this.workDirectory = workDirectory;
    }

    /**
     * @return rssNotifierConfig
     */
    public HashMap<String, ArrayList<String>> getRssNotifierConfig() {
        return rssNotifierConfig;
    }

    /**
     * @param rssNotifierConfig rssNotifierConfig
     */
    public void setRssNotifierConfig(
            HashMap<String, ArrayList<String>> rssNotifierConfig) {
        this.rssNotifierConfig = rssNotifierConfig;
    }

    /**
     * @return scriptNotifierConfig
     */
    public HashMap<String, ArrayList<ScriptNotifierConfig>> getScriptNotifierConfig() {
        return scriptNotifierConfig;
    }

    /**
     * @param scriptNotifierConfig scriptNotifierConfig
     */
    public void setScriptNotifierConfig(
            HashMap<String, ArrayList<ScriptNotifierConfig>> scriptNotifierConfig) {
        this.scriptNotifierConfig = scriptNotifierConfig;
    }

    /**
     * @return scriptProcessorConfig
     */
    public HashMap<String, ArrayList<String>> getScriptProcessorConfig() {
        return scriptProcessorConfig;
    }

    /**
     * @param scriptProcessorConfig scriptProcessorConfig
     */
    public void setScriptProcessorConfig(
            HashMap<String, ArrayList<String>> scriptProcessorConfig) {
        this.scriptProcessorConfig = scriptProcessorConfig;
    }

    /**
     * @return isAllowHttpMessage
     */
    public boolean isAllowHttpMessage() {
        return isAllowHttpMessage;
    }

    /**
     * @param isAllowHttpMessage isAllowHttpMessage
     */
    public void setAllowHttpMessage(boolean isAllowHttpMessage) {
        this.isAllowHttpMessage = isAllowHttpMessage;
    }

}
