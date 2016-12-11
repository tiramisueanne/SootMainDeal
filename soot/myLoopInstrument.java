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
        Scene.v().addBasicClass("java.lang.Object");

        LoopFinder lf = new LoopFinder();
        lf.transform(b);

        Collection<Loop> loops = lf.loops();

        if(loops.isEmpty()) return;

        final PatchingChain<Unit> units = b.getUnits();
        SootClass c = Scene.v().getSootClass("OuterClass");
        c.setApplicationClass();
      
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("OuterClass"));
        b.getLocals().add(tmpRef);

        Iterator<Loop> lIt = loops.iterator();
        while(lIt.hasNext()){
            Loop loop = lIt.next();
            Collection<Stmt> exits = loop.getLoopExits();
            Iterator<Stmt> eIt = exits.iterator();
            while(eIt.hasNext()){
            Stmt ex = eIt.next();
 
            if(ex instanceof IfStmt) {
           
                IfStmt head = (IfStmt) ex;
                Value cond = head.getCondition();
                if(cond instanceof ConditionExpr){
                    ConditionExpr condEx = (ConditionExpr)head.getCondition();
                   
                    Value op1 = condEx.getOp1();
                    Value op2 = condEx.getOp2(); 
                    System.out.println("op1: "+op1+", type: "+op1.getType()+", op2: "+op2+", type:"+op2.getType()); 
                    invokeMethod(units, tmpRef, head, op1);
                    invokeMethod(units, tmpRef, head, op2);
               }     
               else {
                    System.out.println("HELP CONDITION IS NOT A CONDITIONEXPR, fix");
                    System.out.println("cond is: "+cond.getClass()+": "+cond);
                    //System.exit(1);              
               }
          }
          else {
                System.out.println("HELP HEADER IS NOT AN IF STATEMENT, fix it");
                System.out.println("header is: "+ex.getClass()+": "+ex);
                //System.exit(2);
                // Ignore JExitMonitorStmt, JEnterMonitorStmt, JAssignStmt, JInvokeStmt, JGotoStmt, 
          }

        }
       }  
    }

    public void invokeMethod(PatchingChain<Unit> units, Local tmpRef, Stmt head, Value op){
        Type t = op.getType();
        SootMethod ourMethod = null;

        if (t instanceof RefLikeType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(java.lang.Object)");
        }
        else if (t instanceof IntType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(int)");
        }
        else if (t instanceof BooleanType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(boolean)");
        }
        else if (t instanceof ByteType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(byte)");
        }
        else if (t instanceof CharType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(char)");
        }
        else if (t instanceof DoubleType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(double)");
        }
        else if (t instanceof FloatType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(float)");
        }
        else if (t instanceof LongType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(long)");
        }
        else if (t instanceof ShortType){
            ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(short)"); 
        }

        if (ourMethod != null){
            units.insertBefore(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(
            tmpRef, ourMethod.makeRef(), op)), head); 
        }
        else {
            System.out.println("invoking method on unknown type: "+t.getClass());
        }         
    } 
}
