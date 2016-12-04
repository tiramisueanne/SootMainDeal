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
        SootClass c = Scene.v().getSootClass("java.lang.System");
        c.setApplicationClass();
        SootMethod ourMethod = Scene.v().getSootClass("OuterClass").getMethod(
                                    "void ourMethod(java.lang.Object)");
        SootMethod ourMethodInt = Scene.v().getSootClass("OuterClass").getMethod("void ourMethod(int)");
       
        Local tmpRef = Jimple.v().newLocal("tmpRef", RefType.v("OuterClass"));
        b.getLocals().add(tmpRef);

        Iterator<Loop> lIt = loops.iterator();
        while(lIt.hasNext()){
            Loop loop = lIt.next();
            Stmt header = loop.getHead();
 
            if(header instanceof IfStmt) {
                //System.out.println("If statement found");
                IfStmt head = (IfStmt) header;
                Value cond = head.getCondition();
                if(cond instanceof ConditionExpr){
                    ConditionExpr condEx = (ConditionExpr)head.getCondition();
                    //System.out.println("cond class is: "+ condEx.getClass());
                    Value op1 = condEx.getOp1();
                    Value op2 = condEx.getOp2(); //do I need to check if one of these is a constant?
                    System.out.println("op1: "+op1+", type: "+op1.getType()+", op2: "+op2+", type:"+op2.getType()); 
                    
                    Type t1 = op1.getType();
                    if(t1 instanceof PrimType){
                        if(t1 instanceof IntType){
                            units.insertBefore(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(
                            tmpRef, ourMethodInt.makeRef(), op1)), head); 
                            System.out.println("Invoking int version");
                        }
                        else {
                            System.out.println("op1 is unsuportted Primtype "+t1.getClass());
                        }
                    } else if (t1 instanceof RefLikeType) {
                    
                    units.insertBefore(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(
                        tmpRef, ourMethod.makeRef(), op1)), head);
                   } else {
                       System.out.println("op1 unknown type "+op1.getClass());
                   }


                   Type t2 = op2.getType();
                   if(t2 instanceof PrimType){
                        if(t2 instanceof IntType){
                            units.insertBefore(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(
                            tmpRef, ourMethodInt.makeRef(), op2)), head); 
                            System.out.println("Invoking int version");
                        }
                        else {
                            System.out.println("op2 is unsuportted Primtype "+t2.getClass());
                        }
                    } else if (t2 instanceof RefLikeType) {
                    
                    units.insertBefore(Jimple.v().newInvokeStmt(Jimple.v().newVirtualInvokeExpr(
                        tmpRef, ourMethod.makeRef(), op2)), head);
                   } else {
                       System.out.println("op2 unknown type "+op2.getClass());
                   }
                }
                else {
                    System.out.println("HELP CONDITION IS NOT A CONDITIONEXPR, fix");
                    System.out.println("cond is: "+cond.getClass()+": "+cond);
                    //System.exit(1);               
                }
            }
            else {
                System.out.println("HELP HEADER IS NOT AN IF STATEMENT, fix it");
                System.out.println("header is: "+header.getClass()+": "+header);
                //System.exit(2);
            }

        }   
    }
}
