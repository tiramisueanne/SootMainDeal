# SootMainDeal
This is the sootMain

To Compile:
javac -cp soot-trunk.jar sootTransformation.java myLoopInstrument.java OuterClass.java

To Run:
java -cp ./soot-trunk.jar:./:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:./platforms sootTransformation -android-jars ./platforms -process-dir HeLLoWorld.apk

Remove the sootOutput directory each time you run.

