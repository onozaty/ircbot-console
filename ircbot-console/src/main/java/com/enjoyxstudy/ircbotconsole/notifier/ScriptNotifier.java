package com.enjoyxstudy.ircbotconsole.notifier;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * スクリプトを実行し、その結果を通知するクラスです。
 *
 * @author onozaty
 */
public class ScriptNotifier extends AbstractNotifier {

    /** 実行するスクリプトです。 */
    private String scriptText;

    /**
     * コンストラクタです。
     *
     * @param channel
     * @param scheduler
     * @param scriptText
     */
    public ScriptNotifier(String channel, Scheduler scheduler, String scriptText) {
        super(channel, scheduler);
        this.scriptText = scriptText;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.notifier.AbstractNotifier#createMessage(com.enjoyxstudy.ircbotconsole.IrcBot)
     */
    @Override
    protected String[] createMessage(IrcBot ircBot) throws Exception {

        // スクリプトを実行
        ContextFactory contextFactory = new ContextFactory();
        Context context = contextFactory.enterContext();

        Scriptable scope = context.initStandardObjects();

        // JSのオブジェクトにマッピング
        ScriptableObject.putProperty(scope, "_channel", Context.javaToJS(channel,
                scope));
        ScriptableObject.putProperty(scope, "_ircBot", Context.javaToJS(ircBot,
                scope));

        Object result = context.evaluateString(scope, scriptText, "<script>",
                1, null);

        if (result == null || result instanceof Undefined) {
            // nullまたはundefinedの場合はメッセージ送信を行わない
            return null;
        }

        String message = Context.toString(result);
        return new String[] { message };
    }
}
