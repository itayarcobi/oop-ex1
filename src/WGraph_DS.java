package ex1.src;


import java.util.*;


public class WGraph_DS implements weighted_graph,java.io.Serializable {
    private HashMap<Integer, node_info> gra;
    private HashMap<Integer, HashMap<Integer, Double>> edg;
    private HashMap<Integer, HashMap<Integer, node_info>> nie;
    private int countedg;
    private int countmc;

    public WGraph_DS() {
        gra = new HashMap<Integer, node_info>();
        edg = new HashMap<Integer, HashMap<Integer, Double>>();
        nie = new HashMap<Integer, HashMap<Integer, node_info>>();
        countmc=0;
        countedg=0;
    }
    /**
     * return the node_data by the node_id,
     * using get function form hashmap
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_info getNode(int key) {
        return gra.get(key);
    }
    /**
     * return true iff there is an edge between node1 and node2
     * check if the nodes exist and if node2 in node1 neighbors list (each other neighbors list)
     * using nie hashmap -integer-nodes form the graph and hashmap of all his  neighbors.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if (!(gra.containsKey(node1) && gra.containsKey(node2))) return false;
        if (node1 == node2) return false;
        if (countedg == 0) return false;

        if (nie.get(node1).containsKey(node2))
            return true;
        return false;
    }
    /**
     * return the weight if the edge (node1, node1).
     * In case there is no such edge - should return -1
     * if node1=node2 return 0
     * using nie hashmap -integer-nodes form the graph and hashmap of one neighbor and the value of the edge between them.
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public double getEdge(int node1, int node2) {
        if (node1 == node2) return 0;
        if (hasEdge(node1, node2))
            return edg.get(node1).get(node2);
        return -1;
    }
    /**
     * add a new node to the graph with the given key.
     * create a new node in the graph and create his neighbors and edges hashmaps
     * Note2: if there is already a node with such a key -> no action should be performed.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (!gra.containsKey(key)) {
            gra.put(key, new Node_INFO(key));
            HashMap<Integer, Double> edg1 = new HashMap<Integer, Double>();
            edg.put(key, edg1);
            HashMap<Integer, node_info> nie1 = new HashMap<Integer, node_info>();
            nie.put(key, nie1);
            countmc = countmc + 1;
        }
    }
    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * add each other to their neighbors hashmap
     * add each other to their edge hashmap between them
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (gra.containsKey(node1) && gra.containsKey(node2) && w >= 0 && node1 != node2) {
            if (!hasEdge(node1, node2)) {
                nie.get(node1).put(node2, gra.get(node2));
                nie.get(node2).put(node1, gra.get(node1));
                edg.get(node1).put(node2, w);
                edg.get(node2).put(node1, w);
                countedg = countedg + 1;
                countmc = countmc + 1;

            } else if (getEdge(node1, node2) != w) {
                edg.get(node1).put(node2, w);
                edg.get(node2).put(node1, w);
                countmc = countmc + 1;
            }
        }
    }
    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * using the graph hashmap function
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return gra.values();
    }
    /**
     * This method returns a Collection containing all the
     * nodes connected to node_id
     * using the node_id neighbors hashmap function
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        return nie.get(node_id).values();
    }
    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * remove node key from all his neighbors and edges hashmaps
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_info removeNode(int key) {
        if (getNode(key) != null) {
            Iterator<node_info> itr = nie.get(key).values().iterator();
            while (itr.hasNext()) {
                node_info n = itr.next();
                edg.get(n.getKey()).remove(key);
                nie.get(n.getKey()).remove(key);
                countedg = countedg - 1;
            }
            nie.remove(key);
            edg.remove(key);
            countmc = countmc + 1;
            return gra.remove(key);
        }
        return null;
    }
    /**
     * Delete the edge from the graph,
     * remove each other from theirs neighbors and edges hashmaps
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        if (hasEdge(node1, node2)) {
            edg.get(node1).remove(node2);
            edg.get(node2).remove(node1);
            nie.get(node1).remove(node2);
            nie.get(node2).remove(node1);
            countedg = countedg - 1;
            countmc = countmc + 1;
        }

    }
    /** return the number of vertices (nodes) in the graph.
     * using graph hashmap function
     * @return
     */
    @Override
    public int nodeSize() {
        return gra.size();
    }
    /**
     * return the number of edges (undirectional graph).
     * counter to all the edges changes in the graph
     * @return
     */
    @Override
    public int edgeSize() {
        return countedg;
    }
    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @return
     */
    @Override
    public int getMC() {
        return countmc;
    }
    /**
     * set the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     * @param mc
     */
    public void setMC(int mc) {
        this.countmc = mc;
    }
    //using generate option:

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WGraph_DS wGraph_ds = (WGraph_DS) o;

        if (countedg != wGraph_ds.countedg) return false;
        if (countmc != wGraph_ds.countmc) return false;
        if (gra != null ? !gra.equals(wGraph_ds.gra) : wGraph_ds.gra != null) return false;
        if (edg != null ? !edg.equals(wGraph_ds.edg) : wGraph_ds.edg != null) return false;
        return nie != null ? nie.equals(wGraph_ds.nie) : wGraph_ds.nie == null;
    }

    @Override
    public int hashCode() {
        int result = gra != null ? gra.hashCode() : 0;
        result = 31 * result + (edg != null ? edg.hashCode() : 0);
        result = 31 * result + (nie != null ? nie.hashCode() : 0);
        result = 31 * result + countedg;
        result = 31 * result + countmc;
        return result;
    }
     //using my own equals
    /*
            @Override
            public boolean equals(Object obj) {
                if(this ==obj)return true;
                if(obj==null || getClass() != obj.getClass()) return false;
                WGraph_DS g1 = (WGraph_DS) obj;
                node_info[] arr = g1.getV().toArray(new node_info[0]);
                for (int i = 0; i < arr.length; i++) {
                    if(!gra.containsKey(arr[i].getKey())) {
                        return false;

                    }
                    Iterator<node_info> itrn=g1.getV(arr[i].getKey()).iterator();
                    while (itrn.hasNext()){
                        node_info ni = itrn.next();
                        if(!nie.get(arr[i].getKey()).containsKey(ni.getKey()))return false;
                        if(getEdge(arr[i].getKey(),ni.getKey())!=edg.get(arr[i].getKey()).get(ni.getKey()))return false;
                        if(countedg!=g1.edgeSize()||countmc!=g1.getMC())return false;
                    }
                }
                return true;
            }
*/

    private class Node_INFO implements node_info,java.io.Serializable{
        private int key;
        private String info;
        private double tag;

        public Node_INFO(int key) {
            this.key = key;
            this.info = "";
            this.tag = -1;
        }
        /**
         * Return the key (id) associated with this node.
         * Note: each node_data should have a unique key.
         * @return
         */
        @Override
        public int getKey() {
            return this.key;
        }
        /**
         * return the remark (meta data) associated with this node.
         * @return
         */
        @Override
        public String getInfo() {
            return this.info;
        }
        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s
         */
        @Override
        public void setInfo(String s) {
            this.info = s;
        }
        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return
         */
        @Override
        public double getTag() {
            return this.tag;
        }
        /**
         * Allow setting the "tag" value for temporal marking an node - common
         * practice for marking by algorithms.
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            this.tag = t;
        }
        /*
        //using my own equals
        @Override
        public boolean equals(Object obj) {
            if(this ==obj)return true;
            if(obj==null || getClass() != obj.getClass()) return false;
            Node_INFO n1 = (Node_INFO) obj;
            if(n1.getKey()!=this.key)return false;
          //  if(n1.getInfo()!=this.info)return false;
         //   if(n1.getTag()!=this.getTag())return false;
            return true;
        }
*/
    //using generate option:
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node_INFO node_info = (Node_INFO) o;

            if (key != node_info.key) return false;
            if (Double.compare(node_info.tag, tag) != 0) return false;
            return info != null ? info.equals(node_info.info) : node_info.info == null;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = key;
            result = 31 * result + (info != null ? info.hashCode() : 0);
            temp = Double.doubleToLongBits(tag);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }


    }


}
