//-----------------------------------------------------
// Author: Olivia Anastassov     
// Date:   11/4/21     
// Description: directed DGraph represented with adjacency lists
//              templates for dfs, strong components etc.
// Used template provided by Professor Streinu
//-----------------------------------------------------
import java.util.*;
public class DGraphAdj{
    private ArrayList<Integer>[] adjLists;

//local var - used to help with debugging
    final static boolean DEBUG = false;

//utilities
    public static int toMathId(int n){
        return n+ 1;
    }
    public static void printDebug(String message){
        if (DEBUG){
            System.out.println(message);
        }
    }
    public static void printArrayInt(int [] array){
        for (int i = 0; i< array.length; i++){
            printDebug("" + toMathId(i)+ ": "+ array[i]);
        }
    }

//------------------------------------- 
// Constructors 
//-------------------------------------
    public DGraphAdj(int nrVertices){
        adjLists = new ArrayList[nrVertices];//throws a warning 
        for(int i = 0; i<nrVertices; i++){
            adjLists[i] = new ArrayList<Integer>();
        }
    }
    public DGraphAdj(ArrayList<Integer>[] adjLists){
        this.adjLists = adjLists;
    }

//------------------------------------- 
// Getters
//-------------------------------------
    public ArrayList<Integer>[] getAdjLists(){
        return adjLists;
    }
    public ArrayList<Integer> getAdjLists(int i){
        return adjLists[i];
    }
    public int getNrVertices(){
        return adjLists.length;
    }
    public int getNrNeighbors(int i){
        return adjLists[i].size();
    }

//------------------------------------- 
// Setters
//-------------------------------------
    public void setAdjLists(ArrayList<Integer>[] adjLists){
        this.adjLists = adjLists;
    }

//------------------------------------- 
// Serialize and print
//-------------------------------------
    public String toString(){
        int nrVertices = this.getNrVertices();
        String graphString = "";

        for(int v = 0; v<nrVertices; v++){
            graphString += toMathId(v) + ": ";
            ArrayList<Integer> adjList = getAdjLists(v);
            for(int i = 0; i < adjList.size(); i++){
                int neighbr = adjList.get(i);
                graphString += "" + toMathId(neighbr) + " ";
            }
            if (v < nrVertices - 1){
                graphString += "\n";
            }
        }
        return graphString;
    }
    public void printDGraph(){
        System.out.println(this.toString());
    }
    //debugging helper
    private void printVisited(boolean[] visited){
        printDebug("\nvisited:");
        for(int j = 0; j<visited.length; j++){
            printDebug(toMathId(j)+ ": " + visited[j]);
        }
        printDebug("");
    }

//------------------------------------- 
// Modifiers
//-------------------------------------
    public void addEdge(int u, int v){
        if (u != v){
            adjLists[u].add(v);
        }
    }

//------------------------------------- 
// Converters
//-------------------------------------
    public DGraphEdges toDGraphEdges(){
        ArrayList<DEdge> dEdges = new ArrayList<DEdge>();
        for(int i = 0; i<this.getNrVertices(); i++){
            for(int r = 0; r<adjLists[i].size(); r++){
                dEdges.add(new DEdge(i,adjLists[i].get(r)));
            }
        }
        DGraphEdges toDGraph = new DGraphEdges(this.getNrVertices(),dEdges);
        return toDGraph;
    }

//------------------------------------- 
// Operations
//-------------------------------------
    public DGraphAdj reverseDGraph(){
        DGraphAdj revDGraph = new DGraphAdj(this.getNrVertices());
        for(int i = 0; i<this.getNrVertices(); i++){
            for(int r = 0; r<adjLists[i].size(); r++){
                revDGraph.addEdge(adjLists[i].get(r),i);
            }
        }
        return revDGraph;
    }

//helper functions
    public int[] makeStdVertexOrdering(){
        // an array of vertices in increasing order of their ids
        // 0, 1, 2, ....
        int [] stdVertexOrdering = new int [this.getNrVertices()];
        for(int i = 0; i<this.getNrVertices(); i++){
            stdVertexOrdering[i] = i;
        }
        return stdVertexOrdering;
    }

//----------------------------------------------------
// DFS with discovery and finishing times
// The "full" DFS
//  - using lots of additional data structures: colors, discovery, finishing etc.
//  - returns information in the additional data structures
//  - all functions below are extensions of the "basic" DFS above
//    enhanced with the collection of the additional data
//-------------------------------------
//single vertex
    public DfsDataStructures dfsDF(int v){
        DfsDataStructures dfsData = new DfsDataStructures(this.getNrVertices()); 
        return dfsData;
    }
    //all vertices, standard core
    public DfsDataStructures dfsRecDF(int v, DfsDataStructures dfsData){
        printDebug("PRE: Visit v=" + v);
        dfsData.time = dfsData.time+1;
        dfsData.discTime = dfsData.discTime+1;
        dfsData.discoveryTime[v] = dfsData.discTime;
        dfsData.vertexColor[v] = "gray";
        for(int i = 0; i< adjLists[v].size(); i++){
            if(dfsData.vertexColor[adjLists[v].get(i)] == "white"){
                dfsData.parent[adjLists[v].get(i)] = v;
                dfsData = dfsRecDF(adjLists[v].get(i),dfsData);
            }
        }
        dfsData.vertexColor[v] = "black";
        dfsData.time = dfsData.time+1;
        dfsData.finTime = dfsData.finTime+1;
        dfsData.finishingTime[v] = dfsData.finTime;
        printDebug("POST: Done v=" + v);
        return dfsData;
    }
    // DFS with given order for choosing new unvisited vertex
    public DfsDataStructures dfsDFV(int [] orderedVerts){
        DfsDataStructures dfsData = new DfsDataStructures(this.getNrVertices()); 
        for(int i = 0; i<orderedVerts.length; i++){
            dfsData.vertexColor[i] = "white";
            dfsData.parent[i] = -1;
        }
        for(int i = 0; i<orderedVerts.length; i++){
            if (dfsData.vertexColor[orderedVerts[i]] == "white"){
                dfsData = dfsRecDF(orderedVerts[i], dfsData);
            }
        }
        return dfsData;
    }
//---------------------------------------------------- 
// Strongly connected components 
//----------------------------------------------------
    public DfsDataStructures strongComponents(){
        DfsDataStructures dfsData = dfsDFV(makeStdVertexOrdering());
        DGraphAdj DGraphAdjRev = this.reverseDGraph();
        DfsDataStructures dfsDataRev = DGraphAdjRev.dfsDFV(dfsData.getVertsInverseFinishingOrder());
        return dfsDataRev;
    }

}