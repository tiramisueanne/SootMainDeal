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

        Iterator<Loop> lIt = loops.iterator();
        while(lIt.hasNext()){
            Loop loop = lIt.next();
            Stmt header = loop.getHead();
Local tmpRef = addTmpRef(b);
								Local tmpString = addTmpString(b);
								
								  // insert "tmpRef = java.lang.System.out;" 
						        units.insertBefore(Jimple.v().newAssignStmt( 
						                      tmpRef, Jimple.v().newStaticFieldRef( 
						                      Scene.v().getField("<java.lang.System: java.io.PrintStream out>").makeRef())), header);

						        // insert "tmpLong = 'HELLO';" 
						        units.insertBefore(Jimple.v().newAssignStmt(tmpString, 
						                      StringConstant.v("HELLO")), header);
						        
						        // insert "tmpRef.println(tmpString);" 
						        SootMethod toCall = Scene.v().getSootClass("java.io.PrintStream").getMethod("void println(java.lang.String)");                    
						        units.insertBefore(Jimple.v().newInvokeStmt(
						                      Jimple.v().newVirtualInvokeExpr(tmpRef, toCall.makeRef(), tmpString)), header);
						        
						        //check that we did not mess up the Jimple
//						        b.validate();

/*            System.out.println("Head is class: "+header.getClass());

            if( !header instanceof IfStmt) {System.out.println("header isn't an if statement, help");
            IfStmt head = (IfStmt) header; //don't know if this is safe/works
            if(!head.isNull());
            Value cond = head.getCondition(); 
            //insert the statement
            units.insertBefore(Jimple.v().newInvokeStmt(,header));
*/
        }   
    }

    private static Local addTmpRef(Body body)
    {
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("java.io.PrintStream"));
        body.getLocals().add(tmpRef);
        return tmpRef;
    }
    
    private static Local addTmpString(Body body)
    {
        Local tmpString = Jimple.v().newLocal("tmpString", RefType.v("java.lang.String")); 
        body.getLocals().add(tmpString);
        return tmpString;
    }
 
}
