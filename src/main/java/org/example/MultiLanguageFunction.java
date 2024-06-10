package org.example;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;

public class MultiLanguageFunction implements ScriptFunction {

    private final String language;
    private final String scriptPath;
    private final String functionName;

    public MultiLanguageFunction(String language, String scriptPath, String functionName) {
        this.language = language;
        this.scriptPath = scriptPath;
        this.functionName = functionName;
    }

    @Override
    public Object execute(Object... args) {
        try (Context context = Context.newBuilder(language).allowAllAccess(true).build()) {
            context.eval(Source.newBuilder(language, new File(scriptPath)).build());
            Value function = context.getBindings(language).getMember(functionName);
            return function.execute(args).as(Object.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
