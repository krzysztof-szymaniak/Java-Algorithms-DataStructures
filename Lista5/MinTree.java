import java.util.Scanner;


public class MinTree {
	static String algorithm;

	public static void main(String[] args) {
		try {
			
			if(args[0].equals("-p")) {
				algorithm = "-p";
			}
			else if(args[0].equals("-k")) {
				algorithm = "-k";
			}
			else {
				System.err.println("Bledny argument");
			}
			Scanner in = new Scanner(System.in);
			System.err.println("Podaj liczbe wierzcholkow:");
			int n = Integer.parseInt(in.nextLine());
			System.err.println("Podaj liczbe krawedzi:");
			int m = Integer.parseInt(in.nextLine());
			
			if(algorithm.equals("-p")) {
				Graph G = new Graph(n, m);
				for (int i = 0; i < m; i++) {
					String line = in.nextLine();
					int u = Integer.parseInt(line.split(" ")[0]);
					int v = Integer.parseInt(line.split(" ")[1]);
					double w = Double.parseDouble(line.split(" ")[2]);
					G.addEdge(u, v, w, false);
				}
				Prim prim = new Prim(G);
				prim.run();
			}
			else if(algorithm.equals("-k")) {
				Edge[] edges = new Edge[m];
				for (int i = 0; i < m; i++) {
					String line = in.nextLine();
					int u = Integer.parseInt(line.split(" ")[0]);
					int v = Integer.parseInt(line.split(" ")[1]);
					double w = Double.parseDouble(line.split(" ")[2]);
					edges[i] = new Edge(u, v, w);
				}
				Kruskal kruskal = new Kruskal(edges, n, m);
				kruskal.run();
			}
			
		}catch(ArrayIndexOutOfBoundsException ex) {
			ex.printStackTrace();
			System.err.println("Brak argumentu");
		}

	}

}
