package com.enjoyxstudy.ircbotconsole;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

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
    public synchronized ArrayList<String> getRssNotiferFeeds(String target) {

        return IrcBotServer.getInstance().getConfig().getRssNotiferConfig()
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
    public synchronized void addRssNotifer(String target, String feedUrl)
            throws NoSuchAlgorithmException, InterruptedException, FileNotFoundException {

        IrcBotServer.getInstance().addRssNotifer(target, feedUrl);
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
    public synchronized void removeRssNotifer(String target, String feedUrl)
            throws NoSuchAlgorithmException, InterruptedException, FileNotFoundException {

        IrcBotServer.getInstance().removeRssNotifer(target, feedUrl);
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
