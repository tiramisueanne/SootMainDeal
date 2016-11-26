import soot.*;
import soot.toolkits.graph.*;
import soot.jimple.*;

import java.util.*;

import soot.toolkits.scalar.*;
import soot.tagkit.*;
import soot.jimple.toolkits.annotation.logic.Loop;
import soot.jimple.toolkits.annotation.logic.LoopFinder;

public class myLoopInstrument extends BodyTransformer {

    private static myLoopInstrument instance = new myLoopInstrument();
    private myLoopInstrument() {}

    public static myLoopInstrument v() {return instance;}
    protected void internalTransform(Body b, String phaseName, Map options){
    
        LoopFinder lf = new LoopFinder();
        lf.transform(b);

        Collection<Loop> loops = lf.loops();

        if(loops.isEmpty()) return;

        final PatchingChain<Unit> units = b.getUnits();
        SootClass c = Scene.v().getSootClass("OuterClass");
        SootMethod ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(java.lang.Object)");
        
        //make a Test object to call ourMethod() on
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("OuterClass"));
        b.getLocals().add(tmpRef);

        Iterator<Loop> lIt = loops.iterator(); //liiiiiit
        while(lIt.hasNext()){
            Loop loop = lIt.next();
            Stmt header = loop.getHead();

/* Checks to see if I can cast stuff
 
            System.out.println("Head is class: "+header.getClass());
*/
            if(header instanceof IfStmt) {
                System.out.println("If statement found");
                //IfStmt head = (IfStmt) header;
                List<Value> cond = head.getArgs(); 
                units.insertBefore(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(
                    tmpRef, ourMethod.makeRef(), cond)), head);
            }

        }   
    }
}
