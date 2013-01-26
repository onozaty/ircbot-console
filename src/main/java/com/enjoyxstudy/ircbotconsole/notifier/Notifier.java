package com.enjoyxstudy.ircbotconsole.notifier;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * 定期的にIRCに通知するクラスのインタフェースです。
 *
 * @author onozaty
 */
public interface Notifier {

    /**
     * 実行可能か判定します。
     *
     * @return 実行可能な場合true
     */
    public boolean isExecutable();

    /**
     * 実行します。
     *
     * @param ircBot
     * @throws Exception
     */
    public void execute(IrcBot ircBot) throws Exception;

}
