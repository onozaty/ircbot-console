package com.enjoyxstudy.ircbotconsole.notifier;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * メッセージを通知するクラスです。
 *
 * @author onozaty
 */
public class MessageNotifier extends AbstractNotifier {

    /** 通知するメッセージです。 */
    private String message;

    /**
     * コンストラクタです。
     *
     * @param channel
     * @param scheduler
     * @param message
     */
    public MessageNotifier(String channel, Scheduler scheduler, String message) {
        super(channel, scheduler);
        this.message = message;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.notifier.AbstractNotifier#createMessage(com.enjoyxstudy.ircbotconsole.IrcBot)
     */
    @Override
    protected String[] createMessage(IrcBot ircBot) throws Exception {
        return new String[] { message };
    }

}
