package com.enjoyxstudy.ircbotconsole;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import com.enjoyxstudy.ircbotconsole.command.RecieveCommandHandler;

/**
 * IRCボットクラスです。
 *
 * @author onozaty
 */
public class IrcBot extends PircBot {

    /** チャンネル名で使用される先頭の文字です。 */
    private static final String CHANNEL_PREFIXES = "#&+!";

    /** IRCボットのバージョン情報です。 */
    private static final String VERSION_INFO = "IRCBot plus version 0.1 <http://www.enjoyxstudy.com/ipcbotplus/>";

    /** 各プロセッサの呼び出しを制御するハンドラです。 */
    private RecieveCommandHandler recieveCommandHandler;

    /** 接続時にJOINするチャンネルのリストです。 */
    private ArrayList<String> autoJoinChannels = new ArrayList<String>();

    /**
     * コンストラクタ
     *
     * @param recieveCommandHandler 各プロセッサの呼び出しを制御するハンドラ
     */
    public IrcBot(RecieveCommandHandler recieveCommandHandler) {
        super();
        this.recieveCommandHandler = recieveCommandHandler;

        setVersion(VERSION_INFO);
        setFinger("");
    }

    /**
     * 接続を行います。
     *
     * @throws IOException サーバに接続できなかった場合
     * @throws IrcException IRCに加われなかった場合
     * @throws NickAlreadyInUseException ニックネームが既に利用されていた場合
     */
    public synchronized void connect() throws IOException, IrcException,
            NickAlreadyInUseException {

        connect(getServer(), getPort(), getPassword());
    }

    /**
     * 接続(+自動JOIN)を行います。
     *
     * @throws IOException サーバに接続できなかった場合
     * @throws IrcException IRCに加われなかった場合
     * @throws NickAlreadyInUseException ニックネームが既に利用されていた場合
     */
    public synchronized void connectJoin() throws IOException, IrcException,
            NickAlreadyInUseException {

        connect();
        joinAutoChannels();
    }

    /**
     * 再接続(+自動JOIN)を行います。
     *
     * @throws IOException サーバに接続できなかった場合
     * @throws IrcException IRCに加われなかった場合
     * @throws NickAlreadyInUseException ニックネームが既に利用されていた場合
     */
    public synchronized void reconnectJoin() throws IOException, IrcException,
            NickAlreadyInUseException {

        reconnect();
        joinAutoChannels();
    }

    /**
     * あらかじめ設定されたチャンネルにJOINします。
     */
    public void joinAutoChannels() {
        for (String channel : autoJoinChannels) {
            joinChannel(channel);
        }
    }

    /**
     * 指定されたニックネームのユーザがJOINしているチャンネルの一覧を
     * 返却します。
     *
     * @param nick ニックネーム
     * @return JOINしているチャンネルの一覧
     */
    public final String[] getChannelsByNick(String nick) {

        // 全チャンネルのユーザ一覧から、ニックネームが一致するユーザを探し
        // JOINしているチャンネル名を収集
        ArrayList<String> joinChannelList = new ArrayList<String>();
        for (String channel : getChannels()) {
            for (User user : getUsers(channel)) {
                if (user.getNick().equals(nick)) {
                    joinChannelList.add(channel);
                }
            }
        }
        return joinChannelList.toArray(new String[joinChannelList.size()]);
    }

    /**
     * チャンネル名か判定します。
     *
     * @param target チャンネル名またはニックネーム
     * @return チャンネル名の場合true
     */
    public static boolean isChannel(String target) {
        // 先頭の文字でチャンネルか判断
        return (CHANNEL_PREFIXES.indexOf(target.charAt(0)) >= 0);
    }

    /**
     * メッセージを送信します。
     *
     * @param target 送信先
     * @param messages メッセージ
     */
    public void sendMessages(String target, String[] messages) {
        for (String message : messages) {
            sendMessage(target, message);
        }
    }

    /**
     * サーバ名を設定します。
     *
     * @param server サーバ名
     */
    public void setServer(String server) {
        _server = server;
    }

    /**
     * パスワードを設定します。
     *
     * @param password パスワード
     */
    public void setPassword(String password) {
        _password = password;
    }

    /**
     * ポート番号を設定します。
     *
     * @param port ポート番号
     */
    public void setPort(int port) {
        _port = port;
    }

    /**
     * Nickを設定します。
     *
     * @param nick Nick
     */
    public void setNickName(String nick) {
        setName(nick);
        setLogin(nick);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    /**
     * @see org.jibble.pircbot.PircBot#onMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onMessage(String channel, String sender, String login,
            String hostname, String message) {
        recieveCommandHandler.handleMessage(this, channel, sender, login,
                hostname, message);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onPrivateMessage(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onPrivateMessage(String sender, String login, String hostname,
            String message) {
        recieveCommandHandler.handlePrivateMessage(this, sender, login,
                hostname, message);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onNotice(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onNotice(String sourceNick, String sourceLogin,
            String sourceHostname, String target, String notice) {
        recieveCommandHandler.handleNotice(this, sourceNick, sourceLogin,
                sourceHostname, target, notice);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onJoin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onJoin(String channel, String sender, String login,
            String hostname) {
        recieveCommandHandler
                .handleJoin(this, channel, sender, login, hostname);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onPart(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onPart(String channel, String sender, String login,
            String hostname, String reason) {
        recieveCommandHandler.handlePart(this, channel, sender, login,
                hostname, reason);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onNickChange(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onNickChange(String oldNick, String login, String hostname,
            String newNick) {
        recieveCommandHandler.handleNickChange(this, oldNick, login, hostname,
                newNick);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onKick(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onKick(String channel, String kickerNick, String kickerLogin,
            String kickerHostname, String recipientNick, String reason) {
        recieveCommandHandler.handleKick(this, channel, kickerNick,
                kickerLogin, kickerHostname, recipientNick, reason);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onQuit(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onQuit(String sourceNick, String sourceLogin,
            String sourceHostname, String reason) {
        recieveCommandHandler.handleQuit(this, sourceNick, sourceLogin,
                sourceHostname, reason);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onTopic(java.lang.String, java.lang.String, java.lang.String, long, boolean)
     */
    @Override
    public void onTopic(String channel, String topic, String setBy, long date,
            boolean changed) {
        recieveCommandHandler.handleTopic(this, channel, topic, setBy, date,
                changed);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onMode(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onMode(String channel, String sourceNick, String sourceLogin,
            String sourceHostname, String mode) {
        recieveCommandHandler.handleMode(this, channel, sourceNick,
                sourceLogin, sourceHostname, mode);
    }

    /**
     * @see org.jibble.pircbot.PircBot#onDisconnect()
     */
    @Override
    public void onDisconnect() {
        recieveCommandHandler.handleDisconnect(this);
    }

    /**
     * @see org.jibble.pircbot.PircBot#sendMessage(java.lang.String, java.lang.String)
     */
    @Override
    public void sendMessage(String target, String message) {
        super.sendMessage(target, message);
        recieveCommandHandler.handleSendMessage(this, target, message);
    }

    ///////////////////////////////////////////////////////////////////////////////////////

    /**
     * @return recieveCommandHandler
     */
    public RecieveCommandHandler getRecieveCommandHandler() {
        return recieveCommandHandler;
    }

    /**
     * @param recieveCommandHandler recieveCommandHandler
     */
    public void setRecieveCommandHandler(
            RecieveCommandHandler recieveCommandHandler) {
        this.recieveCommandHandler = recieveCommandHandler;
    }

    /**
     * @return autoJoinChannels
     */
    public ArrayList<String> getAutoJoinChannels() {
        return autoJoinChannels;
    }

    /**
     * @param autoJoinChannels autoJoinChannels
     */
    public void setAutoJoinChannels(ArrayList<String> autoJoinChannels) {
        this.autoJoinChannels = autoJoinChannels;
    }

}
