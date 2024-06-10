package org.example;

public class MultiLanguageFunctionRegistryDemo {

    public static void main(String[] args) {
        // Initialize the function registry
        FunctionRegistry registry = new FunctionRegistry();

        // Register Python functions
        registry.registerFunction("addPython", new MultiLanguageFunction("python", "src/main/java/org/example/functions.py", "add"));
        registry.registerFunction("multiplyPython", new MultiLanguageFunction("python", "src/main/java/org/example/functions.py", "multiply"));

        // Register JavaScript functions
        registry.registerFunction("addJS", new MultiLanguageFunction("js", "src/main/java/org/example/functions.js", "add"));
        registry.registerFunction("multiplyJS", new MultiLanguageFunction("js", "src/main/java/org/example/functions.js", "multiply"));

        // Retrieve and invoke the functions
        ScriptFunction addPythonFunction = registry.getFunction("addPython");
        ScriptFunction multiplyPythonFunction = registry.getFunction("multiplyPython");
        ScriptFunction addJSFunction = registry.getFunction("addJS");
        ScriptFunction multiplyJSFunction = registry.getFunction("multiplyJS");

        // Execute the functions
        System.out.println("Add (Python): " + addPythonFunction.execute(5, 3)); // Output: 8
        System.out.println("Multiply (Python): " + multiplyPythonFunction.execute(5, 3)); // Output: 15
        System.out.println("Add (JavaScript): " + addJSFunction.execute(5, 3)); // Output: 8
        System.out.println("Multiply (JavaScript): " + multiplyJSFunction.execute(5, 3)); // Output: 15
    }
}
