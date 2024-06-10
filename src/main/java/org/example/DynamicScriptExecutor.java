package org.example;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.ProxyExecutable;

import java.io.File;
import java.util.Map;

public class DynamicScriptExecutor {

    public static void main(String[] args) {
        // Initialize the function registry
        FunctionRegistry registry = new FunctionRegistry();

        // Register Python functions
        registry.registerFunction("add", new MultiLanguageFunction("python", "src/main/java/org/example/functions.py", "add"));
        registry.registerFunction("multiply", new MultiLanguageFunction("python", "src/main/java/org/example/functions.py", "multiply"));

        // Register JavaScript functions
        registry.registerFunction("addJS", new MultiLanguageFunction("js", "src/main/java/org/example/functions.js", "add"));
        registry.registerFunction("multiplyJS", new MultiLanguageFunction("js", "src/main/java/org/example/functions.js", "multiply"));

        // User provided script in Python
        String userScript = "result_add = add(5, 3)\n" +
                "result_multiply = multiply(5, 3)\n" +
                "result_add_js = addJS(5, 3)\n" +
                "result_multiply_js = multiplyJS(5, 3)\n" +
                "print(result_add)\n" +
                "print(result_multiply)\n" +
                "print(result_add_js)\n" +
                "print(result_multiply_js)\n";

        // Execute the user script
        executeUserScript(userScript, registry);
    }

    public static void executeUserScript(String script, FunctionRegistry registry) {
        try (Context context = Context.newBuilder("python").allowAllAccess(true).build()) {

            // Create a new bindings object to hold the functions
            Value bindings = context.getBindings("python");

            // Inject registered functions into the context
            for (Map.Entry<String, ScriptFunction> entry : registry.getAllFunctions().entrySet()) {
                String functionName = entry.getKey();
                ScriptFunction function = entry.getValue();

                // Create a proxy for the function
                ProxyExecutable proxy = args -> function.execute(args);
                bindings.putMember(functionName, proxy);
            }

            // Evaluate the user script with the injected functions
            context.eval("python", script);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
