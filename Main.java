import soot.*;
import soot.options.*;

class Main {
    public static void main(String[] args) {
        Scene scene = Scene.v();
        Options.v().set_soot_classpath("./");
        Options.v().set_allow_phantom_refs(false);
        SootClass cl = scene.loadClass("Main", SootClass.SIGNATURES);
    }
}
