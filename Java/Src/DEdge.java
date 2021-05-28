//-----------------------------------------------------
// Author: Olivia Anastassov
// Date: 11/4/21
// Description: Class for: directed edge (for a digraph)
// Used template provided by Professor Streinu
//-----------------------------------------------------
import java.util.*;
public class DEdge{
    private int u;
    private int v;
//------------------------------------- 
// Constructors 
//-------------------------------------
    public DEdge(int u, int v){
        this.u = u; this.v = v;
    }
//------------------------------------- 
// Getters 
//-------------------------------------
    public int getVertex1(){
        return u;
    }
    public int getVertex2(){
        return v;
    }
//------------------------------------- 
// Testers
//-------------------------------------
    public boolean equals(DEdge edge){
        if(this.u == edge.u && this.v == edge.v){
            return true;
        }
        return false;
    }
}