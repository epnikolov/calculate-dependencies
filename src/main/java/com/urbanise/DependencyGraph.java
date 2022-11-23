package com.urbanise;

import java.util.*;

public class DependencyGraph {
    public static final String SEPARATOR = " ";

    private final Map<String, Node> nodeMap;

    public DependencyGraph() {
        nodeMap = new HashMap<>();
    }

    public static DependencyGraph buildGraph(String[] inputList) {
        DependencyGraph dGraph = new DependencyGraph();
        for (String str : inputList) {
            String[] inputDependencyArr = str.split(SEPARATOR);
            if (inputDependencyArr.length < 1) {
                continue;
            }
            Node node = dGraph.createNodeIfNotExisting(inputDependencyArr[0]);
            for (int i = 1; i < inputDependencyArr.length; i++) {
                Node tempNode = dGraph.createNodeIfNotExisting(inputDependencyArr[i]);
                node.addDependency(tempNode);
            }
        }
        return dGraph;
    }

    public DependencyGraph getReversedGraph() {
        DependencyGraph reversedGraph = new DependencyGraph();
        for (Node originalNode : this.nodeMap.values()) {
            Node reversedNode = reversedGraph.createNodeIfNotExisting(originalNode.name);
            for (String dependencyName : originalNode.dependenciesMap.keySet()) {
                Node newDependencyNode = reversedGraph.createNodeIfNotExisting(dependencyName);
                newDependencyNode.addDependency(reversedNode);
            }
        }
        return reversedGraph;
    }

    private Node createNodeIfNotExisting(String name) {
        Node node = nodeMap.get(name);
        if (node == null) {
            node = new Node(name);
            nodeMap.put(name, node);
        }
        return node;
    }

    public String getAllDependenciesByNode(String nodeName) {
        StringBuilder resultSb = new StringBuilder();
        if (!nodeMap.containsKey(nodeName)) {
            return null;
        }
        Node rootNode = nodeMap.get(nodeName);
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(rootNode);
        while (!queue.isEmpty()) {
            Node dependency = queue.poll();
            visited.add(dependency.name);
            resultSb.append(dependency.name).append(SEPARATOR);
            for (Node tempDependency : dependency.dependenciesMap.values()) {
                if (visited.contains(tempDependency.name)) {
                    //Ensures non-infinite loop
                    continue;
                }
                queue.add(tempDependency);
                visited.add(tempDependency.name);
            }
        }
        return resultSb.toString().trim();
    }

    public Map<String, Node> getNodeMap() {
        return nodeMap;
    }

}

class Node {
    final String name;
    final Map<String, Node> dependenciesMap;

    Node(String name) {
        this.name = name;
        this.dependenciesMap = new HashMap<>();
    }

    void addDependency(Node dependency) {
        dependenciesMap.put(dependency.name, dependency);
    }
}
