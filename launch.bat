@echo off
java -cp RSBot-5058.jar;./bin/ org.powerbot.Boot -Xss6m -Xmx2048m -XX:MaxPermSize=512m -XX:+CMSClassUnloadingEnabled -XX:+UseCodeCacheFlushing -XX:-UseSplitVerifier -XX:+UseConcMarkSweepGC -debug
pause