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
//import soot.jimple.toolkits.annotation.logic.*;

public class sootTransformation {
	public static void main(String[] args){
	Options.v().set_src_prec(Options.src_prec_apk);
	Options.v().set_output_format(Options.output_format_jimple);
	PackManager.v().getPack("jtp").add(new Transform("jtp.loopFinder", new LoopFinder()));
	soot.Main.main(args); // or Main.main(args); ????
	}
}
