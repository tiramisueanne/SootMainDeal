import soot.*;
import soot.options.*;

class Main {
    public static void main(String[] args) {
        Scene scene = Scene.v();
        Options.v().set_soot_classpath("./:/usr/lib/jvm/java-8-openjdk-amd64/jre/lib/rt.jar");
        //Options.v().set_soot_classpath("./");
        Options.v().set_allow_phantom_refs(false);
        SootClass cl = scene.loadClass("Main", SootClass.SIGNATURES);
    }
}
