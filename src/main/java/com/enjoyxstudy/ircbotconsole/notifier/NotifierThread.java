package com.enjoyxstudy.ircbotconsole.notifier;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * Notifierを実行するスレッドクラスです。
 *
 * @author onozaty
 */
public class NotifierThread extends Thread {

    /** loggerです。 */
    private static Logger logger = LoggerFactory
            .getLogger(NotifierThread.class);

    /** 終了を表すフラグです。 */
    private boolean isStop;

    /** IRCボットです。 */
    private IrcBot ircBot;

    /** Notifierのリストです。 */
    private List<Notifier> notifierList;

    /** 通知判定処理でWAITする時間(ミリ秒)です。 */
    private static final int WAIT_TIMMING = 10 * 1000;

    /**
     * コンストラクタです。
     *
     * @param ircBot IRCボット
     * @param notifierList Notifierのリスト
     */
    public NotifierThread(IrcBot ircBot, List<Notifier> notifierList) {
        super();
        this.ircBot = ircBot;
        this.notifierList = notifierList;
    }

    /**
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        logger.info("NotifierThreadを開始しました。 Notifier数=[{}]", new Integer(
                notifierList.size()));

        while (!isStop) {

            // 接続状態時のみ実施
            if (ircBot.isConnected()) {

                for (Notifier notifier : notifierList) {
                    try {
                        if (notifier.isExecutable()) {
                            notifier.execute(ircBot);
                        }
                    } catch (Exception e) {
                        logger.error("Notifierの呼び出しでエラーが発生しました。", e);
                    }
                }
            }
            try {
                Thread.sleep(WAIT_TIMMING);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("NotifierThreadが終了しました。");
    }

    /**
     * @param isStop isStop
     */
    public void setStop(boolean isStop) {
        this.isStop = isStop;
    }

}
