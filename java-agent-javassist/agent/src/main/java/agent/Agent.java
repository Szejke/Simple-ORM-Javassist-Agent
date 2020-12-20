package agent;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
                if (s.contains("other")) {
                    try {
                        String pathClassCurrent = s.replace("/", ".");
                        ClassPool cp = ClassPool.getDefault();
                        CtClass cc = cp.get(pathClassCurrent);

                        if (cc.isFrozen()) {
                            cc.defrost();
                        }
                        // nie używane Dla PrintArgumentsTranslator
                        if (s.contains("Main")) {
                            Loader cl = new Loader(cp);
                            cl.addTranslator(cp, new PrintArgumentsTranslator());
                            cl.run(pathClassCurrent, new String[0]);
                            cc.defrost();
                        }
                        if (s.contains("Main")) {
                            cp.importPackage("agent.DataBase");
                            cp.importPackage("other.OneToOne;");
                            cp.importPackage("java.util.*");
                            cp.importPackage("java.util.ArrayList");
                            cp.importPackage("java.lang.reflect.Field");
                            cp.importPackage("java.lang.reflect.InvocationTargetException");

                            CtField f = CtField.make("public static String INHERITANCE_ROOT = \"java.lang.Object\";", cc);
                            cc.addField(f);

                            MakeMethod makeMethod = new MakeMethod();
                            CtMethod getClassTree = CtNewMethod.make(makeMethod.makeMainMathod(), cc);
                            cc.addMethod(getClassTree);
                            CtMethod m = cc.getDeclaredMethod("main");
                            m.addLocalVariable("elapsedTime", CtClass.longType);
                            m.insertBefore("elapsedTime = System.currentTimeMillis(); ");
                            m.insertAfter("" +
                                    "System.out.println(listOfObjects); " +
                                    " getClassTree(listOfObjects);" +
                                    "elapsedTime = System.currentTimeMillis() - elapsedTime;  " +
                                    " System.out.println(\"Method Executed in ms: \" + elapsedTime); " +
                                    "");
                        }

                        byte[] byteCode = cc.toBytecode();

                    cc.detach();
                    return byteCode;
                } catch (Exception ex) {
                    ex.printStackTrace();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                }
                return null;
            }
        });

    }

}
