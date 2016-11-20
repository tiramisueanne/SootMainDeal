How to Run:
java -cp ./soot-trunk.jar:./:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:./platforms sootTransformation -android-jars ./platforms -process-dir HeLLoWorld.apk  
Then you must remove "sootOutput" or else it'll just keep throwing errors
