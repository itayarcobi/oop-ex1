package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    WGraph_DS gt = new WGraph_DS();

    @BeforeEach
    void setup() {
        for (int i = 0; i < 10; i++) {
            gt.addNode(i);
        }
    }

    @Test
    @DisplayName("Check getNode function-WGraph_DS")
    void getNode() {
        assertNull(gt.getNode(10), "node not exist");
    }

    @Test
    @DisplayName("Check hasEdge function-WGraph_DS")
    void hasEdge() {
        gt.addNode(222);
        assertFalse(gt.hasEdge(222, 222), "same node return false");
    }

    @Test
    @DisplayName("Check getEdge function-WGraph_DS")
    void getEdge() {
        gt.connect(1, 2, 0);
        gt.connect(2, 1, 25);
        assertTrue(gt.getEdge(1, 2) == 25, "must update the weight of the edge");
        assertTrue(gt.getEdge(1, 1) == 0, " 'edge' between node to himself 0");
        assertTrue(gt.getEdge(8, 9) == -1, "edge not exist");
    }

    @Test
    @DisplayName("Check addNode function-WGraph_DS")
    void addNode() {
        gt.addNode(33);
        gt.addNode(33);
        gt.addNode(22);
        gt.addNode(22);
        assertTrue(gt.nodeSize() == 12, "cant add the same node_key to two different nodes");
    }

    @Test
    @DisplayName("Check connect function-WGraph_DS")
    void connect() {
        gt.connect(1, 2, 0);
        gt.connect(2, 1, 1);
        assertTrue(gt.edgeSize() == 1, "if the edge already exist ony update the weight ");
        gt.connect(9, 10, 5);
        assertTrue(gt.edgeSize() == 1, "can not connect- the node not exist ");
        gt.connect(8, 9, -1);
        assertFalse(gt.hasEdge(8, 9), "can not negative edge weight");
    }

    @Test
    @DisplayName("Check getV function-WGraph_DS")
    void getV() {
        for (int i = 0; i < 10; i++) {
            gt.addNode(i);
        }
        assertTrue(gt.nodeSize() == 10, "after add of 10 node getv size should be 10");
    }

    @Test
    @DisplayName("Check GetV(node_id) function-WGraph_DS")
    void testGetV() {
        gt.addNode(1);
        gt.addNode(2);
        gt.addNode(3);
        gt.addNode(4);
        gt.connect(1, 2, 3);
        gt.connect(3, 2, 5);
        gt.connect(3, 4, 7);
        assertTrue(gt.getV(2).size() == 2, "only 2 edges connected to node 2");
    }

    @Test
    @DisplayName("Check removeNode function-WGraph_DS")
    void removeNode() {
        gt.connect(1, 2, 3);
        gt.connect(1, 3, 4);
        gt.connect(1, 8, 9);
        gt.connect(2, 3, 5);
        gt.connect(2, 8, 10);
        gt.removeNode(1);
        assertTrue(gt.nodeSize() == 9, "one node remove");
        assertFalse(gt.edgeSize() == 5);
        assertTrue(gt.getV(2).size() == 2, "remove the node from the neighbors list");
    }

    @Test
    @DisplayName("Check removeEdge function-WGraph_DS")
    void removeEdge() {
        gt.connect(1, 2, 3);
        gt.connect(1, 3, 4);
        gt.connect(1, 8, 9);
        gt.connect(2, 3, 5);
        gt.connect(2, 8, 10);
        gt.removeNode(1);
        gt.removeEdge(2, 3);
        assertTrue(gt.getV(2).size() == 1, "the edges removed");
        gt.removeEdge(1, 2);
        assertTrue(gt.edgeSize() == 1, "this edge not exist");

    }

    @Test
    @DisplayName("Check nodeSize function-WGraph_DS")
    void nodeSize() {
        gt.connect(1, 2, 3);
        gt.connect(1, 3, 4);
        gt.connect(1, 8, 9);
        gt.connect(2, 3, 5);
        gt.connect(2, 8, 10);
        gt.removeNode(1);
        assertTrue(gt.nodeSize() == 9, "the node and his edges  removed");
        gt.addNode(2);
        assertTrue(gt.nodeSize() == 9, "the graph contain node with this key");

    }

    @Test
    @DisplayName("Check edgeSize function-WGraph_DS")
    void edgeSize() {
        gt.connect(1, 2, 3);
        gt.connect(1, 3, 4);
        gt.connect(1, 8, 9);
        gt.connect(2, 3, 5);
        gt.connect(2, 8, 10);
        gt.removeNode(1);
        assertTrue(gt.edgeSize() == 2, "the edges removed");

    }

    @Test
    @DisplayName("Check getMC function-WGraph_DS")
    void getMC() {
        gt.connect(1, 2, 1);
        gt.connect(2, 1, 1);
        assertEquals(gt.getMC(), 11, "add same edge with the same weight not count as MC");
        gt.connect(1, 7, 8);
        gt.connect(1, 3, 4);
        gt.removeEdge(1, 7);
        gt.removeNode(1);
        assertTrue(gt.getMC() == 15);
    }

    @Test
    void testEquals() {
        weighted_graph ge = gt;
        assertEquals(ge, gt, "the graphs not equal");

    }

    @Test
    void runtime() {
        for (int i = 0; i < 1000000; i++) {
            gt.addNode(i);
        }
        for (int j = 0; j < 100000; j++) {
            gt.connect(1, j, j);
            gt.connect(2, j, j);
            gt.connect(3, j, j);
            gt.connect(4, j, j);
            gt.connect(5, j, j);
            gt.connect(6, j, j);
            gt.connect(7, j, j);
            gt.connect(8, j, j);
            gt.connect(9, j, j);
            gt.connect(10, j, j);
        }
    }
}

