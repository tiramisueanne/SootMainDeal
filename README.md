# SootMainDeal #
An extension to Soot which allows FlowDroid to recognize loop parameters and conditions as taint sinks.

## The Process ##
1) Build or acquire an APK  
2) Modify the APK with soot  
3) Run the modified APK through FlowDroid  

## Building an initial APK from Java source code (in Android Studio) ##
http://stackoverflow.com/questions/16709848/build-unsigned-apk-file-with-android-studio

## Soot ##
1) Change the path of your Java rt.jar in the `String path_to_rt_jar = "...";` line of sootTransformation.java  
2) To compile (in /soot directory)
```
javac -cp soot-trunk.jar sootTransformation.java myLoopInstrument.java OuterClass.java
```

3) To run (in /soot directory)
```
java -cp ./soot-trunk.jar:./:path_to_java_rt.jar:./platforms sootTransformation -android-jars ./platforms -process-dir path_to_apk.apk
```

4) Get your new APK file from the newly created /soot/sootOutput directory  
5) If you would like to run Soot again, remove the /soot/sootOutput directory (Soot does not like overwriting files)

#### Possible Java rt.jar locations ####
* /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar [Linux]
* /usr/lib/jvm/java-8-oracle/jre/lib/rt.jar [UT lab computers]
* /Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre/lib/rt.jar [macOS]
* $(/usr/libexec/java_home)/jre/lib/rt.jar [works on bash shell in macOS]

#### Example Compilation & Run (in /soot directory) ####
```
javac -cp soot-trunk.jar sootTransformation.java myLoopInstrument.java OuterClass.java

java -cp ./soot-trunk.jar:./:/Library/Java/JavaVirtualMachines/jdk1.8.0_45.jdk/Contents/Home/jre/lib/rt.jar:./platforms sootTransformation -android-jars ./platforms -process-dir ../apps/Loop1Modified.apk
```

## Soot -> FlowDroid ##
To make it easier to run the new APK produced by Soot through FlowDroid, I would recommend moving the APK to the /apps directory. I would then remove the /soot/sootOuput directory to allow for future Soot runs.

#### Example Move & Delete ###
```
mv sootOutput/Loop1Modified.apk ../apps/Loop1NewAPK.apk

rm -rf sootOutput

cd ..
```

## FlowDroid ##
1) To run (in top-level directory)
```
java -jar flowdroid/FlowDroid.jar path_to_apk.apk path_to_android_sdk
```

2) Examine the output produced by FlowDroid. If the output contains the following line, no taints were found.
```
[main] ERROR soot.jimple.infoflow.Infoflow - No sources or sinks found, aborting analysis
```

Otherwise, all flow to sink violations will appear as:
```
Found a flow to sink virtualinvoke $rX.<sink_class: return_type sink_method(arg_types)>(args), from the following sources:
	- virtualinvoke $rX.<source_class: return_type source_method(arg_types)>(args) (in <parent_class: void parent_method(arg_types)>)
		on Path [Comment-separated IR stack trace]
	- second source
	- third source
	- ...
```

FlowDroid will create its own empty /sootOuput directory which can be ignored for the purposes of this analysis.

#### Example Run (in top-level directory) ####
```
java -jar flowdroid/FlowDroid.jar apps/Loop1NewAPK.apk /Users/ekaminsky/Library/Android/sdk
```

## Notes ##

The test app used throughout the previous examples can be found in apps/Loop1Modified. It builds off of the Loop1 sample code available in DroidBench (https://github.com/secure-software-engineering/DroidBench), which identifies the following sink.
```
<android.telephony.SmsManager: void sendTextMessage(java.lang.String,java.lang.String,java.lang.String,android.app.PendingIntent,android.app.PendingIntent)> android.permission.SEND_SMS -> _SINK_
```

To limit the analysis to just our contributions, this sink was removed from SourcesAndSinks.txt





