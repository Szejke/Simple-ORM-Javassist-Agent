package agent;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

//Klasa główna Agenta który wporwadza Kod bytowy
public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        // sprawdza każdy pakiet w projekcie
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader classLoader, String s, Class<?> aClass, ProtectionDomain protectionDomain, byte[] bytes) throws IllegalClassFormatException {
                // jezeli jest to w pakiecie other zacznij działanie
                if (s.contains("other")) {
                    try {
                        String pathClassCurrent = s.replace("/", ".");
                        // przypisanie kalsy aktualnej
                        ClassPool cp = ClassPool.getDefault();
                        CtClass cc = cp.get(pathClassCurrent);
                        // jezeli jest ona juz uzywana to jest zablokowana odblokuj ja
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
                        // jeżeli znajdziesz Klase Main to
                        if (s.contains("Main")) {
                            //dodawanie do klasy importów które są niezbędne dla naszej metody
                            cp.importPackage("agent.DataBase");
                            cp.importPackage("other.OneToOne;");
                            cp.importPackage("java.util.*");
                            cp.importPackage("java.util.ArrayList");
                            cp.importPackage("java.lang.reflect.Field");
                            cp.importPackage("java.lang.reflect.InvocationTargetException");


                            // tworzenie zmiennej globalnej
                            CtField f = CtField.make("public static String INHERITANCE_ROOT = \"java.lang.Object\";", cc);
                            cc.addField(f);

                           // stworzenie metody z klasy MakeMetgod
                            MakeMethod makeMethod = new MakeMethod();
                            CtMethod getClassTree = CtNewMethod.make(makeMethod.makeMainMathod(), cc);
                            cc.addMethod(getClassTree);
                            // gdy znajdziesz metode main to
                            CtMethod m = cc.getDeclaredMethod("main");
                            //wprowadzanie zmiennej lokalnej do metody
                            m.addLocalVariable("elapsedTime", CtClass.longType);
                            //wprowadzanie na początku metody zmienną która pobiera aktualny czas dla testów wydajnosciowych
                            m.insertBefore("elapsedTime = System.currentTimeMillis(); ");
                            // wprowadzanie na końcu metody main wywołania naszej stworzonej metody oraz obiczanie czasu wykonania programu
                            m.insertAfter("" +
                                    "System.out.println(listOfObjects); " +
                                    " getClassTree(listOfObjects);" +
                                    "elapsedTime = System.currentTimeMillis() - elapsedTime;  " +
                                    " System.out.println(\"Method Executed in ms: \" + elapsedTime); " +
                                    "");
                        }

                        // bytecode jak wyglada w naszym programie
                        byte[] byteCode = cc.toBytecode();

                        // wylacz klase
                    cc.detach();
                    // zwroc bytecode
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
