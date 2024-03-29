import java.util.*;

public class GraphProcessor {
    static Graph graph;

    public GraphProcessor(Graph graph) {
        this.graph = graph;
    }

    public void dijkstra(int source, int[] costs, int[] parents) {
        Arrays.fill(costs, Integer.MAX_VALUE); // set all costs to infinity
        Arrays.fill(parents, -1); // set all parents to undefined
        costs[source] = 0; // set cost of source to 0
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(source, 0));
        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int u = node.vertex;
            if (node.cost > costs[u])
                continue; // skip if not improving
            for (Graph.Edge e : graph.getAdj(u)) {
                int v = e.getV();
                int newCost = costs[u] + e.getWeight();
                if (newCost < costs[v]) { // found shorter path to v
                    costs[v] = newCost;
                    parents[v] = u;
                    pq.offer(new Node(v, newCost));
                }
            }
        }
        // System.out.println("Using Dijkstra .. \n");
        // System.out.println("Costs:");
        // System.out.println(Arrays.toString(costs));
        // System.out.println("Parents:");
        // System.out.println(Arrays.toString(parents));
    }

    public boolean bellmanFord(int source, int[] costs, int[] parents) {
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
        // System.out.println("Using Bellman Ford .. \n");
        // System.out.println("Costs:");
        // System.out.println(Arrays.toString(costs));
        // System.out.println("Parents:");
        // System.out.println(Arrays.toString(parents));

        return true; // no negative-weight cycle found
    }

    public boolean floyedWarsell(int[][] costs, int[][] parents) {
        int n = graph.getV();
        boolean NoCycle = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                costs[i][j] = Integer.MAX_VALUE;
                parents[i][j] = -1;
            }
        }
        for (int i = 0; i < n; i++)
            costs[i][i] = 0;
        for (int i = 0; i < n; i++) {
            for (Graph.Edge e : graph.getEdges(i)) {
                costs[e.getU()][e.getV()] = e.getWeight();
                parents[e.getU()][e.getV()] = e.getU();
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (j != i && k != i && costs[j][i] != Integer.MAX_VALUE && costs[i][k] != Integer.MAX_VALUE) {
                        if (costs[j][i] + costs[i][k] < costs[j][k]) {
                            costs[j][k] = costs[j][i] + costs[i][k];
                            parents[j][k] = parents[i][k];
                        }
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (costs[i][i] < 0) {
                // List<Integer> cycle = new ArrayList<>();
                NoCycle = false;
                // int cur = parents[i][i];
                // while (cur != i) {
                // cycle.add(cur);
                // cur = parents[i][cur];
                // }
                // cycle.add(i);
                // for (int x : cycle) {
                // bellmanFord(x, costs[x], parents[x]);
                // }
                break;
            }
        }

        if (!NoCycle) {
            for (int i = 0; i < n; i++) {
                bellmanFord(i, costs[i], parents[i]);
            }
        }

        return NoCycle;
    }

}
