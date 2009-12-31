package com.enjoyxstudy.ircbotconsole.notifier;

import java.util.Calendar;

/**
 * 毎日決められた時間に実行するスケジューラです。
 *
 * @author onozaty
 */
public class DailyScheduler implements Scheduler {

    /** 実行時間(時)です */
    protected int hour;

    /** 実行時間(分)です */
    protected int minute;

    /** 実行予定時刻です。 */
    protected long nextTime;

    /**
     * 実行予定時刻からの猶予時間です。
     * (現在時刻が実行予定時刻を過ぎていて、かつこの時間以内の場合に実行)
     */
    protected static final int DELAY_TIME = 10 * 60 * 1000;

    /**
     * 1日のミリ秒です。
     */
    protected static final int DAY_TIME_SPAN = 24 * 60 * 60 * 1000;

    /**
     * コンストラクタです。
     *
     * @param hour 実行時間(時)
     * @param minute 実行時間(分)
     */
    public DailyScheduler(int hour, int minute) {
        super();
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.notifier.Scheduler#isExecutable()
     */
    public boolean isExecutable() {

        long now = System.currentTimeMillis();

        if (nextTime < now) {
            // 実行予定時間を越えていて、かつ実行予定時間から10分以内の場合に実行
            // (時刻をずらされた場合などを考慮し、実行予定時刻から離れすぎている場合には実行しない)
            if (now - nextTime < DELAY_TIME) {
                return true;
            }
            calcNextTime();
        } else if (nextTime - now < collectTimeSpan()) {
            // 実行周期より離れている場合、再度実行予定時刻を計算
            calcNextTime();
        }
        return false;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.notifier.Scheduler#nextSchedule()
     */
    public void nextSchedule() {
        calcNextTime();
    }

    /**
     * 実行周期を返却します。
     *
     * @return 実行周期(ミリ秒)
     */
    protected int collectTimeSpan() {
        return DAY_TIME_SPAN;
    }

    /**
     * 次の実行予定時刻を計算します。
     */
    protected void calcNextTime() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.SECOND, 0); // 秒、ミリ秒は0に
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        nextTime = calendar.getTimeInMillis();
    }
}
