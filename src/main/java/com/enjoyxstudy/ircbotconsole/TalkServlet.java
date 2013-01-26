package com.enjoyxstudy.ircbotconsole;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author onozaty
 */
public class TalkServlet extends HttpServlet {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    /**
     *
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        // リクエストパラメータ取得
        request.setCharacterEncoding("UTF-8");
        String message = request.getParameter("message");
        String channel = request.getParameter("channel");

        if (message == null || message.equals("") || channel == null
                || channel.equals("")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // リモートIPアドレス
        String remoteAddr = request.getRemoteAddr();

        // メッセージを分割
        // ->改行があった場合、複数の発言として送信
        String[] messages = message.split("\r\n|\r|\n");

        IrcBotServer ircBotServer = IrcBotServer.getInstance();
        ircBotServer.sendMessageFromHttp(channel, messages, remoteAddr);

    }

}
