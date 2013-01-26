package com.enjoyxstudy.ircbotconsole.command;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * ログ出力を行うプロセッサです。
 *
 * @author onozaty
 */
public class LogWriteProcessor extends AbstractRecieveCommandProcessor {

    /** ログ出力ディレクトリです。 */
    private String outputDirectory;

    /** 出力時のエンコーディングです。 */
    public static final String ENCODING = "UTF-8";

    /** ファイル名のフォーマットです。 */
    private static final SimpleDateFormat LOGFILE_FORMAT = new SimpleDateFormat(
            "yyyy-MM-dd'.log'");

    /** 時間のフォーマットです。 */
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat(
            "HH:mm");

    /** メッセージ1行のフォーマットです。 */
    private static final String LINE_FORMAT = "%s %s\n";

    /**
     * コンストラクタです。
     *
     * @param outputDirectory 出力ディレクトリ
     */
    public LogWriteProcessor(String outputDirectory) {
        super();

        this.outputDirectory = outputDirectory;
        if (this.outputDirectory.charAt(this.outputDirectory.length() - 1) != File.separatorChar) {
            // 末尾にセパレータがなければ追加
            this.outputDirectory += File.separatorChar;
        }

        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            // 存在しない場合、ディレクトリ作成
            directory.mkdir();
        }
    }

    /**
     * ログ出力を行います。
     *
     * @param date 日付
     * @param channel チャンネル
     * @param message メッセージ
     */
    private void write(Date date, String channel, String message) {

        BufferedWriter writer = null;
        try {
            // 1行を出力
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(getLogFile(date, channel), true),
                    ENCODING));
            writer.write(String.format(LINE_FORMAT, TIME_FORMAT.format(date),
                    message));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ログファイルを取得します。
     *
     * @param date 日付
     * @param channel チャンネル
     * @return ログファイル
     */
    private File getLogFile(Date date, String channel) {

        StringBuilder directoryPathBuilder = new StringBuilder(outputDirectory);
        directoryPathBuilder.append(channel.substring(1)); // チャンネル名の先頭は除去
        directoryPathBuilder.append(File.separator);
        File directory = new File(directoryPathBuilder.toString());
        if (!directory.exists()) {
            // ディレクトリが存在しない場合作成
            directory.mkdir();
        }

        return new File(directory, LOGFILE_FORMAT.format(date));
    }

    ////////////////////////////////////////////////////////////////////////////

    /** DISCONNECT時のログメッセージです。 */
    private static final String MSG_FORMAT_DISCONNECT = "* Disconnected.";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onDisconnect(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String[])
     */
    @Override
    public void onDisconnect(IrcBot ircBot, String[] channels) {

        Date now = new Date();
        for (String channel : channels) {
            write(now, channel, MSG_FORMAT_DISCONNECT);
        }
    }

    /** JOIN時のログメッセージです。 */
    private static final String MSG_FORMAT_JOIN = "* %s (%s@%s) has joined %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onJoin(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onJoin(IrcBot ircBot, String channel, String sender,
            String login, String hostname) {

        Date now = new Date();
        write(now, channel, String.format(MSG_FORMAT_JOIN, sender, login,
                hostname, channel));
    }

    /** KICK時のログメッセージです。 */
    private static final String MSG_FORMAT_KICK = "* %s was kicked from %s by %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onKick(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onKick(IrcBot ircBot, String channel, String kickerNick,
            String kickerLogin, String kickerHostname, String recipientNick,
            String reason) {

        Date now = new Date();
        write(now, channel, String.format(MSG_FORMAT_KICK, recipientNick,
                channel, kickerNick));
    }

    /** メッセージ受信時のログメッセージです。 */
    private static final String MSG_FORMAT_MESSAGE = "<%s> %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onMessage(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onMessage(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String message) {

        Date now = new Date();
        write(now, channel, String.format(MSG_FORMAT_MESSAGE, sender, message));
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onSendMessage(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String)
     */
    @Override
    public void onSendMessage(IrcBot ircBot, String channel, String message) {
        Date now = new Date();
        write(now, channel, String.format(MSG_FORMAT_MESSAGE, ircBot.getNick(),
                message));
    }

    /** モード設定時のログメッセージです。 */
    private static final String MSG_FORMAT_MODE = "* %s sets mode %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onMode(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onMode(IrcBot ircBot, String channel, String sourceNick,
            String sourceLogin, String sourceHostname, String mode) {

        Date now = new Date();
        write(now, channel, String.format(MSG_FORMAT_MODE, sourceNick, mode));
    }

    /** ニックネーム変更時のログメッセージです。 */
    private static final String MSG_FORMAT_NICK_CHANGE = "* %s is now known as %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onNickChange(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
     */
    @Override
    public void onNickChange(IrcBot ircBot, String oldNick, String login,
            String hostname, String newNick, String[] channels) {

        Date now = new Date();
        for (String channel : channels) {
            write(now, channel, String.format(MSG_FORMAT_NICK_CHANGE, oldNick,
                    newNick));
        }
    }

    /** NOTICE時のログメッセージです。 */
    private static final String MSG_FORMAT_NOTICE = "-%s- %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onNotice(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onNotice(IrcBot ircBot, String sourceNick, String sourceLogin,
            String sourceHostname, String channel, String notice) {

        Date now = new Date();
        write(now, channel, String
                .format(MSG_FORMAT_NOTICE, sourceNick, notice));
    }

    /** PART時のログメッセージです。 */
    private static final String MSG_FORMAT_PART = "* %s (%s@%s) has left %s (%s)";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.RecieveCommandProcessor#onPart(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onPart(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String reason) {

        Date now = new Date();
        write(now, channel, String.format(MSG_FORMAT_PART, sender, login,
                hostname, channel, reason));
    }

    /** QUIT時のログメッセージです。 */
    private static final String MSG_FORMAT_QUIT = "* %s (%s@%s) Quit (%s)";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onQuit(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String[])
     */
    @Override
    public void onQuit(IrcBot ircBot, String sourceNick, String sourceLogin,
            String sourceHostname, String reason, String[] channels) {

        Date now = new Date();
        for (String channel : channels) {
            write(now, channel, String.format(MSG_FORMAT_QUIT, sourceNick,
                    sourceLogin, sourceHostname, reason));
        }
    }

    /** TOPIC設定時のログメッセージです。 */
    private static final String MSG_FORMAT_CHANGED_TOPIC = "* %s changes topic to '%s'";

    /** TOPIC受信時のログメッセージです。 */
    private static final String MSG_FORMAT_TOPIC = "* Topic is '%s'  Set by %s on %s";

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onTopic(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, long, boolean)
     */
    @Override
    public void onTopic(IrcBot ircBot, String channel, String topic,
            String setBy, long date, boolean changed) {

        Date now = new Date();
        if (changed) {
            write(now, channel, String.format(MSG_FORMAT_CHANGED_TOPIC, setBy,
                    topic));
        } else {
            write(now, channel, String.format(MSG_FORMAT_TOPIC, topic, setBy,
                    new Date(date)));
        }

    }
    ///////////////////////////////////////////////////////////////////////////////

}
