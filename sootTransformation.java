import java.util.Iterator;
import java.util.Map;
import java.util.*;
import java.lang.*;

import soot.*;
import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.PackManager;
import soot.PatchingChain;
import soot.RefType;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Unit;
import soot.jimple.AbstractStmtSwitch;
import soot.jimple.InvokeExpr;
import soot.jimple.InvokeStmt;
import soot.jimple.Jimple;
import soot.jimple.StringConstant;
import soot.options.Options;
import soot.jimple.toolkits.annotation.logic.*;

public class sootTransformation {
	public static void main(String[] args){
	Options.v().set_src_prec(Options.src_prec_apk);
	Options.v().set_output_format(Options.output_format_dex);
    //Options.v().set_allow_phantom_refs(true); 
    Options.v().set_soot_classpath("./soot-trunk.jar:./:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar:./platforms/android--1/android.jar");
    Scene.v().addBasicClass("java.io.PrintStream",SootClass.SIGNATURES);
    Scene.v().addBasicClass("java.lang.System",SootClass.SIGNATURES);
    Scene.v().addBasicClass("OuterClass", SootClass.SIGNATURES);   
    //SootClass c = Scene.v().getSootClass("java.lang.System");
    //c.setApplicationClass();

	PackManager.v().getPack("jtp").add(new Transform("jtp.myLoopInstrument", myLoopInstrument.v()));
        
	soot.Main.main(args); // or Main.main(args); ????
	}
}
