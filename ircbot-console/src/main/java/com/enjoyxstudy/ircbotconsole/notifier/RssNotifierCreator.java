package com.enjoyxstudy.ircbotconsole.notifier;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * RssNotifierを生成するクラスです。
 *
 * @author onozaty
 */
public class RssNotifierCreator {

    /** 作業用ディレクトリです。 */
    public File workDirectory;

    /** 通知周期(秒)です。 */
    private static final int NOTIFIER_CYCLE = 60; // 60秒

    /**
     * コンストラクタです。
     *
     * @param workDirectory 作業用ディレクトリ
     */
    public RssNotifierCreator(String workDirectory) {
        this(new File(workDirectory));
    }

    /**
     * コンストラクタです。
     *
     * @param workDirectory 作業用ディレクトリ
     */
    public RssNotifierCreator(File workDirectory) {
        super();
        this.workDirectory = workDirectory;
    }

    /**
     * RssNotifierを生成します。
     *
     * @param channel チャンネル名
     * @param feedUrl RSSフィード
     * @return RssNotifier
     * @throws NoSuchAlgorithmException
     */
    public RssNotifier createNotifier(String channel, String feedUrl)
            throws NoSuchAlgorithmException {

        return new RssNotifier(channel, new CyclicScheduler(NOTIFIER_CYCLE),
                feedUrl, workDirectory);
    }

    /**
     * RssNotifierを生成します。
     *
     * @param channelFeedsMap チャンネル名とRSSフィードリストのマップ
     * @return RssNotifierのリスト
     * @throws NoSuchAlgorithmException
     */
    public List<Notifier> createNotifiers(
            HashMap<String, ArrayList<String>> channelFeedsMap)
            throws NoSuchAlgorithmException {

        ArrayList<Notifier> notifierList = new ArrayList<Notifier>();

        for (String channel : channelFeedsMap.keySet()) {
            for (String feed : channelFeedsMap.get(channel)) {
                notifierList.add(createNotifier(channel, feed));
            }
        }

        return notifierList;
    }
}
