package soot.jimple.toolkits.annotation.logic;

import java.util.Iterator;
import java.util.Map;

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
import java.util.*;

public class sootTransformation {
	public static void main(String[] args){
	Options.v().set_src_prec(Options.src_prec_apk);
        Options.v().set_allow_phantom_refs(true);
	Options.v().set_output_format(Options.output_format_dex);

        //myLoopFinder loopy =  myLoopFinder.v();
	PackManager.v().getPack("jtp").add(new Transform("jtp.myLoopFinder", new BodyTransformer(){

//            package soot.jimple.toolkits.annotation.logic;
            @Override
            protected void internalTransform(Body b, String phaseName, Map options){
                LoopFinder lf = new LoopFinder();
//                lf.internalTransform(b, phaseName, options);
                Collection<Loop> loops = lf.loops();
            }
        }));
        soot.Main.main(args); // or Main.main(args); ????
	}
}
