package com.enjoyxstudy.ircbotconsole;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import com.enjoyxstudy.ircbotconsole.command.LogWriteProcessor;
import com.enjoyxstudy.ircbotconsole.command.RecieveCommandHandler;
import com.enjoyxstudy.ircbotconsole.notifier.CyclicScheduler;
import com.enjoyxstudy.ircbotconsole.notifier.MessageNotifier;
import com.enjoyxstudy.ircbotconsole.notifier.Notifier;
import com.enjoyxstudy.ircbotconsole.notifier.NotifierThread;
import com.enjoyxstudy.ircbotconsole.notifier.RssNotifier;

/**
 * @author onozaty
 */
public class IrcBotTest extends TestCase {

    /**
     * @throws IOException
     *             サーバに接続できなかった場合
     * @throws IrcException
     *             IRCに加われなかった場合
     * @throws NickAlreadyInUseException
     *             ニックネームが既に利用されていた場合
     * @throws NoSuchAlgorithmException
     * @throws InterruptedException
     */
    public void testConnectStringIntStringString()
            throws NickAlreadyInUseException, IOException, IrcException,
            NoSuchAlgorithmException, InterruptedException {

        RecieveCommandHandler recieveCommandHandler = new RecieveCommandHandler();
        recieveCommandHandler.addProcessor(null, new LogWriteProcessor(
                "work/log"));

        IrcBot bot = new IrcBot(recieveCommandHandler);

        // localhostにIRCサーバ立てておく
        bot.setServer("localhost");
        bot.setPort(6667);
        bot.setNickName("ircbotplus");
        bot.connect();

        bot.joinChannel("#hoge");

        ArrayList<Notifier> notifierList = new ArrayList<Notifier>();

        notifierList.add(new RssNotifier("#hoge", new CyclicScheduler(60),
                "http://b.hatena.ne.jp/entrylist?sort=hot&threshold=&mode=rss",
                null, null, null, new File("work")));

        notifierList.add(new MessageNotifier("#hoge", new CyclicScheduler(30),
                "30秒毎に発言します。"));

        NotifierThread notifierThread = new NotifierThread(bot, notifierList);

        new Thread(notifierThread).start();

        Thread.sleep(5 * 60 * 1000); // 5分間
    }
}
