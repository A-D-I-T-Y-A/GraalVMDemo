package org.example;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class FunctionRegistry {

    private Map<String, ScriptFunction> registry;

    public FunctionRegistry() {
        this.registry = new ConcurrentHashMap<>();
    }

    public void registerFunction(String name, ScriptFunction function) {
        registry.put(name, function);
    }

    public ScriptFunction getFunction(String name) {
        return registry.get(name);
    }

    public boolean hasFunction(String name) {
        return registry.containsKey(name);
    }

    public void removeFunction(String name) {
        registry.remove(name);
    }

    public Map<String, ScriptFunction> getAllFunctions() {
        return registry;
    }
}
