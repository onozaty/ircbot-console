package com.enjoyxstudy.ircbotconsole.command;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enjoyxstudy.ircbotconsole.IrcBot;

/**
 * スクリプトを実行するプロセッサです。
 *
 * @author onozaty
 */
public class ScriptProcessor extends AbstractRecieveCommandProcessor {

    /** loggerです。 */
    static Logger logger = LoggerFactory.getLogger(ScriptProcessor.class);

    /** 実行するスクリプトです。 */
    private String scriptText;

    /**
     * コンストラクタです。
     *
     * @param scriptText スクリプト
     */
    public ScriptProcessor(String scriptText) {
        super();
        this.scriptText = scriptText;
    }

    /**
     * @see com.enjoyxstudy.ircbotconsole.command.AbstractRecieveCommandProcessor#onMessage(com.enjoyxstudy.ircbotconsole.IrcBot, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void onMessage(IrcBot ircBot, String channel, String sender,
            String login, String hostname, String message) {

        try {
            ContextFactory contextFactory = new ContextFactory();
            Context context = contextFactory.enterContext();

            Scriptable scope = context.initStandardObjects();

            // JSのオブジェクトにマッピング
            ScriptableObject.putProperty(scope, "_channel", Context.javaToJS(
                    channel, scope));
            ScriptableObject.putProperty(scope, "_sender", Context.javaToJS(
                    sender, scope));
            ScriptableObject.putProperty(scope, "_message", Context.javaToJS(
                    message, scope));
            ScriptableObject.putProperty(scope, "_ircBot", Context.javaToJS(
                    ircBot, scope));

            // スクリプトを実行
            Object result = context.evaluateString(scope, scriptText,
                    "<script>", 1, null);

            if (result != null && !(result instanceof Undefined)) {
                // nullおよびundefined以外が返却された場合
                // 返却された値をメッセージ送信
                ircBot.sendMessage(channel, Context.toString(result));
            }
        } catch (Exception e) {
            logger.error("スクリプト実行にてエラーが発生しました。", e);
        }
    }

}
