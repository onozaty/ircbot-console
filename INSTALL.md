# インストール関連

## ircbot console の起動方法

2パターンの実行方法があります。

### 1. サーブレットコンテナにircbot.warをデプロイして実行

リリースファイルの``ircbot.war``をサーブレットコンテナ(Tomcat等)にデプロイすることにより実行できます。

### 2. 起動スクリプトによる実行

リリースファイルのアーカイブ(ircbot_console-X.X.zip)を解凍し、その中の起動スクリプト(``start.sh`` or ``start.bat``)を実行することにより、サーブレットコンテナのJettyで実行できます。

簡易的に試す場合には、これで十分ですが、実際に運用するとなると、いちいち起動/終了スクリプトを叩く必要がでてきてしまうため、長く運用する場合には、サーブレットコンテナにデプロイする方をおすすめします。

## IRC環境構築

参考としてircサーバのインストール方法を下記に記載します。

CentOS 7.1 での ngIRCdインストール方法になります。

* [ngIRCd: Next Generation IRC Daemon](http://ngircd.barton.de/ "ngIRCd: Next Generation IRC Daemon")

ngIRCdはEPELリポジトリにて公開されていますので、yumのリポジトリとしてEPELを追加し、ngIRCdをインストールします。

```
[root@localhost ~]# yum install -y epel-release
[root@localhost ~]# yum install -y ngircd
```

ngIRCdの設定ファイル(``/etc/ngircd.conf``)の``Listen``の行をコメントアウトします。(コメントアウトすることで、デフォルトの0.0.0.0となり、すべてのIPアドレスに対してlistenします)

```
;Listen = 127.0.0.1
```

あとは、ngIRCdをサービスに登録し、起動するだけです。
```
[root@localhost ~]# systemctl enable ngircd.service
[root@localhost ~]# systemctl start ngircd.service
```

適当なチャットクライアント(LimeChatなど)から、該当サーバに対して接続できることが確認できればOKです。(ポートは6667)

パスワードなどを設定したい場合には、``/etc/ngircd.conf``をいろいろ触ってみてください。

## Tomcatのインストール

サーブレットコンテナとしてTomcatを使用し、ircbotをデプロイするところまでを記載します。

ここでも CentOS 7.1 を対象とします。

まずTomcatのインストールです。(Javaもこれで一緒に入ります)
```
[root@localhost ~]# yum install -y tomcat
```

あとは、Tomatをサービスに登録し、起動するだけです。
```
[root@localhost ~]# systemctl enable tomcat.service
[root@localhost ~]# systemctl start tomcat.service
```

ircbot.war は GitHubから最新のものを取得し、Tomcatのwebapps配下に配置します。

```
[root@localhost ~]# wget https://github.com/onozaty/ircbot-console/raw/master/dist/ircbot.war -O /usr/share/tomcat/webapps/ircbot.war
```

これでircbotがデプロイされます。

ircbot console の各種設定方法については、下記ページをご参照ください。

* [IRCbot Console](http://www.enjoyxstudy.com/ircbotconsole/ "IRCbot Console")
