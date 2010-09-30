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
     * @param config RSS情報
     * @return RssNotifier
     * @throws NoSuchAlgorithmException
     */
    public RssNotifier createNotifier(String channel, RssNotifierConfig config)
            throws NoSuchAlgorithmException {

        return new RssNotifier(channel, new CyclicScheduler(config
                .getCycleMinute() * 60), config.getFeedUrl(), config
                .getMessageFormatScript(), workDirectory);
    }

    /**
     * RssNotifierを生成します。
     *
     * @param channelRssMap チャンネル名とRSS情報のマップ
     * @return RssNotifierのリスト
     * @throws NoSuchAlgorithmException
     */
    public List<Notifier> createNotifiers(
            HashMap<String, ArrayList<RssNotifierConfig>> channelRssMap)
            throws NoSuchAlgorithmException {

        ArrayList<Notifier> notifierList = new ArrayList<Notifier>();

        for (String channel : channelRssMap.keySet()) {
            for (RssNotifierConfig rssConfig : channelRssMap.get(channel)) {
                notifierList.add(createNotifier(channel, rssConfig));
            }
        }

        return notifierList;
    }
}
