@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;../lib/mail.jar;../lib/activation.jar

%JAVA_HOME%\bin\java -Xms128m -Xmx384m -Xnoclassgc main.Main