import java.util.Scanner;
import java.util.Stack;

public class Dijkstra {
	PriorityQ pQ;
	Graph G;
	int start;

	public Dijkstra(Graph G, int start) {
		this.G = G;
		this.start = start;
		pQ = new PriorityQ(G.n);
		for (int i = 0; i < G.n; i++) {
			pQ.insert(i, Integer.MAX_VALUE);
		}
		pQ.priority(start, 0);

	}

	public void run() {
		Vertex[] vertices = G.vertices;
		vertices[start].prev = vertices[start];
		while (pQ.empty() == 0) {
			double p = pQ.topPriority();
			int min = pQ.pop();
			vertices[min].cost = p;
			Vertex u = vertices[min];
			for (int i = 0; i < u.neighbours.size(); i++) {
				int neighbourID = u.neighbours.get(i);
				Vertex v = vertices[neighbourID];
				if (v.cost > u.cost + u.weights.get(i)) {
					v.cost = u.cost + u.weights.get(i);
					v.prev = u;
					pQ.priority(neighbourID, v.cost);
				}
			}

		}
		for (int i = 0; i < G.n; i++) {
			Vertex v = vertices[i];
			System.out.println("["+v.id + "], " + v.cost);
			Stack<Vertex> stack = new Stack<Vertex>();			
			do{
				if (v.prev == null) {
					stack = null;
					break;
				}
				stack.push(v);
				v = v.prev;
			}while (v.prev != v);
			if (stack != null) {
				StringBuilder sb = new StringBuilder("(["+vertices[start].id+"], "+0.0+")->");
				while (! stack.empty()) {
					v = stack.pop();
					double weight = v.cost - v.prev.cost;
					sb.append("(["+v.id+"], "+weight+")->");
				}
				sb.setLength(sb.length()-2);
				System.err.println(sb.toString());
			}
		}

	}

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			System.err.println("Podaj liczbe wierzcholkow:");
			int n = Integer.parseInt(in.nextLine());
			System.err.println("Podaj liczbe krawedzi:");
			int m = Integer.parseInt(in.nextLine());
			Graph G = new Graph(n, m);
			for (int i = 0; i < m; i++) {
				String line = in.nextLine();
				int u = Integer.parseInt(line.split(" ")[0]);
				int v = Integer.parseInt(line.split(" ")[1]);
				double w = Double.parseDouble(line.split(" ")[2]);
				G.addEdge(u, v, w, true);
			}
			int start = Integer.parseInt(in.nextLine());
			long time = System.currentTimeMillis();
			Dijkstra dj = new Dijkstra(G, start);
			dj.run();
			System.err.println("W czasie: "+(System.currentTimeMillis()-time)+" ms");

		} catch (NumberFormatException e) {
			System.err.println("Nie jest to liczba");
		}

	}

}
//13
//0 1 1
//1 2 2
//2 3 1
//0 4 4
//0 5 8
//1 5 6
//1 6 6
//2 6 2
//3 6 1
//3 7 4
//4 5 5
//6 5 1
//6 7 1
