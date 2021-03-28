import java.util.ArrayList;

public class Kruskal {
	ArrayList<Integer> list;
	int[] prev;
	int[] parent;
	int[] rank;
	Edge[] edges;

	private PriorityQ pQ;

	public Kruskal(Edge[] edges, int n, int m) {
		this.edges = edges;
		parent = new int[n];
		rank = new int[n];
		prev = new int[n];
		pQ = new PriorityQ(m);
		for (int i = 0; i < m; i++) {
			pQ.insert(i, edges[i].weight);
		}
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}

	public void run() {
		list = new ArrayList<Integer>();
		while (pQ.empty() == 0) {
			int e = pQ.pop();
			int u = edges[e].tail;
			int v = edges[e].head;
			if (find(u) != find(v)) {
				list.add(e);
				union(u, v);
			}
		}
		System.out.println("Krawedzie drzewa: ");
		double sum = 0;
		for (int e : list) {
			Edge edge = edges[e];
			int u = edge.tail;
			int v = edge.head;
			double w = edge.weight;
			sum += w;
			if(u < v)
				System.out.println(u+" "+v+" "+w);
			else
				System.out.println(v+" "+u+" "+w);
		}
		System.out.println("Waga drzewa: "+sum);
	}

	private int find(int x) {
		while (x != parent[x])
			x = parent[x];
		return x;
	}

	private void union(int x, int y) {
		int rootX = find(x);
		int rootY = find(y);
		if (rootX == rootY)
			return;
		if (rank[rootX] > rank[rootY]) {
			parent[rootY] = rootX;
		} else {
			parent[rootX] = rootY;
			if (rank[rootX] == rank[rootY]) {
				rank[rootY]++;
			}
		}
	}

}
