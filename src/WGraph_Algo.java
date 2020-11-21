package ex1.src;

import java.io.*;

import java.util.*;

public class WGraph_Algo implements weighted_graph_algorithms {
    private weighted_graph g0;
   // private node_info n;
    /**
     * Init the graph on which this set of algorithms operates on.
     * @param g
     */
    @Override
    public void init(weighted_graph g) {
        this.g0=g;
    }
    /**
     * Return the underlying graph of which this class works.
     * @return
     */
    @Override
    public weighted_graph getGraph() {
        return this.g0;
    }
    /**
     * Compute a deep copy of this weighted graph.
     * create a new graph
     * Take all the nodes from the old graph and put them to an array of nodes
     * Then copy all the nodes from the array to the new graph
     * Then copy to each node his :key, info and tag
     * Then I got a new graph with all the nodes and values of the old graph.
     * Create another array with the new graph nodes
     * Run on the neighbors of each node in the original graph with iterator- By the nodes in the new array(order)
     * and connected in the new graph between all the nodes needed
     * @return copy graph
     */
    @Override
    public weighted_graph copy() {
        if(g0==null)return null;
        if(g0.nodeSize()==0){
            WGraph_DS eg0 = new WGraph_DS();
            return eg0;
        }
        WGraph_DS cg0 = new WGraph_DS();
        node_info []arr= g0.getV().toArray(new node_info[0]);
        for(int i=0;i<arr.length;i++) {
            cg0.addNode(arr[i].getKey());
            cg0.getNode(arr[i].getKey()).setInfo(arr[i].getInfo());
            cg0.getNode(arr[i].getKey()).setTag(arr[i].getTag());
        }
        for(int i=0;i<arr.length;i++) {
            Iterator<node_info> itr = g0.getV(arr[i].getKey()).iterator();
            while (itr.hasNext()) {
                node_info n = itr.next();
                cg0.connect(arr[i].getKey(), n.getKey(), g0.getEdge(arr[i].getKey(), (n.getKey())));
            }
        }
        cg0.setMC(0);
        return cg0;
    }
    /**
     * Returns true if  there is a valid path from every node to each
     * using the color white and black (info-string) to check if visited
     * make all the nodes in the graph white (not visited)- using iterator
     * create a LinkedList to implement the queue
     * after set all the nodes info - set the (last) node to black
     * add the node n to the queue
     * As long as the queue not empty -run on node n Neighbors
     * As long as node n have Neighbors - if Neighbor's info is white(not visited)-mark black and add him to the queue.
     * after we add and visited all n node Neighbors - remove n from the queue and count
     * As long as queue is not empty that mean we have another node to check
     * we define the first node in the queue to be the next to check (n)
     * after we done check node we count (count= all nodes that checked)
     * if count = all the nodes in the graph the graph is Connected
     * (We will move from one node to all his neighbors from all his neighbors to all their neighbors and so on and after counting we will know if the graph is Connected)
     * @return boolean
     */
    @Override
    public boolean isConnected() {
        if(g0.nodeSize()==0 ||g0.nodeSize()==1) return true;
        Iterator<node_info> itrc=g0.getV().iterator();
        node_info n= itrc.next();
        while(itrc.hasNext()) {
            n.setInfo("white");
            n=itrc.next();
        }
        LinkedList<node_info> q=new LinkedList<node_info>();
        n.setInfo("black");
        q.add(n);
        int count=0;
        while(!q.isEmpty()) {
            Iterator<node_info> itrn=g0.getV(n.getKey()).iterator();
            while(itrn.hasNext()) {
                node_info n2=itrn.next();
                if(n2.getInfo().equalsIgnoreCase("white")){
                    n2.setInfo("black");
                    q.addLast(n2);
                }
            }
            q.removeFirst();
            count++;
            if(q.size()!=0) {
                n=g0.getNode(q.peekFirst().getKey());
            }
        }
        if(g0.nodeSize()==count)
            return true ;
        else {
            return false;
        }
    }
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * using priority queue to implement Dijkstra's Algorithm
     * insert the node to array and set their tag to -1 (not yet visited)
     * change src tag to 0 and add him to the priority queue
     * while the queue is not empty go and check the neighbors of the first node in the queue (lowest tag) and remove hin
     * if we not visited him (tag=-1):add him to the graph with the tag that was temp(the first node queue) and the weight edge between them
     * if we visited him:update his tag ony if his current tag bigger then the update one
     * (the update tag is:the temp tag (the first node queue that remove) and the weight edge between them
     * The advantage of the priority queue: I set a comparator where I specify that in each addition the queue
     * will be sorted only by the lowest tag and not by the order of income
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if(g0.getNode(src)==null || g0.getNode(dest)==null)return -1;
        if(g0.nodeSize()==0) return -1;
        if(src==dest)return 0;
        PriorityQueue<node_info> q=new PriorityQueue<node_info>(new node_infoComparator());
        node_info[] arr =g0.getV().toArray(new node_info[0]);
        for(int i=0; i< arr.length;i++){
           arr[i].setTag(-1);
        }
        g0.getNode(src).setTag(0);
        q.add(g0.getNode(src));
        while (!q.isEmpty()) {
            node_info temp=q.poll();
            Iterator<node_info> itrn = g0.getV(temp.getKey()).iterator();
            while (itrn.hasNext()) {
                node_info ng = itrn.next();
                if (ng.getTag()==-1){
                    ng.setTag(temp.getTag()+g0.getEdge(temp.getKey(),ng.getKey()));
                    q.add(ng);
                }
                else if(ng.getTag()>temp.getTag()+g0.getEdge(temp.getKey(),ng.getKey())){
                    ng.setTag(temp.getTag()+g0.getEdge(temp.getKey(),ng.getKey()));
                    q.add(ng);
                }
        }
        }

        return g0.getNode(dest).getTag();
    }
    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     * using shortestpathdist function
     * create a list and add node dest to the end
     * now run all the first nodes in the list neighbors
     * if the node tag=the neighbor tag + the edge between them add the neighbor to the list (and now check his neighbors)
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(src==dest) {
            LinkedList<node_info> l0 = new LinkedList<node_info>();
            l0.add(g0.getNode(src));
            return l0;
        }
        shortestPathDist(src, dest);
        LinkedList<node_info> l1 = new LinkedList<node_info>();
        node_info mn = g0.getNode(dest);
        l1.addLast(mn);
        while(mn.getTag()!=0){
        Iterator<node_info> itrr=g0.getV(mn.getKey()).iterator();
        while (itrr.hasNext()) {
            node_info nr = itrr.next();
            if(g0.getNode(mn.getKey()).getTag()==g0.getNode(nr.getKey()).getTag()+g0.getEdge(nr.getKey(), mn.getKey())) {
                mn = nr;
                l1.addFirst(mn);
                break;
            }
        }

        }
    return l1;
    }
    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     * using Serializable interface
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try
        {
            //Saving of object in a file
            FileOutputStream filegra = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(filegra);

            // Method for serialization of object
            out.writeObject(this.g0);

            out.close();
            filegra.close();
        }
        catch(IOException ex)
        {
            return false;
        }
        return  true;
    }


    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     * using Serializable interface
     * @param file - file name
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try
        {
            // Reading the object from a file
            FileInputStream filegr = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(filegr);

            // Method for deserialization of object
            WGraph_DS g1 = (WGraph_DS)in.readObject();

            in.close();
            filegr.close();
            this.g0=g1;

        }

        catch(IOException ex)
        {
            return false;
        }

        catch(ClassNotFoundException ex)
        {
            return false;
        }
        return true;

    }
     static class node_infoComparator implements Comparator<node_info> {
        @Override
        public int compare(node_info o1, node_info o2) {
            if(o1.getTag()>o2.getTag())
                return 1;
            else if (o1.getTag()<o2.getTag())
                return -1;
            return 0;

        }
    }


    }



