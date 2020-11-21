Definition:
ex1 is the second project of oop course .
The project is written in java.
The project implements an unintentional graph with Weighted edges  using node_info,weighted_graph and weighted_graph_algorithms interfaces.
using hashmaps as the main Data Structure -Can save any object (Node in this project) in the form of an object and a key
and also its access to organs in o(1) for example in get the key.
The project allows the user to create an weighted unintentional graph, nodes and edges that will be in the graph.
The user can run several algorithms as copy ,check if Connected and the shortest path from two nodes on the built graph and also save and load the graphs.

Terminology:
Node - a single Vertex in the graph
Edge - line, which connects two nodes in the same graph.
Path - a sequence of nodes and edges connecting a src node with a dest node in the same graph.
ShortestPathDist- Include any weight of edges from src to dest, and choose the lowest one.
Node_key- key (id) associated with this node (each node_data should have a unique key).
Node_tag-Temporal data (sum of dist form src) used in SortestPathDist algorithm.
Node_info-remark ("visited"/"not-visited") associated with this node.
getv-pointer (shallow copy) for a Collection representing specific nodes in the graph.
MC-Mode Count - for testing changes in the graph( Any change in the inner state of the graph should cause an increment in the ModeCount).

Some info:
The 3 interfaces Are implemented in 2 classes:WGraph_DS, WGraph_Algo.
The first interface node_info implement as a private class in WGraph_DS class.
(All functions are more detailed and explained in the code itself-Details of each step)


Praivate class Node_INFO:
getKey(): 
Return the key (id) associated with this node
getInfo():
Return the remark ("visited"/"not-visited") associated with this node.
setInfo(String s):
Change the Temporal data ("visited"/"not-visited").
getTag():	
Temporal data (sum of dist form src) used in SortestPathDist algorithm.
setTag(double t):
Change the Temporal data (sum of dist form src) used in SortestPathDist algorithm.

public class WGraph_DS:
 getNode(int key):
 return the node_data by the node_id of the specific graph.
hasEdge(int node1, int node2):
 return true iff there is an edge between node1 and node2
 getEdge(int node1, int node2):
retuen the edge weight  value between node1 and node2
addNode(int key):
add new node to the graph with this key.
connect(int node1, int node2, double w):
connect this to nodes in the graph.
 getV():
return collection of all the nodes in the graph.
getV(int node_id):
retuen collection of all node_id neighbors.
 removeNode(int key):
remove node key from the graph and all the edges belongs to him.
removeEdge(int node1, int node2):
 Delete the edge from the graph.
nodeSize():
 return the number of vertices (nodes) in the graph.
 edgeSize():
return the number of edges in the graph.
getMC():
return the Mode Count - for testing changes in the graph.

The WGraph_Algo class implement The interface weighted_graph_algorithms :
init(weighted_graph g):
nit the graph on which this set of algorithms operates on.
 getGraph():
Return the underlying graph of which this class works.
 copy():
Compute a deep copy of this weighted graph.
 isConnected():
Returns true iff there is a valid path from EVREY node to each
shortestPathDist(int src, int dest):
 returns the length of the shortest path between src to dest
shortestPath(int src, int dest):
returns the the shortest path between src to dest - as an ordered List of nodes- usuing linkedlist
save(String file):
Saves this weighted (undirected) graph to the given file name.
 load(String file):
 This method load a graph to this graph algorithm.

