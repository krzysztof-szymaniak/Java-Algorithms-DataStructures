
public class Prim {
	private boolean[] visited;
	int[] prev;
	private PriorityQ pQ;
	private Graph g;

	public Prim(Graph g) {
		this.g = g;
		prev = new int[g.n];
		visited = new boolean[g.n];
		pQ = new PriorityQ(g.n);
		for (int i = 0; i < g.n; i++) {
			pQ.insert(i, Double.MAX_VALUE);
		}
		pQ.priority(0, 0);
		prev[0] = -1;
	}

	public void run() {
		Vertex[] vertices = g.vertices;
		while (pQ.empty() == 0) {
			double p = pQ.topPriority();
			int min = pQ.pop();
			Vertex u = vertices[min];
			visited[min] = true;
			u.cost = p;
			for (int i = 0; i < u.neighbours.size(); i++) {
				int neighbourID = u.neighbours.get(i);
				Vertex v = vertices[neighbourID];
				if (v.cost > u.weights.get(i) && !visited[neighbourID]) {
					v.cost = u.weights.get(i);
					prev[neighbourID] = min;
					pQ.priority(neighbourID, v.cost);
				}
			}
		}
		double sum = 0;
		System.out.println("Krawedzie drzewa: ");
		for (int i = 1; i < g.n; i++) {
			Vertex v = vertices[i];
			Vertex p = vertices[prev[i]];
			sum += v.cost;
			if(p.id < v.id)
				System.out.println(p.id+" "+v.id+" "+v.cost);
			else 
				System.out.println(v.id+" "+p.id+" "+v.cost);
			
		}
		System.out.println("Waga drzewa: " + sum);
	}

}
