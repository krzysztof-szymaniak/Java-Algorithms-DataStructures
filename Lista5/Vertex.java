import java.util.ArrayList;

public class Vertex {
	public int id;
	public double cost;
	public Vertex prev;
	public ArrayList<Double> weights;
	public ArrayList<Integer> neighbours;
	public Vertex(int v) {
		this.id = v;
		cost = Double.MAX_VALUE;
		prev = null;
		neighbours = new ArrayList<Integer>();
		weights = new ArrayList<Double>();
	}
}