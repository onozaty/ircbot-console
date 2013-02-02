package com.enjoyxstudy.ircbotconsole.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * 各プロセッサの呼び出しを制御するハンドラクラスです。
 *
 * @author onozaty
 */
public class RecieveCommandHandler {

    /**
     * チャンネル名をキーとしたプロセッサのリストです。
     * nullの場合、デフォルト(必ず呼ばれる)のプロセッサとなります。
     */
    private HashMap<String, List<RecieveCommandProcessor>> processorListHash = new HashMap<String, List<RecieveCommandProcessor>>();

    /**
     * プロセッサを追加します。
     * @param channel チャンネル名
     * @param processor プロセッサ
     */
    public void addProcessor(String channel, RecieveCommandProcessor processor) {

        channel = normalizeChannelName(channel);

        List<RecieveCommandProcessor> processorList = processorListHash
                .get(channel);

        if (processorList == null) {
            processorList = new ArrayList<RecieveCommandProcessor>();
            processorListHash.put(channel, processorList);
        }

        processorList.add(processor);
    }

    /**
     * プロセッサを削除します。
     * @param channel チャンネル名
     * @param processor プロセッサ
     */
    public void removeProcessor(String channel,
            RecieveCommandProcessor processor) {

        channel = normalizeChannelName(channel);

        List<RecieveCommandProcessor> processorList = processorListHash
                .get(channel);

        processorList.remove(processor);

        if (processorList.size() == 0) {
            // チャンネルに対するプロセッサがまったく無くなった場合
            // そのチャンネルに対するリスト自体を削除
            processorListHash.remove(channel);
        }
    }

    /**
     * 指定したチャンネルとクラスのプロセッサを削除します。
     * @param channel チャンネル名
     * @param processorType プロセッサのClass
     */
    public void removeProcessors(String channel, Class<?> processorType) {

        channel = normalizeChannelName(channel);

        List<RecieveCommandProcessor> processorList = processorListHash
                .get(channel);

        if (processorList != null) {
            List<RecieveCommandProcessor> removeTargerProcessor = new ArrayList<RecieveCommandProcessor>();
            for (RecieveCommandProcessor recieveCommandProcessor : processorList) {
                if (recieveCommandProcessor.getClass() == processorType) {
                    removeTargerProcessor.add(recieveCommandProcessor);
                }
            }
            for (RecieveCommandProcessor targetProcessor : removeTargerProcessor) {
                processorList.remove(targetProcessor);
            }

            if (processorList.size() == 0) {
                // チャンネルに対するプロセッサがまったく無くなった場合
                // そのチャンネルに対するリスト自体を削除
                processorListHash.remove(channel);
            }
        }
    }

    /**
     * DISCONNECTを通知します。
     *
     * @param ircBot IRCボット
     */
    public void handleDisconnect(IrcBot ircBot) {

        String[] channels = ircBot.getChannels();

        channels = normalizeChannelNames(channels);

        // デフォルトと、ログインしているチャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onDisconnect(ircBot, channels);
            }
        }

        for (String channel : channels) {

            List<RecieveCommandProcessor> processorList = processorListHash
                    .get(channel);
            if (processorList != null) {
                for (RecieveCommandProcessor processor : processorList) {
                    processor.onDisconnect(ircBot, channels);
                }
            }
        }

    }

    /**
     * JOINを通知します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     */
    public void handleJoin(IrcBot ircBot, String channel, String sender,
            String login, String hostname) {

        channel = normalizeChannelName(channel);

        // デフォルトと、指定チャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onJoin(ircBot, channel, sender, login, hostname);
            }
        }

        List<RecieveCommandProcessor> targetProcessorList = processorListHash
                .get(channel);
        if (targetProcessorList != null) {
            for (RecieveCommandProcessor processor : targetProcessorList) {
                processor.onJoin(ircBot, channel, sender, login, hostname);
            }
        }
    }

    /**
     * KICKを通知します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param kickerNick KICKしたユーザのニックネーム
     * @param kickerLogin KICKしたユーザのログイン名
     * @param kickerHostname KICKしたユーザのホスト名
     * @param recipientNick KICKされたユーザのニックネーム
     * @param reason 理由
     */
    public void handleKick(IrcBot ircBot, String channel, String kickerNick,
            String kickerLogin, String kickerHostname, String recipientNick,
            String reason) {

        channel = normalizeChannelName(channel);

        // デフォルトと、指定チャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onKick(ircBot, channel, kickerNick, kickerLogin,
                        kickerHostname, recipientNick, reason);
            }
        }

        List<RecieveCommandProcessor> targetProcessorList = processorListHash
                .get(channel);
        if (targetProcessorList != null) {
            for (RecieveCommandProcessor processor : targetProcessorList) {
                processor.onKick(ircBot, channel, kickerNick, kickerLogin,
                        kickerHostname, recipientNick, reason);
            }
        }
    }

    /**
     * メッセージを通知します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param message メッセージ
     */
    public void handleMessage(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String message) {

        channel = normalizeChannelName(channel);

        // デフォルトと、指定チャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onMessage(ircBot, channel, sender, login, hostname,
                        message);
            }
        }

        List<RecieveCommandProcessor> targetProcessorList = processorListHash
                .get(channel);
        if (targetProcessorList != null) {
            for (RecieveCommandProcessor processor : targetProcessorList) {
                processor.onMessage(ircBot, channel, sender, login, hostname,
                        message);
            }
        }
    }

    /**
     * モード設定を通知します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sourceNick モード設定元のニックネーム
     * @param sourceLogin モード設定元のログイン名
     * @param sourceHostname モード設定元のホスト名
     * @param mode モード
     */
    public void handleMode(IrcBot ircBot, String channel, String sourceNick,
            String sourceLogin, String sourceHostname, String mode) {

        channel = normalizeChannelName(channel);

        // デフォルトと、指定チャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onMode(ircBot, channel, sourceNick, sourceLogin,
                        sourceHostname, mode);
            }
        }

        List<RecieveCommandProcessor> targetProcessorList = processorListHash
                .get(channel);
        if (targetProcessorList != null) {
            for (RecieveCommandProcessor processor : targetProcessorList) {
                processor.onMode(ircBot, channel, sourceNick, sourceLogin,
                        sourceHostname, mode);
            }
        }
    }

    /**
     * ニックネーム変更を通知します。
     *
     * @param ircBot IRCボット
     * @param oldNick 旧ニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param newNick 新ニックネーム
     */
    public void handleNickChange(IrcBot ircBot, String oldNick, String login,
            String hostname, String newNick) {

        String[] channels = ircBot.getChannelsByNick(newNick);

        channels = normalizeChannelNames(channels);

        // デフォルトと、ニックネーム変更したユーザがJOINしているチャンネルの
        // プロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onNickChange(ircBot, oldNick, login, hostname,
                        newNick, channels);
            }
        }

        for (String channel : channels) {
            List<RecieveCommandProcessor> processorList = processorListHash
                    .get(channel);
            if (processorList != null) {
                for (RecieveCommandProcessor processor : processorList) {
                    processor.onNickChange(ircBot, oldNick, login, hostname,
                            newNick, channels);
                }
            }
        }
    }

    /**
     * NOTICEを通知します。
     *
     * @param ircBot IRCボット
     * @param sourceNick 送信元のニックネーム
     * @param sourceLogin 送信元のログイン名
     * @param sourceHostname 送信元のホスト名
     * @param target 送信先(チャンネルまたはニックネーム)
     * @param notice NOTICEメッセージ
     */
    public void handleNotice(IrcBot ircBot, String sourceNick,
            String sourceLogin, String sourceHostname, String target,
            String notice) {

        // チャンネルに対するメッセージの場合のみ通知
        if (IrcBot.isChannel(target)) {

            target = normalizeChannelName(target);

            // デフォルトと、指定チャンネルのプロセッサに通知
            List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                    .get(null);
            if (defaultProcessorList != null) {
                for (RecieveCommandProcessor processor : defaultProcessorList) {
                    processor.onNotice(ircBot, sourceNick, sourceLogin,
                            sourceHostname, target, notice);
                }
            }

            List<RecieveCommandProcessor> targetProcessorList = processorListHash
                    .get(target);
            if (targetProcessorList != null) {
                for (RecieveCommandProcessor processor : targetProcessorList) {
                    processor.onNotice(ircBot, sourceNick, sourceLogin,
                            sourceHostname, target, notice);
                }
            }
        }
    }

    /**
     * PARTを通知します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param reason 理由
     */
    public void handlePart(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String reason) {

        channel = normalizeChannelName(channel);

        // デフォルトと、指定チャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onPart(ircBot, channel, sender, login, hostname,
                        reason);
            }
        }

        List<RecieveCommandProcessor> targetProcessorList = processorListHash
                .get(channel);
        if (targetProcessorList != null) {
            for (RecieveCommandProcessor processor : targetProcessorList) {
                processor.onPart(ircBot, channel, sender, login, hostname,
                        reason);
            }
        }
    }

    /**
     * プライベートメッセージを通知します。
     *
     * @param ircBot IRCボット
     * @param sender 送信者のニックネーム
     * @param login ログイン名
     * @param hostname ホスト名
     * @param message メッセージ
     */
    public void handlePrivateMessage(IrcBot ircBot, String sender,
            String login, String hostname, String message) {

        // デフォルトのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onPrivateMessage(ircBot, sender, login, hostname,
                        message);
            }
        }
    }

    /**
     * QUITを通知します。
     *
     * @param ircBot IRCボット
     * @param sourceNick 送信元のニックネーム
     * @param sourceLogin 送信元のログイン名
     * @param sourceHostname 送信元のホスト名
     * @param reason 理由
     */
    public void handleQuit(IrcBot ircBot, String sourceNick,
            String sourceLogin, String sourceHostname, String reason) {

        String[] channels = ircBot.getChannelsByNick(sourceNick);

        channels = normalizeChannelNames(channels);

        // デフォルトと、QuitしたユーザがJOINしていたチャンネルの
        // プロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onQuit(ircBot, sourceNick, sourceLogin,
                        sourceHostname, reason, channels);
            }
        }

        for (String channel : channels) {
            List<RecieveCommandProcessor> processorList = processorListHash
                    .get(channel);
            if (processorList != null) {
                for (RecieveCommandProcessor processor : processorList) {
                    processor.onQuit(ircBot, sourceNick, sourceLogin,
                            sourceHostname, reason, channels);
                }
            }
        }
    }

    /**
     * TOPICを通知します。
     *
     * @param ircBot IRCボット
     * @param channel チャンネル
     * @param topic トピック
     * @param setBy 設定者のニックネーム
     * @param date 日付
     * @param changed 変更したタイミングの場合true
     */
    public void handleTopic(IrcBot ircBot, String channel, String topic,
            String setBy, long date, boolean changed) {

        channel = normalizeChannelName(channel);

        // デフォルトと、指定チャンネルのプロセッサに通知
        List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                .get(null);
        if (defaultProcessorList != null) {
            for (RecieveCommandProcessor processor : defaultProcessorList) {
                processor.onTopic(ircBot, channel, topic, setBy, date, changed);
            }
        }

        List<RecieveCommandProcessor> targetProcessorList = processorListHash
                .get(channel);
        if (targetProcessorList != null) {
            for (RecieveCommandProcessor processor : targetProcessorList) {
                processor.onTopic(ircBot, channel, topic, setBy, date, changed);
            }
        }
    }

    /**
     * メッセージ送信を通知します。
     * IrcBotの発言になります。
     *
     * @param ircBot IRCボット
     * @param target 送信先(チャンネルまたはニックネーム)
     * @param message メッセージ
     */
    public void handleSendMessage(IrcBot ircBot, String target, String message) {

        // チャンネルに対するメッセージの場合のみ通知
        if (IrcBot.isChannel(target)) {

            target= normalizeChannelName(target);

            // デフォルトと、指定チャンネルのプロセッサに通知
            List<RecieveCommandProcessor> defaultProcessorList = processorListHash
                    .get(null);
            if (defaultProcessorList != null) {
                for (RecieveCommandProcessor processor : defaultProcessorList) {
                    processor.onSendMessage(ircBot, target, message);
                }
            }

            List<RecieveCommandProcessor> targetProcessorList = processorListHash
                    .get(target);
            if (targetProcessorList != null) {
                for (RecieveCommandProcessor processor : targetProcessorList) {
                    processor.onSendMessage(ircBot, target, message);
                }
            }
        }
    }

    /**
     * チャンネル名を正規化します。
     *
     * @param chanel 正規化前のチャンネル名
     * @return 正規化したチャンネル名
     */
    private String normalizeChannelName(String channel) {

        if (channel == null) {
            return null;
        }

        return channel.toLowerCase();
    }

    /**
     * チャンネル名を正規化します。
     *
     * @param chanels 正規化前のチャンネル名
     * @return 正規化したチャンネル名
     */
    private String[] normalizeChannelNames(String[] channels) {

        if (channels == null) {
            return null;
        }

        String[] normalizeChannels = new String[channels.length];
        for (int i = 0; i < channels.length; i++){

            normalizeChannels[i] = normalizeChannelName(channels[i]);
        }

        return normalizeChannels;
    }
}
