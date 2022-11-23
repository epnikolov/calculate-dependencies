package com.urbanise;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.out.println("You should supply at least one string argument.");
//            return;
        }

        args = new String[]{"A B", "B C", "C A"};

        DependencyGraph dg = DependencyGraph.buildGraph(args);
        System.out.println("Display all dependencies for each item:");
        for (String input : args) {
            String dependencies = dg.getAllDependenciesByNode(input.split(DependencyGraph.SEPARATOR)[0]);
            System.out.println(dependencies);
        }

        System.out.println();
        System.out.println("Display sets of depending items on each item:");
        dg = dg.getReversedGraph();
        for (String input : args) {
            String dependencies = dg.getAllDependenciesByNode(input.split(DependencyGraph.SEPARATOR)[0]);
            System.out.println(dependencies);
        }
    }
}
