package com.enjoyxstudy.ircbotconsole.command;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * 各種メッセージ受信に対応する処理を実行するプロセッサの抽象クラスです。
 * 空のメソッドを定義しておきます。
 *
 * @author onozaty
 */
public abstract class AbstractRecieveCommandProcessor implements
        RecieveCommandProcessor {

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onDisconnect(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String[])
     */
    public void onDisconnect(IrcBot ircBot, String[] channels) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onJoin(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onJoin(IrcBot ircBot, String channel, String sender,
            String login, String hostname) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onKick(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onKick(IrcBot ircBot, String channel, String kickerNick,
            String kickerLogin, String kickerHostname, String recipientNick,
            String reason) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onMessage(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onMessage(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String message) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onMode(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onMode(IrcBot ircBot, String channel, String sourceNick,
            String sourceLogin, String sourceHostname, String mode) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onNickChange(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
     */
    public void onNickChange(IrcBot ircBot, String oldNick, String login,
            String hostname, String newNick, String[] channels) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onNotice(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onNotice(IrcBot ircBot, String sourceNick, String sourceLogin,
            String sourceHostname, String channel, String notice) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onPart(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onPart(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String reason) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onPrivateMessage(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    public void onPrivateMessage(IrcBot ircBot, String sender, String login,
            String hostname, String message) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onQuit(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
     */
    public void onQuit(IrcBot ircBot, String sourceNick, String sourceLogin,
            String sourceHostname, String reason, String[] channels) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onTopic(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, long, boolean)
     */
    public void onTopic(IrcBot ircBot, String channel, String topic,
            String setBy, long date, boolean changed) {
        // 処理なし(必要があれば継承して実装)
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onSendMessage(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String)
     */
    public void onSendMessage(IrcBot ircBot, String channel, String message) {
        // 処理なし(必要があれば継承して実装)
    }

}
