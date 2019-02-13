package environment.supersonic.com.drillmap.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import environment.supersonic.com.drillmap.MainActivity;

public class KruskalAlgorithm {

    public static int adjacency_matrix[][];    //for graph
    public static final int MAX_VALUE = 999;   //for unconnected routes
    public static int number_of_vertices;
    private List<Edge> edges;
    private int visited[];
    private int spanning_tree[][];
    private int road;                           // Kruskal says MST should has n road when we have n+1 nodes


    public KruskalAlgorithm(){
        adjacency_matrix = new int[16][16];
        road = 0;
        edges = new LinkedList<Edge>();

    }


    public void createSpanningTree() {


        number_of_vertices = MainActivity.number;

        visited = new int[number_of_vertices];
        spanning_tree = new int[number_of_vertices][number_of_vertices];

        int i;
        int j;

        for (i = 0; i < number_of_vertices; i++) {
            for (j = 0; j < number_of_vertices; j++) {
                if (adjacency_matrix[i][j] == 0)
                    adjacency_matrix[i][j] = MAX_VALUE;
                //System.out.print(adjacency_matrix[i][j] + " ");
            }
            //System.out.println();
        }


        boolean finished = false;

        for (int source = 0; source < number_of_vertices; source++) {
            for (int destination = 0; destination < number_of_vertices; destination++) {
                if (adjacency_matrix[source][destination] != MAX_VALUE && source != destination) {
                    Edge edge = new Edge();
                    edge.sourcevertex = source;
                    edge.destinationvertex = destination;
                    edge.weight = adjacency_matrix[source][destination];
                    adjacency_matrix[destination][source] = MAX_VALUE;
                    edges.add(edge);
                }


            }
        }

        Collections.sort(edges, new EdgeComparator());
        CheckCycle checkCycle = new CheckCycle();
        for (Edge edge : edges) {
            spanning_tree[edge.sourcevertex][edge.destinationvertex] = edge.weight;
            spanning_tree[edge.destinationvertex][edge.sourcevertex] = edge.weight;
            road++;

            if (checkCycle.checkCycle(spanning_tree, edge.sourcevertex)) {
                spanning_tree[edge.sourcevertex][edge.destinationvertex] = 0;
                spanning_tree[edge.destinationvertex][edge.sourcevertex] = 0;
                road--;
                edge.weight = -1;
                continue;

            } else {
                visited[edge.sourcevertex] = 1;
                visited[edge.destinationvertex] = 1;
            }

            for (i = 0; i < visited.length; i++) {
                if (visited[i] == 0) {
                    finished = false;
                    break;
                } else {
                    if (road == number_of_vertices - 1)
                        finished = true;
                }
            }

            if (finished)
                break;
        }

        System.out.println("The spanning tree is ");
        for (i = 0; i < number_of_vertices; i++)
            System.out.print("\t" + i);
        System.out.println();
        for (int source = 0; source < number_of_vertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 0; destination < number_of_vertices; destination++) {
                System.out.print(spanning_tree[source][destination] + "\t");
            }
            System.out.println();

        }
    }
    //to fill adjacency matrix
    public void setMatrix(int startpoint,int finishpoint,String cost) {
        adjacency_matrix[startpoint][finishpoint]= Integer.parseInt(cost);
        adjacency_matrix[finishpoint][startpoint] = Integer.parseInt(cost);

    }


    class Edge
    {
        int sourcevertex;
        int destinationvertex;
        int weight;
    }

    class EdgeComparator implements Comparator<Edge>
    {
        @Override
        public int compare(Edge edge1,Edge edge2)
        {
            if(edge1.weight < edge2.weight )
                return -1;
            if (edge1.weight > edge2.weight )
                return 1;
            return 0;
        }

    }

}
