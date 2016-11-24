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
        SootMethod ourMethod = new SootMethod(); //instantiate this properly
        Local tmpRef = addTmpRef(b);  //I have no idea what this is supposed to be

        Iterator<Loop> lIt = loops.iterator();
        while(lIt.hasNext()){
            Loop loop = lIt.next();
            Stmt header = loop.getHead();

/* Checks to see if I can cast stuff
 
            System.out.println("Head is class: "+header.getClass());

            if( !header instanceof IfStmt) {System.out.println("header isn't an if statement, help");
*/

            IfStmt head = (IfStmt) header; //don't know if this is safe/works
            if(!head.isNull());
            Value cond = head.getCondition(); 
            //insert the statement
            units.insertBefore(Jimple.v().newInvokeStmt(
                Jimple.v().newVirtualInvokeExpr( tmpref, ourMethod.makeRef(), cond)
            ,header));

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
