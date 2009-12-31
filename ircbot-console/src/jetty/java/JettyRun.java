import java.io.File;

import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Jetty上で起動するためのクラスです。
 *
 * @author onozaty
 */
public class JettyRun {

    /** デフォルトのポート番号です。 */
    private static final int DEFAULT_PORT = 8080;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String warFilePath = args[0];
        String warFileName = new File(warFilePath).getName();
        String contextPath = "/" + warFileName.substring(0, warFileName.indexOf('.'));

        int port = DEFAULT_PORT;
        if (args.length == 2) {
            port = Integer.parseInt(args[1]);
        }

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setPort(port);
        Server server = new Server();
        server.setConnectors(new Connector[] { connector });
        WebAppContext web = new WebAppContext();

        // WARフォルダのパスとコンテキストを設定
        web.setWar(warFilePath);
        web.setContextPath(contextPath);
        server.addHandler(web);
        server.start();
        server.join();
    }
}
