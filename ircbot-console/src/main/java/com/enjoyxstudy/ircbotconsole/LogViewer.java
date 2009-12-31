package com.enjoyxstudy.ircbotconsole;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import com.enjoyxstudy.ircbotconsole.command.LogWriteProcessor;

/**
 * ログファイルを表示するクラスです。
 *
 * @author onozaty
 */
public class LogViewer {

    /**
     * 指定チャンネルのログファイル一覧を返却します。
     *
     * @param channel チャンネル名
     * @return ログファイル一覧
     */
    public String[] getFiles(String channel) {

        File logDirectory = getLogDirectory(channel);
        File[] logFiles = logDirectory.listFiles();

        String[] fileNames = new String[logFiles.length];
        for (int i = 0; i < logFiles.length; i++) {
            fileNames[i] = logFiles[i].getName();
        }

        Arrays.sort(fileNames, new Comparator<String>() {
            public int compare(String o1, String o2) {
                // 降順で
                return -(o1.compareTo(o2));
            }
        });
        return fileNames;
    }

    /**
     * ログ内容を返却します。
     *
     * @param channel チャンネル
     * @param fileName ログファイル名
     * @return ログ内容
     * @throws IOException
     */
    public String[] getLog(String channel, String fileName) throws IOException {

        File logFile = new File(getLogDirectory(channel), fileName);

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(logFile), LogWriteProcessor.ENCODING));

        ArrayList<String> logList = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            logList.add(line);
        }

        return logList.toArray(new String[logList.size()]);
    }

    /**
     * ログディレクトリを取得します。
     *
     * @param channel チャンネル名
     * @return ログディレクトリ
     */
    private File getLogDirectory(String channel) {

        Config config = IrcBotServer.getInstance().getConfig();
        return new File(new File(IrcBotServer.getInstance().getHomeDirectory(),
                config.getLogDirectory()), channel.substring(1));
    }
}
