@ECHO OFF
set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;../lib/jl1.0.jar

%JAVA_HOME%\bin\java -Xms128m -Xmx384m -Xnoclassgc client.Client 1
