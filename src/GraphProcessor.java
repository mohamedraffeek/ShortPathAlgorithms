import java.util.*;

public class GraphProcessor {
     static Graph graph;
     int source;
     int[] costs;
     int[] parents;

    public GraphProcessor(Graph graph, int source, int[] costs, int[] parents) {
        this.graph = graph;
        this.source = source;
        this.costs = costs;
        this.parents = parents;
    }

    public static void dijkstra(int source, int[] costs, int[] parents) {
        Arrays.fill(costs, Integer.MAX_VALUE); // set all costs to infinity
        Arrays.fill(parents, -1); // set all parents to undefined
        costs[source] = 0; // set cost of source to 0
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.vertex;
            if (node.cost > costs[u]) continue; // skip if not improving
            for (Graph.Edge e : graph.getAdj(u)) {
                int v = e.getV();
                int newCost = costs[u] + e.weight;
                if (newCost < costs[v]) { // found shorter path to v
                    costs[v] = newCost;
                    parents[v] = u;
                    pq.offer(new Node(v, newCost));
                }
            }
        }
        System.out.println("Using Dijkstra .. \n");
        System.out.println("Costs:");
        System.out.println(Arrays.toString(costs));
        System.out.println("Parents:");
        System.out.println(Arrays.toString(parents));
    }

    public static boolean bellmanFord(int source, int[] costs, int[] parents) {
        Arrays.fill(costs, Integer.MAX_VALUE); // set all costs to infinity
        Arrays.fill(parents, -1); // set all parents to undefined
        costs[source] = 0; // set cost of source to 0

        // Relax edges repeatedly
        for (int i = 0; i < graph.getV() - 1; i++) {
            for (int u = 0; u < graph.getV(); u++) {
                for (Graph.Edge e : graph.getEdges(u)) {
                    int v = e.getV();
                    int weight = e.getWeight();
                    if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                        costs[v] = costs[u] + weight;
                        parents[v] = u;
                    }
                }
            }
        }

        // Check for negative-weight cycles
        for (int u = 0; u < graph.getV(); u++) {
            for (Graph.Edge e : graph.getEdges(u)) {
                int v = e.getV();
                int weight = e.getWeight();
                if (costs[u] != Integer.MAX_VALUE && costs[u] + weight < costs[v]) {
                    return false; // negative-weight cycle found
                }
            }
        }
        System.out.println("Using Bellman Ford .. \n");
        System.out.println("Costs:");
        System.out.println(Arrays.toString(costs));
        System.out.println("Parents:");
        System.out.println(Arrays.toString(parents));

        return true; // no negative-weight cycle found
    }





}