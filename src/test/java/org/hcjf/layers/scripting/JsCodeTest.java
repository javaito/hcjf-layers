package org.hcjf.layers.scripting;

import org.hcjf.layers.Layers;
import org.hcjf.utils.Introspection;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class JsCodeTest {

    @Test
    public void test() {
        CodeEvaluator codeEvaluator = Layers.get(CodeEvaluator.class, "js");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name", "javier");
        parameters.put("age", 40);
        parameters.put("date", new Date());
        parameters.put("map", Map.of("lastName", "quiroga"));

        String script = "" +
                "print(name);" +
                "print(age);" +
                "print(date);" +
                "print(map);" +
                "print(index);" +
                "return age * index;";

        Long time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            parameters.put("index", i);
            ExecutionResult result = codeEvaluator.evaluate(script, parameters);
            System.out.println(result.getResult().toString());
        }
        System.out.println("Time:" + (System.currentTimeMillis() - time));
    }

    @Test
    public void testJsonPrint() {
        CodeEvaluator codeEvaluator = Layers.get(CodeEvaluator.class, "js");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name", "javier");
        parameters.put("age", 40);
        parameters.put("date", new Date());
        parameters.put("map", Map.of("lastName", "quiroga"));

        String script = "" +
                "print(name);" +
                "print(age);" +
                "print(date);" +
                "print(map);" +
                "print(index);" +
                "return JSON.stringify(map, null, 2);";

        Long time = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            parameters.put("index", i);
            ExecutionResult result = codeEvaluator.evaluate(script, parameters);
            System.out.println(result.getResult().toString());
        }
        System.out.println("Time:" + (System.currentTimeMillis() - time));
    }

    @Test
    public void testReturnMap() {
        CodeEvaluator codeEvaluator = Layers.get(CodeEvaluator.class, "js");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name", "javier");
        parameters.put("age", 40);
        parameters.put("date", new Date());
        parameters.put("map", Map.of("lastName", "quiroga"));

        String script =
                "print(name);" +
                "print(age);" +
                "print(date);" +
                "print(map);" +
                "return {key:\"value\"};";
        ExecutionResult result = codeEvaluator.evaluate(script, parameters);
        System.out.println(result.getResult().toString());
    }

    @Test
    public void testDeleteElementOfMap() {
        CodeEvaluator codeEvaluator = Layers.get(CodeEvaluator.class, "js");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name", "javier");
        parameters.put("age", 40);
        parameters.put("date", new Date());
        parameters.put("list", List.of(Map.of("middleName", "roman", "lastName", "quiroga"),
                Map.of("middleName", "nicolas", "lastName", "quiroga")));

        String script =
                "print(name);" +
                "print(age);" +
                "print(date);" +
                "print(list);" +
                "var i;\n" +
                "var newList = [];" +
                "for (i = 0; i < list.length; i++) {\n" +
                "  if(list[i].middleName != 'nicolas') {" +
                "     newList = [...newList, list[i]]" +
                "  }\n" +
                "}" +
                "return newList";
        ExecutionResult result = codeEvaluator.evaluate(script, parameters);
        System.out.println(result.getResult().toString());
    }

    @Test
    public void testMapWithList() {
        CodeEvaluator codeEvaluator = Layers.get(CodeEvaluator.class, "js");
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name", "javier");
        parameters.put("age", 40);
        parameters.put("date", new Date());
        Map<String,Object> innerMap = new HashMap<>();
        innerMap.put("innerField1", "innerValue1");
        Map<String,Object> innerMap2 = new HashMap<>();
        innerMap2.put("innerField2", "innerValue2");
        List<Map<String,Object>> listOfMaps = new ArrayList<>();
        listOfMaps.add(innerMap);
        listOfMaps.add(innerMap2);
        List<String> list = new ArrayList<>();
        list.add("item1");
        list.add("item2");
        parameters.put("map", Map.of("lastName", "quiroga", "list", list, "listOfMaps", listOfMaps));

        String script =
                "print(name);" +
                        "print(age);" +
                        "print(date);" +
                        "print(map);" +
                        "print(map.list);" +
                        "map.list = [...map.list, 'item3'];" +
                        "return map;";
        ExecutionResult result = codeEvaluator.evaluate(script, parameters);
        List<Object> resultList = Introspection.resolve(result.getResult(), "list");
        Assert.assertEquals(resultList.size(), 3);
        System.out.println("Result into java context: " + result.getResult().toString());
    }
}

