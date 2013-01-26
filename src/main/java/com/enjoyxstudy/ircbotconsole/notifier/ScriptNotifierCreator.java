package com.enjoyxstudy.ircbotconsole.notifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ScriptNotifierを生成するクラスです。
 *
 * @author onozaty
 */
public class ScriptNotifierCreator {

    /**
     * ScriptNotifierを生成します。
     *
     * @param channel チャンネル名
     * @param config 設定
     * @return ScriptNotifer
     */
    public ScriptNotifier createNotifier(String channel,
            ScriptNotifierConfig config) {

        Scheduler scheduler = null;

        switch (config.getType()) {
        case ScriptNotifierConfig.TYPE_CYCLIC:
            scheduler = new CyclicScheduler(config.getCyclicSpan() * 60);
            break;
        case ScriptNotifierConfig.TYPE_DAILY:
            scheduler = new DailyScheduler(config.getDailyHour(), config
                    .getDailyMinute());
            break;
        }

        return new ScriptNotifier(channel, scheduler, config.getScriptText());
    }

    /**
     * ScriptNotifierを生成します。
     *
     * @param scriptNotifierConfigMap チャンネル名とScriptNotifier設定のマップ
     * @return ScriptNotifierのリスト
     */
    public List<Notifier> createNotifiers(
            HashMap<String, ArrayList<ScriptNotifierConfig>> scriptNotifierConfigMap) {

        ArrayList<Notifier> notifierList = new ArrayList<Notifier>();

        for (String channel : scriptNotifierConfigMap.keySet()) {
            for (ScriptNotifierConfig config : scriptNotifierConfigMap
                    .get(channel)) {
                notifierList.add(createNotifier(channel, config));
            }
        }

        return notifierList;
    }
}
