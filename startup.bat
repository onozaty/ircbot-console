@echo off
rem ---------------------------------------------------
rem  ircbot Startup.
rem ---------------------------------------------------

set IRCBOT_CLASSPATH=.;./lib_jetty/jetty-6.1.21.jar;./lib_jetty/jetty-util-6.1.21.jar;./lib_jetty/servlet-api-2.5-20081211.jar

java -classpath %IRCBOT_CLASSPATH% JettyRun ircbot.war
