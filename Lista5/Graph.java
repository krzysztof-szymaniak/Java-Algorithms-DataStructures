
public class Graph {

	public int n;
	public int m;
	public Vertex[] vertices;

	public Graph(int n, int m) {
		this.n = n;
		this.m = m;
		vertices = new Vertex[n];
		for (int i = 0; i < n; i++)
			vertices[i] = new Vertex(i);

	}

	public void addEdge(int u, int v, double weight, boolean isDirected) {
		if(isDirected) {
			vertices[u].neighbours.add(v);
			vertices[u].weights.add(weight);
		}else {
			vertices[u].neighbours.add(v);
			vertices[u].weights.add(weight);
			vertices[v].neighbours.add(u);
			vertices[v].weights.add(weight);
		}

	}

}
