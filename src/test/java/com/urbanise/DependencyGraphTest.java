package com.urbanise;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DependencyGraphTest {
    @Test
    public void testBuildGraphWithOneNode() {
        //Test graph with one value
        String[] inputList = new String[]{"A"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        Node testNode = dGraph.getNodeMap().get("A");
        assertNotNull(testNode);
        assertEquals("A", testNode.name);
        assertEquals(0, testNode.dependenciesMap.size());

    }

    @Test
    public void testBuildGraphStandard() {
        String[] inputList = new String[]{"A B E", "B C E", "C D E", "D E", "E"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        Node testNode = dGraph.getNodeMap().get("A");
        assertEquals("A", testNode.name);
        assertEquals(2, testNode.dependenciesMap.size());
        assertTrue(testNode.dependenciesMap.containsKey("B"));
        assertTrue(testNode.dependenciesMap.containsKey("E"));
        assertSame(dGraph.getNodeMap().get("B"), testNode.dependenciesMap.get("B"));
        assertSame(dGraph.getNodeMap().get("E"), testNode.dependenciesMap.get("E"));

        testNode = dGraph.getNodeMap().get("C");
        assertEquals("C", testNode.name);
        assertEquals(2, testNode.dependenciesMap.size());
        assertTrue(testNode.dependenciesMap.containsKey("D"));
        assertTrue(testNode.dependenciesMap.containsKey("E"));
        assertSame(dGraph.getNodeMap().get("D"), testNode.dependenciesMap.get("D"));
        assertSame(dGraph.getNodeMap().get("E"), testNode.dependenciesMap.get("E"));

        testNode = dGraph.getNodeMap().get("E");
        assertEquals("E", testNode.name);
        assertEquals(0, testNode.dependenciesMap.size());

    }

    @Test
    public void testBuildGraphWithComplexStructure() {
        //Test complex graph
        String[] inputList = new String[]{"A B C", "B C E", "C G", "D A F", "E F", "F H"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);

        Node testNode = dGraph.getNodeMap().get("A");
        assertEquals("A", testNode.name);
        assertEquals(2, testNode.dependenciesMap.size());
        assertTrue(testNode.dependenciesMap.containsKey("B"));
        assertTrue(testNode.dependenciesMap.containsKey("C"));
        assertSame(dGraph.getNodeMap().get("B"), testNode.dependenciesMap.get("B"));
        assertSame(dGraph.getNodeMap().get("C"), testNode.dependenciesMap.get("C"));

        testNode = dGraph.getNodeMap().get("C");
        assertEquals("C", testNode.name);
        assertEquals(1, testNode.dependenciesMap.size());
        assertTrue(testNode.dependenciesMap.containsKey("G"));
        assertSame(dGraph.getNodeMap().get("G"), testNode.dependenciesMap.get("G"));
        assertEquals(0, dGraph.getNodeMap().get("G").dependenciesMap.size());

        testNode = dGraph.getNodeMap().get("E");
        assertEquals(1, testNode.dependenciesMap.size());
        assertSame(dGraph.getNodeMap().get("H"), testNode.dependenciesMap.get("F").dependenciesMap.get("H"));
    }

    @Test
    public void testGetAllDependenciesByNodeOfGraphWithOneNode() {
        //Test graph with one value
        String[] inputList = new String[]{"A"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        assertEquals("A", dGraph.getAllDependenciesByNode("A"));
    }

    @Test
    public void testGetAllDependenciesOfNodeStandard() {
        String[] inputList = new String[]{"A B E", "B C E", "C D E", "D E", "E"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        assertEquals("A B E C D", dGraph.getAllDependenciesByNode("A"));
        assertEquals("B C E D", dGraph.getAllDependenciesByNode("B"));
        assertEquals("C D E", dGraph.getAllDependenciesByNode("C"));
        assertEquals("D E", dGraph.getAllDependenciesByNode("D"));
        assertEquals("E", dGraph.getAllDependenciesByNode("E"));
    }

    @Test
    public void testGetAllDependenciesByNodeOfComplexGraph() {
        String[] inputList = new String[]{"A B C", "B C E", "C G", "D A F", "E F", "F H"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        assertEquals("A B C E G F H", dGraph.getAllDependenciesByNode("A"));
        assertEquals("B C E G F H", dGraph.getAllDependenciesByNode("B"));
        assertEquals("C G", dGraph.getAllDependenciesByNode("C"));
        assertEquals("D A F B C H E G", dGraph.getAllDependenciesByNode("D"));
        assertEquals("E F H", dGraph.getAllDependenciesByNode("E"));
        assertEquals("F H", dGraph.getAllDependenciesByNode("F"));
    }

    @Test
    public void testGetAllDependenciesByNodeOfCycledGraph() {
        //Test cycled graph
        String[] inputList = new String[]{"A B", "B C", "C A"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        assertEquals("A B C", dGraph.getAllDependenciesByNode("A"));
        assertEquals("B C A", dGraph.getAllDependenciesByNode("B"));
        assertEquals("C A B", dGraph.getAllDependenciesByNode("C"));
    }

    @Test
    public void testGetReversedGraphOfGraphWithOneNode() {
        //Test graph with one value
        String[] inputList = new String[]{"A"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        dGraph = dGraph.getReversedGraph();
        Node testNode = dGraph.getNodeMap().get("A");
        assertNotNull(testNode);
        assertEquals("A", testNode.name);
        assertEquals(0, testNode.dependenciesMap.size());
    }

    @Test
    public void testGetReversedGraphOfComplexGraph() {
        //Test graph
        String[] inputList = new String[]{"A B E", "B C E", "C D E", "D E", "E"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        dGraph = dGraph.getReversedGraph();
        Node testNode = dGraph.getNodeMap().get("A");
        assertEquals("A", testNode.name);
        assertEquals(0, testNode.dependenciesMap.size());

        testNode = dGraph.getNodeMap().get("C");
        assertEquals("C", testNode.name);
        assertEquals(1, testNode.dependenciesMap.size());
        assertTrue(testNode.dependenciesMap.containsKey("B"));
        assertSame(dGraph.getNodeMap().get("B"), testNode.dependenciesMap.get("B"));

        testNode = dGraph.getNodeMap().get("E");
        assertEquals("E", testNode.name);
        assertEquals(4, testNode.dependenciesMap.size());
        assertSame(dGraph.getNodeMap().get("A"), testNode.dependenciesMap.get("A"));
        assertSame(dGraph.getNodeMap().get("B"), testNode.dependenciesMap.get("B"));
        assertSame(dGraph.getNodeMap().get("C"), testNode.dependenciesMap.get("C"));
        assertSame(dGraph.getNodeMap().get("D"), testNode.dependenciesMap.get("D"));
    }

    @Test
    public void testGetReversedGraphOfCycledGraph() {
        //Test cycled graph
        String[] inputList = new String[]{"A B", "B C", "C A"};
        DependencyGraph dGraph = DependencyGraph.buildGraph(inputList);
        dGraph = dGraph.getReversedGraph();
        assertEquals("A C B", dGraph.getAllDependenciesByNode("A"));
        assertEquals("B A C", dGraph.getAllDependenciesByNode("B"));
        assertEquals("C B A", dGraph.getAllDependenciesByNode("C"));
    }
}
