package agent;

import javassist.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// Klasa nie używana lecz obrazująca wywoływania po każdej metodzie
public class PrintArgumentsTranslator implements Translator {

    public static ArrayList listObject = new ArrayList();

    public void start(ClassPool pool) {

    }

    @Override
    public void onLoad(ClassPool pool, String cname)
            throws NotFoundException, CannotCompileException {
        CtClass c = pool.get(cname);

        if (c.getSimpleName().equals("Main")) {
        }

        for (CtConstructor m : c.getConstructors()) {
            insertLogStatement(c, m);
        }
    }

    private void insertLogStatement(CtClass c, CtBehavior m) {
        try {
            List<String> args = new LinkedList<String>();
            for (int i = 0; i < m.getParameterTypes().length; i++)
                args.add("$" + (i + 1));


            String toPrint =
                    "\"" + c.getName()
                            + args.toString()
                            .replace("[", "(\" + ")
                            .replace(",", " + \", \" + ")
                            .replace("]", "+\")\"");

        } catch (Exception e) {
            System.out.println(e);
        }
    }


}