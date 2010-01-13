package com.enjoyxstudy.ircbotconsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

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
     * @param target チャンネル名
     * @return RssNotiferのフィード一覧
     */
    public synchronized ArrayList<String> getRssNotifierFeeds(String target) {

        return IrcBotServer.getInstance().getConfig().getRssNotifierConfig()
                .get(target);
    }

    /**
     * RssNotifierを追加します。
     *
     * @param target チャンネル名
     * @param feedUrl RSSフィード
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public synchronized void addRssNotifier(String target, String feedUrl)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().addRssNotifier(target, feedUrl);
    }

    /**
     * RssNotifierを削除します。
     *
     * @param target チャンネル名
     * @param feedUrl RSSフィード
     * @throws InterruptedException
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     */
    public synchronized void removeRssNotifier(String target, String feedUrl)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().removeRssNotifier(target, feedUrl);
    }

    /**
     * ScriptNotifierの設定一覧を取得します。
     *
     * @param target チャンネル名
     * @return ScriptNotifierの設定一覧
     */
    public synchronized ArrayList<ScriptNotifierConfig> getScriptNotifierConfig(
            String target) {

        return IrcBotServer.getInstance().getConfig().getScriptNotifierConfig()
                .get(target);
    }

    /**
     * ScriptNotifierを更新します。
     *
     * @param target チャンネル名
     * @param scriptNotifierCinfig スクリプト通知の設定
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @throws FileNotFoundException
     */
    public synchronized void updateScriptNotifier(String target,
            ArrayList<ScriptNotifierConfig> scriptNotifierCinfig)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().updateScriptNotifier(target,
                scriptNotifierCinfig);
    }

    /**
     * メッセージ受信スクリプトの設定一覧を取得します。
     *
     * @param target チャンネル名
     * @return メッセージ受信スクリプト
     */
    public synchronized ArrayList<String> getScriptProcessorConfig(String target) {

        return IrcBotServer.getInstance().getConfig()
                .getScriptProcessorConfig().get(target);
    }

    /**
     * メッセージ受信スクリプトを更新します。
     *
     * @param target チャンネル名
     * @param scriptProcessorCinfig スクリプト通知の設定
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     * @throws FileNotFoundException
     */
    public synchronized void updateScriptProcessor(String target,
            ArrayList<String> scriptProcessorCinfig)
            throws NoSuchAlgorithmException, InterruptedException,
            FileNotFoundException {

        IrcBotServer.getInstance().updateScriptProcessor(target,
                scriptProcessorCinfig);
    }

    /**
     * 接続中かどうかを返却します。
     *
     * @return 接続中の場合true
     */
    public boolean isConnected() {
        return IrcBotServer.getInstance().isConnected();
    }
}
