/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astarsearchalgo;

import java.util.PriorityQueue;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;

public class AstarSearchAlgo{


        //h scores is the stright-line distance from the current city to Bucharest
        public static void main(String[] args){

                //initialize the graph base on the Romania map
                Node n1 = new Node("Bobbia", 10.5);
                Node n2 = new Node("Piacenza", 10);
                Node n3 = new Node("Carpi", 8);
                Node n4 = new Node("Terme", 7);
                Node n5 = new Node("Emilia", 6);
                Node n6 = new Node("Imola", 5);
                Node n7 = new Node("Faenza", 4);
                Node n8 = new Node("Cesena", 4.5);
                Node n9 = new Node("Forli", 2);
                Node n10 = new Node("Ferrara", 5);
                Node n11 = new Node("Rimini", 0.5);
                Node n12 = new Node("Ravenna", 0);
 
                // inisialisasi Edge based on the distance cost between nodes
        
                // Bobbia
                n1.adjacencies = new Edge[] {
                    new Edge(n2, 5), new Edge(n4, 3), new Edge(n8, 15)
                };

                // Piacenza
                n2.adjacencies = new Edge[] {
                    new Edge(n3, 3), new Edge(n4, 3)
                };

                // Carpi
                n3.adjacencies = new Edge[] {
                    new Edge(n10, 8), new Edge(n5, 2)
                };

                // Terme
                n4.adjacencies = new Edge[] {
                    new Edge(n5, 2), new Edge(n7, 3)
                };

                // Emilia
                n5.adjacencies = new Edge[] {
                    new Edge(n6, 2)
                };

                // Imola
                n6.adjacencies = new Edge[] {
                    new Edge(n7, 1), new Edge(n9, 3)
                };

                // Faenza
                n7.adjacencies = new Edge[] {
                    new Edge(n9, 2), new Edge(n8, 6)
                };

                // Cesena
                n8.adjacencies = new Edge[] {
                    new Edge(n11, 5)
                };

                // Forli
                n9.adjacencies = new Edge[] {
                    new Edge(n12, 3), new Edge(n8, 2)
                };

                // Ferrara
                n10.adjacencies = new Edge[] {
                    new Edge(n6, 3), new Edge(n12, 6)
                };

                // Rimini
                n11.adjacencies = new Edge[] {
                    new Edge(n12, 1)
                };
                
                // 

                AstarSearch(n1,n12);

                List<Node> path = printPath(n12);

                        System.out.println("Path: " + path);


        }

        public static List<Node> printPath(Node target){
                List<Node> path = new ArrayList<Node>();
        
        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }

        Collections.reverse(path);

        return path;
        }

        public static void AstarSearch(Node source, Node goal){

                Set<Node> explored = new HashSet<Node>();

                PriorityQueue<Node> queue = new PriorityQueue<Node>(20, 
                        new Comparator<Node>(){
                                 //override compare method
                 public int compare(Node i, Node j){
                    if(i.f_scores > j.f_scores){
                        return 1;
                    }

                    else if (i.f_scores < j.f_scores){
                        return -1;
                    }

                    else{
                        return 0;
                    }
                 }

                        }
                        );

                //cost from start
                source.g_scores = 0;

                queue.add(source);

                boolean found = false;

                while((!queue.isEmpty())&&(!found)){

                        //the node in having the lowest f_score value
                        Node current = queue.poll();

                        explored.add(current);

                        //goal found
                        if(current.value.equals(goal.value)){
                                found = true;
                        }

                        //check every child of current node
                        for(Edge e : current.adjacencies){
                                Node child = e.target;
                                double cost = e.cost;
                                double temp_g_scores = current.g_scores + cost;
                                double temp_f_scores = temp_g_scores + child.h_scores;


                                /*if child node has been evaluated and 
                                the newer f_score is higher, skip*/
                                
                                if((explored.contains(child)) && 
                                        (temp_f_scores >= child.f_scores)){
                                        continue;
                                }

                                /*else if child node is not in queue or 
                                newer f_score is lower*/
                                
                                else if((!queue.contains(child)) || 
                                        (temp_f_scores < child.f_scores)){

                                        child.parent = current;
                                        child.g_scores = temp_g_scores;
                                        child.f_scores = temp_f_scores;

                                        if(queue.contains(child)){
                                                queue.remove(child);
                                        }

                                        queue.add(child);

                                }

                        }

                }

        }
        
}

class Node{

        public final String value;
        public double g_scores;
        public final double h_scores;
        public double f_scores = 0;
        public Edge[] adjacencies;
        public Node parent;

        public Node(String val, double hVal){
                value = val;
                h_scores = hVal;
        }

        public String toString(){
                return value;
        }

}

class Edge{
        public final double cost;
        public final Node target;

        public Edge(Node targetNode, double costVal){
                target = targetNode;
                cost = costVal;
        }
}
