package com.enjoyxstudy.ircbotconsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import com.enjoyxstudy.ircbotconsole.command.ScriptProcessor;
import com.enjoyxstudy.ircbotconsole.notifier.CyclicScheduler;
import com.enjoyxstudy.ircbotconsole.notifier.ScriptNotifier;
import com.enjoyxstudy.ircbotconsole.notifier.ScriptNotifierConfig;

/**
 * DWR経由でIrcBotServerにアクセスするためのアダプタです。
 *
 * @author onozaty
 *
 */
public class IrcBotServerAdapter {

    /**
     * Configを返却します。
     *
     * @return config
     */
    public Config getConfig() {
        return IrcBotServer.getInstance().getConfig();
    }

    /**
     * 接続情報を更新し、再接続を行います。
     *
     * @param serverName
     * @param serverPort
     * @param serverPassword
     * @param nick
     * @param encoding
     *
     * @throws IrcException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NickAlreadyInUseException
     */
    public void updateConnetInfo(String serverName, int serverPort,
            String serverPassword, String nick, String encoding)
            throws NickAlreadyInUseException, NoSuchAlgorithmException,
            IOException, IrcException {

        IrcBotServer.getInstance().updateConnetInfo(serverName, serverPort,
                serverPassword, nick, encoding);
    }

    /**
     * チャンネルを追加します。
     *
     * @param channel
     * @throws FileNotFoundException
     */
    public void addChannel(String channel) throws FileNotFoundException {

        IrcBotServer.getInstance().addChannel(channel);
    }

    /**
     * チャンネルを削除します。
     *
     * @param channel
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public void removeChannel(String channel) throws NoSuchAlgorithmException,
            InterruptedException, FileNotFoundException {

        IrcBotServer.getInstance().removeChannel(channel);
    }

    /**
     * RssNotifierのフィード一覧を取得します。
     *
     * @param channel チャンネル名
     * @return RssNotiferのフィード一覧
     */
    public ArrayList<String> getRssNotifierFeeds(String channel) {

        return IrcBotServer.getInstance().getConfig().getRssNotifierConfig()
                .get(channel);
    }

    /**
     * RssNotifierを追加します。
     *
     * @param channel チャンネル名
     * @param feedUrl RSSフィード
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public void addRssNotifier(String channel, String feedUrl)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().addRssNotifier(channel, feedUrl);
    }

    /**
     * RssNotifierを削除します。
     *
     * @param channel チャンネル名
     * @param feedUrl RSSフィード
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public void removeRssNotifier(String channel, String feedUrl)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().removeRssNotifier(channel, feedUrl);
    }

    /**
     * ScriptNotifierの設定一覧を取得します。
     *
     * @param channel チャンネル名
     * @return ScriptNotifierの設定一覧
     */
    public ArrayList<ScriptNotifierConfig> getScriptNotifierConfig(
            String channel) {

        return IrcBotServer.getInstance().getConfig().getScriptNotifierConfig()
                .get(channel);
    }

    /**
     * ScriptNotifierを更新します。
     *
     * @param channel チャンネル名
     * @param scriptNotifierCinfig スクリプト通知の設定
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @throws FileNotFoundException
     */
    public synchronized void updateScriptNotifier(String channel,
            ArrayList<ScriptNotifierConfig> scriptNotifierCinfig)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().updateScriptNotifier(channel,
                scriptNotifierCinfig);
    }

    /**
     * ScriptNotifierをテスト実行します。
     *
     * @param channel チャンネル名
     * @param scriptText スクリプト
     * @throws Exception
     */
    public void testScriptNotifier(String channel, String scriptText)
            throws Exception {

        ScriptNotifier scriptNotifier = new ScriptNotifier(channel,
                new CyclicScheduler(0), scriptText);
        scriptNotifier.execute(IrcBotServer.getInstance().ircBot);
    }

    /**
     * メッセージ受信スクリプトの設定一覧を取得します。
     *
     * @param channel チャンネル名
     * @return メッセージ受信スクリプト
     */
    public ArrayList<String> getScriptProcessorConfig(String channel) {

        return IrcBotServer.getInstance().getConfig()
                .getScriptProcessorConfig().get(channel);
    }

    /**
     * メッセージ受信スクリプトを更新します。
     *
     * @param channel チャンネル名
     * @param scriptProcessorCinfig スクリプト通知の設定
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @throws FileNotFoundException
     */
    public void updateScriptProcessor(String channel,
            ArrayList<String> scriptProcessorCinfig)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().updateScriptProcessor(channel,
                scriptProcessorCinfig);
    }

    /**
     * メッセージ受信スクリプトをテスト実行します。
     *
     * @param channel チャンネル名
     * @param scriptText スクリプト
     * @param receiveMessage 受信メッセージ
     */
    public void testScriptProcessor(String channel, String scriptText,
            String receiveMessage) {

        ScriptProcessor scriptProcessor = new ScriptProcessor(scriptText);
        scriptProcessor.onMessage(IrcBotServer.getInstance().ircBot, channel,
                "sender", "login", "hostname", receiveMessage);
    }

    /**
     * 接続中かどうかを返却します。
     *
     * @return 接続中の場合true
     */
    public boolean isConnected() {
        return IrcBotServer.getInstance().isConnected();
    }

    /**
     * HTTPでのメッセージ送信を許可するかを返却します。
     *
     * @return isAllowHttpMessage
     */
    public boolean isAllowHttpMessage() {
        return IrcBotServer.getInstance().getConfig().isAllowHttpMessage();
    }

    /**
     * HTTPでのメッセージ送信を許可を設定します。
     *
     * @param isAllowHttpMessage isAllowHttpMessage
     */
    public void setAllowHttpMessage(boolean isAllowHttpMessage) {
        IrcBotServer.getInstance().getConfig().setAllowHttpMessage(
                isAllowHttpMessage);
    }
}
