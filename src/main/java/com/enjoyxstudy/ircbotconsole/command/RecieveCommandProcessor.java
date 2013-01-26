package com.enjoyxstudy.ircbotconsole.command;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * 各種メッセージ受信に対応する処理を実行するプロセッサのインタフェースです。
 *
 * @author onozaty
 */
public interface RecieveCommandProcessor {

    /**
     * メッセージを受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param message メッセージ
     */
    void onMessage(IrcBot ircBot, String channel, String sender, String login,
            String hostname, String message);

    /**
     * プライベートメッセージを受信します。
     *
     * @param ircBot IRCボット
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param message メッセージ
     */
    void onPrivateMessage(IrcBot ircBot, String sender, String login,
            String hostname, String message);

    /**
     * NOTICEを受信します。
     *
     * @param ircBot IRCボット
     * @param sourceNick 送信元のニックネーム
     * @param sourceLogin 送信元のログイン名
     * @param sourceHostname 送信元のホスト名
     * @param channel 送信先(チャンネル)
     * @param notice NOTICEメッセージ
     */
    void onNotice(IrcBot ircBot, String sourceNick, String sourceLogin,
            String sourceHostname, String channel, String notice);

    /**
     * JOINを受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     */
    void onJoin(IrcBot ircBot, String channel, String sender, String login,
            String hostname);

    /**
     * PARTを受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param reason 理由
     */
    void onPart(IrcBot ircBot, String channel, String sender, String login,
            String hostname, String reason);

    /**
     * ニックネーム変更を受信します。
     *
     * @param ircBot IRCボット
     * @param oldNick 旧ニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param newNick 新ニックネーム
     * @param channels ユーザがJOINしているチャンネル一覧
     */
    void onNickChange(IrcBot ircBot, String oldNick, String login,
            String hostname, String newNick, String[] channels);

    /**
     * KICKを受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param kickerNick KICKしたユーザのニックネーム
     * @param kickerLogin KICKしたユーザのログイン名
     * @param kickerHostname KICKしたユーザのホスト名
     * @param recipientNick KICKされたユーザのニックネーム
     * @param reason 理由
     */
    void onKick(IrcBot ircBot, String channel, String kickerNick,
            String kickerLogin, String kickerHostname, String recipientNick,
            String reason);

    /**
     * QUITを受信します。
     *
     * @param ircBot IRCボット
     * @param sourceNick 送信元のニックネーム
     * @param sourceLogin 送信元のログイン名
     * @param sourceHostname 送信元のホスト名
     * @param reason 理由
     * @param channels ユーザがJOINしていたチャンネル一覧
     */
    void onQuit(IrcBot ircBot, String sourceNick, String sourceLogin,
            String sourceHostname, String reason, String[] channels);

    /**
     * TOPICを受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param topic トピック
     * @param setBy 設定者のニックネーム
     * @param date 日付
     * @param changed 変更したタイミングの場合true
     */
    void onTopic(IrcBot ircBot, String channel, String topic, String setBy,
            long date, boolean changed);

    /**
     * モード設定を受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sourceNick モード設定元のニックネーム
     * @param sourceLogin モード設定元のログイン名
     * @param sourceHostname モード設定元のホスト名
     * @param mode モード
     */
    void onMode(IrcBot ircBot, String channel, String sourceNick,
            String sourceLogin, String sourceHostname, String mode);

    /**
     * DISCONNECTを受信します。
     *
     * @param ircBot IRCボット
     * @param channels チャンネル一覧
     */
    void onDisconnect(IrcBot ircBot, String[] channels);

    /**
     * IRCボットの発言を受信します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param message メッセージ
     */
    void onSendMessage(IrcBot ircBot, String channel, String message);

}
