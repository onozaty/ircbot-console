package com.enjoyxstudy.ircbotconsole;

import java.io.IOException;
import java.io.InputStreamReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.tools.shell.Global;

/**
 * スクリプト実行のためのユーティリティクラスです。
 *
 * @author onozaty
 */
public class ScriptUtils {

    /**
     * コンテキストを作成します。
     *
     * @return コンテキスト
     */
    public static Context createContext() {

        ContextFactory contextFactory = new ContextFactory();
        return contextFactory.enterContext();
    }

    /**
     * スコープを作成、初期化します。
     *
     * @param context コンテキスト
     * @return スコープ
     * @throws IOException
     */
    public static Scriptable initScope(Context context) throws IOException {

        // Rhino Shellの関数を使えるように
        Scriptable scope = context.initStandardObjects(new Global(context));

        // JSON
        InputStreamReader reader = new InputStreamReader(ScriptUtils.class
                .getResourceAsStream("/js/json2.js"));
        try {
            context.evaluateReader(scope, reader, "json2.js", 1, null);
        } finally {
            reader.close();
        }

        return scope;
    }
}
