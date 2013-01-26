package com.enjoyxstudy.ircbotconsole;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * WEBアプリケーションの起動/停止時をIrcBotServerに通知するためのServletです。
 *
 * @author onozaty
 */
public class IrcBotServlet extends HttpServlet {

    /** serialVersionUIDです。 */
    private static final long serialVersionUID = 1L;

    /**
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException {
        super.init();

        // 環境変数から取得
        // JVM変数で、 -Dircbot.home="ディレクトリ名"
        String homeDirectory = System.getProperty("ircbot.home");

        if (homeDirectory == null) {
            // 取得できなかった場合
            // ユーザのホームに作成する
            homeDirectory = new File(System.getProperty("user.home"), ".ircbot")
                    .getAbsolutePath();
        }

        // IRCボットを起動
        try {
            IrcBotServer.getInstance().init(homeDirectory);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * @see javax.servlet.GenericServlet#destroy()
     */
    @Override
    public void destroy() {
        super.destroy();

        // IRCボットを停止
        try {
            IrcBotServer.getInstance().destroy();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
