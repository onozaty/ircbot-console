package com.enjoyxstudy.ircbotconsole.notifier;

/**
 * 実行スケジュールを管理するインタフェースです。
 *
 * @author onozaty
 */
public interface Scheduler {

    /**
     * 実行可能かを返却します。
     *
     * @return 実行可能な場合true
     */
    public boolean isExecutable();

    /**
     * 次の実行スケジュールを設定します。
     *
     */
    public void nextSchedule();

}
