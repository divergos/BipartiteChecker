/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/*
 *
 * @author Dimitris
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BipartiteChecker {

    public static void main(String args[]) throws FileNotFoundException, IOException {
        String file = "example.txt";
        if (args[0].equals("-b")) {
            file = args[1];
        } else {
            file = args[0];
        }

        int max = 0;
        ArrayList<Integer> intList = new ArrayList<Integer>();//lista gia na perasoume oles tis times tou arxeiou wste na mporesoume epeita na tis eksetasoume ws akmes

        max = create_array_vertex(file, max, intList);
        max++;//sunolikos arithmos komvwn
        boolean[][] adjacencylist = new boolean[max][max];
        int size = intList.size();
        create_adjacency_List(adjacencylist, size, intList);
        int vertex = 0;//arxikos komvos o komvos "0"
        String[] color = new String[max];//pinakas gia ton xrwmatismo ton komvon me megethos enan arithmo pou antistoixei ston megalutero komvo
        int metritis = 1;//metraei ton arithmo ton komvon pou xrwmatizoume kai mas voithaei na epistefoume ston proigoumeno geitona-komvo 
        for (int i = 0; i < max; i++) {  //arxikopioume oles tis theseis tou pinaka me tn timi "white"
            color[i] = "white";
        }
        color[0] = "red";//o prwtos komvos("0") pernei tn timi red
        int sum_akmon = (size) / 2;//sunolikos arithmos akmwn
        if (args[0] == "-b") {
            String[] col = BFS(adjacencylist, vertex, color, sum_akmon);
            print_sets(col);
        } else {
            int[] visited = new int[max];
            visited[0] = 0;//started visited vertex "0"
            String[] col2 = DFS(adjacencylist, vertex, color, metritis, visited, sum_akmon);
            print_sets(col2);
        }

    }

    public static String[] DFS(boolean[][] adjacencyList, int vertex, String[] color, int metritis, int[] visited, int sum_akmon) {

        outerloop:

        if (sum_akmon != 0) {

            for (int j = 0; j < color.length; j++) {

                if (vertex != j) {
                    if (adjacencyList[vertex][j] == true) {

                        --sum_akmon;

                        if ((color[vertex] != color[j])) {
                            adjacencyList[vertex][j] = false;
                            adjacencyList[j][vertex] = false;
                            if (color[j] == "white") {
                                if (color[vertex] == "red") {
                                    color[j] = "blue";

                                } else {
                                    color[j] = "red";
                                }
                                if (metritis < color.length) {
                                    visited[metritis] = j;

                                    metritis++;

                                }

                                vertex = j;

                                return DFS(adjacencyList, vertex, color, metritis, visited, sum_akmon);
                            }
                        }

                        if (color[vertex] == color[j]) {

                            color[0] = "green";
                            break outerloop;

                        }

                    }
                    if ((adjacencyList[vertex][color.length - 1] == false) & (j == (color.length - 1))) {
                        if (metritis < color.length) {
                            vertex = visited[metritis - 1];
                            metritis--;

                            return DFS(adjacencyList, vertex, color, metritis, visited, sum_akmon);
                        }
                    }

                }
            }

        }

        return color;
    }

    public static String[] BFS(boolean[][] adjacencyList, int vertex, String[] color, int sum_akmon) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(0);
        outerloop:

        while (queue.size() != 0) {
            vertex = queue.poll();

            for (int j = 0; j < color.length; j++) {
                if (vertex != j) {
                    if (adjacencyList[vertex][j] == true) {
                        queue.add(j);
                        if ((color[vertex] != color[j])) {
                            adjacencyList[vertex][j] = false;
                            adjacencyList[j][vertex] = false;
                            if (color[j] == "white") {
                                if (color[vertex] == "red") {
                                    color[j] = "blue";

                                } else {
                                    color[j] = "red";
                                }

                            }
                        }

                        if (color[vertex] == color[j]) {

                            color[0] = "green";
                            break outerloop;

                        }

                    }

                }
            }

        }

        return color;
    }

    public static void create_adjacency_List(boolean[][] adjacencylist, int size, ArrayList<Integer> intList) {
        for (int i = 0; i < adjacencylist.length; i++) {
            for (int j = 0; j < adjacencylist.length; j++) {
                adjacencylist[i][j] = false;

            }
        }
        for (int k = 0; k < size; k = k + 2) {
            adjacencylist[intList.get(k)][intList.get(k + 1)] = true;
            adjacencylist[intList.get(k + 1)][intList.get(k)] = true;
        }

    }

    public static int create_array_vertex(String file, int max, ArrayList<Integer> intList) throws FileNotFoundException, IOException {
        BufferedReader br = null;
        String line = "";
        String fileSplitBy = "\\s+";
        br = new BufferedReader(new FileReader(file));
        int d;
        int d2;
        while (((line = br.readLine()) != null)) {
            String[] dj = line.split(fileSplitBy);
            d = Integer.parseInt(dj[0]);
            if (d > max) {
                max = d;
            }
            d2 = Integer.parseInt(dj[1]);
            if (d2 > max) {
                max = d2;
            }

            intList.add(d);
            intList.add(d2);

        }//end_while
        return max;
    }

    public static void print_sets(String[] col) {
        ArrayList<Integer> first_set = new ArrayList<Integer>();
        ArrayList<Integer> second_set = new ArrayList<Integer>();

        if (col[0] == "green") {
            System.out.println("Graph is not bipartite.");
        } else {
            for (int i = 0; i < col.length; i++) {
                if (col[i] == "red") {
                    first_set.add(i);

                } else {
                    second_set.add(i);
                }
            }

            System.out.println("to 1o sunolo");
            System.out.print("[");
            for (int i = 0; i < first_set.size(); i++) {

                if(i==(first_set.size()-1 )){
               System.out.print(first_set.get(i));
               }else
               {
                System.out.print(first_set.get(i)+",");
               }


            }
            System.out.print("]");
            System.out.println();
            System.out.println("to 2o ");
            System.out.print("[");
            for (int i = 0; i < second_set.size(); i++) {
               if(i==(second_set.size()-1 )){
               System.out.print(second_set.get(i));
               }else
               {
                System.out.print(second_set.get(i)+",");
               }

            }
            System.out.print("]");
        }
    }
}
