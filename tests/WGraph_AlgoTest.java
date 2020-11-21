package ex1.tests;

import ex1.src.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    WGraph_Algo gat = new WGraph_Algo();
    WGraph_DS g0 = new WGraph_DS();
    @BeforeEach
    void setup(){
        for(int i=0;i<100;i++){
            g0.addNode(i);
        }
        for(int j=0;j<50;j++){
            g0.connect(j,j+1,j);
            g0.connect(j,j+2,j+3);
            g0.connect(j+1,j+10,j+15);
        }
    }

    @Test
    @DisplayName("Check getGraph and Init functions-WGraph_Algo")
    void getGraphAndInit() {
        assertNull(gat.getGraph());
        gat.init(g0);
        assertNotNull(gat.getGraph());
    }

    @Test
    @DisplayName("Check copy function-WGraph_Algo")
    void copy() {
        gat.init(g0);
        weighted_graph gc=gat.copy();
        assertEquals(gc.nodeSize(),100);
        assertEquals(gc.edgeSize(),150);
        assertEquals(gc.getMC(),0,"the mc of the new copied graph should be 0");
    }

    @Test
    @DisplayName("Check isConnected function-WGraph_Algo")
    void isConnected() {
        gat.init(g0);
        assertFalse(gat.isConnected());
        for(int i=0;i<100;i++){
            gat.getGraph().connect(i,i+1,i);
        }
        assertTrue(gat.isConnected());

    }
    @Test
    @DisplayName("Check shortestPathDist function-WGraph_Algo")
    void shortestPathDist() {
        gat.init(g0);
        assertEquals(gat.shortestPathDist(1,1),0,"path 0 from node to himself");
        assertEquals(gat.shortestPathDist(80,100),-1,"this node not exist");
        assertEquals(gat.shortestPathDist(49,98),-1,"this path not exist");
        assertEquals(gat.shortestPathDist(30,40),75,"use the first connect 30-->31 (w=30) use the third connect 31-->40(w=45) ");

    }

    @Test
    @DisplayName("Check shortestPath function-WGraph_Algo")
    void shortestPath() {
        gat.init(g0);
        List<node_info> sp = gat.shortestPath(30,40);
        double[] checkTag = {0.0, 30.0, 75.0};
        int[] checkKey = {30, 31, 40};
        int i = 0;
        for(node_info n: sp) {
            assertEquals(n.getTag(), checkTag[i]);
            assertEquals(n.getKey(), checkKey[i]);
            i++;
        }
    }

    @Test
    @DisplayName("Check save and load functions-WGraph_Algo")
    void saveAndload() {
        weighted_graph_algorithms ag0 = new WGraph_Algo();
        ag0.init(g0);
        String str = "g0-saveloadTest.obj";
        ag0.save(str);
        weighted_graph g1 =new  WGraph_DS();
        for(int i=0;i<100;i++){
            g1.addNode(i);
        }
        for(int j=0;j<50;j++){
            g1.connect(j,j+1,j);
            g1.connect(j,j+2,j+3);
            g1.connect(j+1,j+10,j+15);
        }

        ag0.load(str);
        assertEquals(g0,g1);
        g0.removeNode(0);
        assertNotEquals(g0,g1);

    }


}