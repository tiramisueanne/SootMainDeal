# SootMainDeal #
An extension to Soot which allows FlowDroid to recognize loop parameters and conditions as taint sinks.

## The Process ##
1) Build or acquire an APK  
2) Modify the APK with soot  
3) Run the modified APK through FlowDroid  

## Building an initial APK from Java source code (in Android Studio) ##
http://stackoverflow.com/questions/16709848/build-unsigned-apk-file-with-android-studio

## Soot ##
1) Change the path of your Java rt.jar in the Options.v().set_soot_classpath line of sootTransformation.java  
2) To Compile (in soot directory)
```
javac -cp soot-trunk.jar sootTransformation.java myLoopInstrument.java OuterClass.java
```

3) To Run (in soot directory)
```
java -cp ./soot-trunk.jar:./:path_to_java_rt.jar:./platforms sootTransformation -android-jars ./platforms -process-dir path_to_apk.apk
```

4) Get your new APK file from the newly created sootOutput directory  
5) If you would like to run Soot again, remove the sootOutput directory (Soot does not like overwriting files)

### Possible Java rt.jar locations ###
* /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar [linux]
* /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre/lib/rt.jar [macOS]
* $(/usr/libexec/java_home)/jre/lib/rt.jar [works on bash shell in macOS]

### Example Run (in soot directory) ###
```
java -cp ./soot-trunk.jar:./:/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre/lib/rt.jar:./platforms sootTransformation -android-jars ./platforms -process-dir ../apps/Loop1Modified.apk
```

## FlowDroid ##
1) To Run (in top-level directory)
java -jar flowdroid/FlowDroid.jar path_to_apk.apk path_to_android_sdk

2) 

### Example Run (in top-level directory) ###
```
java -jar flowdroid/FlowDroid.jar apps/Loop1Modified.apk /Users/ekaminsky/Library/Android/sdk
```







