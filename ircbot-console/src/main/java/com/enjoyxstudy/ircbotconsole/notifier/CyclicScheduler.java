package com.enjoyxstudy.ircbotconsole.notifier;

/**
 * 周期実行するスケジューラです。
 *
 * @author onozaty
 */
public class CyclicScheduler implements Scheduler {

    /** 周期(ミリ秒)です。 */
    private long msecSpan;

    /** 次の通知日時です。(ミリ秒) */
    protected long nextNotifyTime;

    /**
     * コンストラクタ
     *
     * @param timeSpan 周期(秒)
     */
    public CyclicScheduler(int timeSpan) {
        msecSpan = timeSpan * 1000;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.notifier.Scheduler#nextSchedule()
     */
    public void nextSchedule() {
        nextNotifyTime = System.currentTimeMillis() + msecSpan;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.notifier.Scheduler#isExecutable()
     */
    public boolean isExecutable() {
        return nextNotifyTime <= System.currentTimeMillis();
    }

    /**
     * @return msecSpan
     */
    public long getMsecSpan() {
        return msecSpan;
    }

}
