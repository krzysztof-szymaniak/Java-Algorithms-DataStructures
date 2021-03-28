import java.util.Scanner;


public class PriorityQ {
	private class Node {
		public int x;
		public double p;

		public Node(int x, double p) {
			this.x = x;
			this.p = p;
		}
	}

	private int size;
	private Node[] heap;

	public PriorityQ(int capacity) {
		size = 0;
		heap = new Node[capacity];

	}

	public void insert(int x, double p) {
		if (size == heap.length) {
			System.err.println("Osiagnieto maksymalny rozmiar kolejki");
			return;
		}
		size++;
		int i = size - 1;
		heap[i] = new Node(x, p);
		while (i != 0 && heap[parent(i)].p > heap[i].p) {
			swap(heap, i, parent(i));
			i = parent(i);
		}
	}

	private void swap(Node[] arr, int a, int b) {
		Node tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;

	}


	public int pop() {
		if (size == 1) {
			size--;
			return top();
		}
		int val = top();
		heap[0] = heap[size - 1];
		size--;
		fix(0);
		return val;
	}

	private void fix(int i) {
		int l = left(i);
		int r = right(i);
		int min = i;
		if (l < size && heap[l].p < heap[i].p)
			min = l;
		if (r < size && heap[r].p < heap[min].p)
			min = r;
		if (min != i) {
			swap(heap, i, min);
			fix(min);
		}

	}

	public void priority(int x, double p) {
		for (int i = 0; i < size; i++) {
			if (heap[i].x == x && heap[x].p > p) {
				decPriority(i, p);
			}
		}
	}

	private void decPriority(int i, double p) {
		heap[i].p = p;
		while (i != 0 && heap[parent(i)].p > heap[i].p) {
			swap(heap, i, parent(i));
			i = parent(i);
		}
	}

	public String print() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append("(" + heap[i].x + "," + heap[i].p + "),");
		}
		if (size != 0) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}
	public double topPriority() {
		return heap[0].p;
	}
	
	private int left(int i) {
		return 2 * i + 1;
	}

	private int right(int i) {
		return 2 * i + 2;
	}

	private int parent(int i) {
		return (i - 1) / 2;
	}

	public int empty() {
		if (size == 0)
			return 1;
		return 0;

	}

	public int top() {
		return heap[0].x;
	}

	public static void main(String[] args) {
		try {
			Scanner in = new Scanner(System.in);
			System.err.println("Podaj liczbe operacji M:");
			int M = Integer.parseInt(in.nextLine());
			int requiredSize = 0;
			String[] lines = new String[M];
			for (int i = 0; i < M; i++) {
				String line = in.nextLine();
				if (line.split(" ")[0].equals("insert"))
					requiredSize++;
				lines[i] = line;
			}
			PriorityQ pQ = new PriorityQ(requiredSize);
			for (int i = 0; i < M; i++) {
				String[] command = lines[i].split(" ");
				switch (command[0]) {
				case "insert":
					int x = Integer.parseInt(command[1]);
					double p = Double.parseDouble(command[2]);
					pQ.insert(x, p);
					break;
				case "empty":
					System.out.println(pQ.empty());
					break;
				case "top":
					System.out.println(pQ.top());
					break;
				case "pop":
					if (pQ.empty() == 1)
						System.out.println();
					else
						System.out.println(pQ.pop());
					break;
				case "priority":
					x = Integer.parseInt(command[1]);
					p = Integer.parseInt(command[2]);
					pQ.priority(x, p);
					break;
				case "print":
					System.out.println(pQ.print());
					break;
				default:
					System.err.println("Nie znam takiej komendy: " + lines[i]);
				}

			}
		} catch (NumberFormatException e) {
			System.err.println("Nie jest to liczba opracji");
		}
	}

}
