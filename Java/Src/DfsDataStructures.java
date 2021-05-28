//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: 11/4/21
// Class:
// Description: data structures for DFS
// Used template provided by Professor Streinu
//-----------------------------------------------------
import java.util.*;
import java.io.*;
public class DfsDataStructures{
    private int nrV;
    private int nrE;

    public int discTime; // This is incremented at each discovery
    public int finTime; //This is incremented at each finishing
    public int time; //This is incremented at each discovery and finishing

    public boolean [] visited;
    public String[] vertexColor;
    public int[] discoveryTime;
    public int[] finishingTime;
    public int[] parent;
    public ArrayList<ArrayList<Integer>> ExtractSCC;

//Local var
    final static boolean DEBUG = false;

//------------------------------------- 
//Utilities
//-------------------------------------
    public static int toMathId(int n){
        return n+1;
    }
    public static void printDebug(String message){
        if(DEBUG){
            System.out.println(message);
        }
    }
    public static void printArrayInt(int [] array){
        for(int i = 0; i<array.length; i++){
            printDebug(""+ toMathId(i)+ ": " + array[i]);
        }
    }
    public static void printArrayBoolean(boolean [] array){
        for(int i = 0; i<array.length; i++){
            printDebug("" + toMathId(i) + ": " + array[i]);
        }
    }
//------------------------------------- 
// Constructors 
//-------------------------------------
    public DfsDataStructures(){
        this.discTime = 0; 
        this.finTime = 0;
        this.time = 0;
        ExtractSCC = new ArrayList<ArrayList<Integer>>();
    }
    public DfsDataStructures(int nrV){
        this.nrV = nrV;
        this.visited = new boolean[nrV];
        this.vertexColor = new String[nrV];
        this.discoveryTime = new int[nrV];
        this.finishingTime = new int[nrV];
        this.parent = new int[nrV];
        this.discTime = 0; 
        this.finTime = 0;
        this.time = 0;
        ExtractSCC = new ArrayList<ArrayList<Integer>>();
    }
//------------------------------------- 
// Setters 
//-------------------------------------
    public void setScc(ArrayList<ArrayList<Integer>> ExtractSCC){
        this.ExtractSCC = ExtractSCC;
    }

//------------------------------------- 
// Getters 
//-------------------------------------
    public boolean [] getVisited(){
        return visited;
    }
    public String [] getVertexColor(){
        return vertexColor;
    }
    public int [] getDiscoveryTime(){
        return discoveryTime;
    }
    public int [] getFinishingTIme(){
        return finishingTime;
    }
    public int [] getParent(){
        return parent;
    }
//------------------------------------- 
//Post DFS information
//-------------------------------------
    public int[] getVertsFinishingOrder(){
        int [] vertsFinOrder = new int [nrV];//compute and return an array of vertex indices in their finishing order
        for(int i = 0; i < nrV; i++){
            for(int j = 0; j < nrV; j++){
                if(finishingTime[j]== i){vertsFinOrder[i]= j;}
            }
        }
        return vertsFinOrder;
    }
    public int [] getVertsInverseFinishingOrder(){
        int [] vertsInvFinOrder = new int [nrV];//compute and return an array of vertex indices in their inverse finishing order
        for(int i = nrV; i>0; i--){
            for(int j = 0; j<nrV; j++){
                if(finishingTime[j]== i){vertsInvFinOrder[nrV - i]= j;}
            }
        }
        return vertsInvFinOrder;
    }

//---------------------------------------------------- 
// Strongly connected components 
//----------------------------------------------------
    public DfsDataStructures extractScc(){
        int[] orderedFinish = getVertsFinishingOrder(); 
        for (int i = 0; i < nrV; i++){ 
            if (parent[i] == -1){ 
                ArrayList<Integer> tempList = new ArrayList<Integer>(); 
                tempList.add(i); 
                int dTime = discoveryTime[i]-1;
                while(dTime + 1 < nrV && parent[orderedFinish[dTime+1]] != -1){
                    dTime += 1;
                    tempList.add(orderedFinish[dTime]); //adds verteces in finishing order
                }
                ExtractSCC.add(tempList); 
            }
        }
        return this;
    }
//-------------------------------------
//Serialization and Print
//-------------------------------------
    public String toStringVisited(){
        String string = "";
        for(int j = 0; j<visited.length; j++){
            string = string + "visited[" + toMathId(j) + "] =" + visited[j];
        }
        return string;
    }
    public String sccToString(){
        String output = "";
        output = output + "{";
        for(int i= 0; i<ExtractSCC.size() ; i++){
            output = output + "{";
            for(int j = 0; j<ExtractSCC.get(i).size();j++){
                output = output + ExtractSCC.get(i).get(j) + ", ";
            }
            output = output + "}, ";
        }
        output = output + "}\n";
        return output.replaceAll(", }", "}");
    }
    public void printSCC(){
        System.out.print(sccToString());
    }
//-------------------------------------
// WriteToFile
//------------------------------------- 
    public void writeToFile(String filename){
    try {
        FileWriter myWriter = new FileWriter(filename); 
        myWriter.write(sccToString());
        myWriter.close();
    } 
    catch (IOException e) { 
        System.out.println("An error occurred."); 
        e.printStackTrace();
    } 
    }
}