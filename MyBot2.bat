ECHO ON
dir c:\Users\Kevin\Desktop\bot\sneetoswoman
javac -classpath pircbot.jar;JMegaHal.jar;twitter4j.jar;twitter4jasync.jar;twitter4jmedia.jar;twitter4jstream.jar;guava-18.0.jar;. *.java
java -Xms64m -Xmx512m -classpath pircbot.jar;JMegaHal.jar;twitter4j.jar;twitter4jasync.jar;twitter4jmedia.jar;twitter4jstream.jar;guava-18.0.jar;. MyBotMain
cmd /K