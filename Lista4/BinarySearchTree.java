import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BinarySearchTree implements Structure {
	private class NodeBST {
		NodeBST left;
		NodeBST right;
		NodeBST parent;

		String element;
		int count;

		public NodeBST(String elem) {		
			left = right = parent = null;
			count = 1;
			element = elem;
		}

	}

	NodeBST rootBST;
	int inserts;
	int deletes;
	int finds;
	int maxes;
	int mins;
	int succs;
	int inorders;
	long numComps;
	long totalComps;

	public BinarySearchTree() {
		rootBST = null;
		inserts = deletes = finds = maxes = mins = succs = inorders = 0;
		totalComps = 0;
		numComps = 0;
	}

	@Override
	public void insert(String s) {
		String e = s;
		if (!e.matches("[a-zA-Z]+"))
			return;

		char c0 = e.charAt(0);
		char cn = e.charAt(e.length() - 1);
		while (!Character.isLetter(c0)) {
			e = e.substring(1);
			c0 = e.charAt(0);
		}
		while (!Character.isLetter(cn)) {
			e = e.substring(0, e.length() - 1);
			cn = e.charAt(e.length() - 1);
		}
		inserts++;
		if (rootBST == null) {
			rootBST = new NodeBST(e);
		} else {
			NodeBST tmp = rootBST;
			NodeBST node;
			while (true) {
				if (e.compareTo(tmp.element) == 0) {
					tmp.count++;
					break;
				} else if (e.compareTo(tmp.element) < 0) {
					if (tmp.left == null) {
						node = new NodeBST(e);
						tmp.left = node;
						node.parent = tmp;
						break;
					} else
						tmp = tmp.left;
				} else {
					if (tmp.right == null) {
						node = new NodeBST(e);
						tmp.right = node;
						node.parent = tmp;
						break;
					} else
						tmp = tmp.right;
				}
			}
		}

	}

	@Override
	public void load(String fileName) {
		File f = new File(fileName);
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku " + fileName);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fos));
		long totalTime = 0;
		int start = inserts;
		long begin = System.currentTimeMillis();
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {
					insert(w);
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan insert: " + (inserts - start));
			System.err.println("Sredni czas: " + (double) totalTime / (inserts - start) + " ms");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int delete(String s) {
		String e = s;
		if (!e.matches("[a-zA-Z]+"))
			return 0;
		char c0 = e.charAt(0);
		char cn = e.charAt(e.length() - 1);
		while (!Character.isLetter(c0)) {
			e = e.substring(1);
			c0 = e.charAt(0);
		}
		while (!Character.isLetter(cn)) {
			e = e.substring(0, e.length() - 1);
			cn = e.charAt(e.length() - 1);
		}

		NodeBST z = findNode(e, rootBST);
		if (z == null)
			return 0;
		z.count--;
		deletes++;
		if (z.count != 0)
			return 1;

		NodeBST x = null;
		NodeBST y = z;
		if (z.left != null && z.right != null) { // wezel ma oboje dzieci
			y = minimum(z.right);
			x = y.right;
			if (y.parent == z && x != null) {
				x.parent = y;
			}else {
				replace(y, y.right);
				y.right = z.right;
				if(y.right != null)
					y.right.parent = y;
			}
			replace(z, y);
			y.left = z.left;
			y.left.parent = y;
		} else if (z.left != null) { // wezel ma jedno dziecko
			x = z.left;
			replace(z, z.left);
		} else {
			x = z.right;
			replace(z, z.right);
		}
		return 1;
	}

	private void replace(NodeBST a, NodeBST b) {
		if (a.parent == null) // a jest korzeniem
			rootBST = b;
		else if (a == a.parent.left)
			a.parent.left = b;
		else
			a.parent.right = b;
		if(b != null) {
		b.parent = a.parent;
		}
	}

	@Override
	public int find(String s) {
		String e = s;
		if (!e.matches("[a-zA-Z]+"))
			return 0;
		finds++;
		numComps = 0;
		char c0 = e.charAt(0);
		char cn = e.charAt(e.length() - 1);
		while (!Character.isLetter(c0)) {
			e = e.substring(1);
			c0 = e.charAt(0);
		}
		while (!Character.isLetter(cn)) {
			e = e.substring(0, e.length() - 1);
			cn = e.charAt(e.length() - 1);
		}
		if (findNode(e, rootBST) == null) {
			return 0;
		} else
			return 1;
	}

	private NodeBST findNode(String e, NodeBST r) {
		NodeBST node = r;
		while (node != null) {
			numComps++;
			totalComps++;
			if (e.compareTo(node.element) == 0) {
				return node;
			}
			numComps++;
			totalComps++;
			if (e.compareTo(node.element) < 0) {
				node = node.left;
			}
			else {
				node = node.right;
			}
		}
		return node;
	}

	@Override
	public String min() {
		mins++;
		NodeBST r = rootBST;
		if (r == null)
			return "";
		return minimum(r).element;
	}

	private NodeBST minimum(NodeBST r) {
		while (r.left != null)
			r = r.left;
		return r;
	}

	@Override
	public String max() {
		maxes++;
		NodeBST r = rootBST;
		if (r == null)
			return "";
		return maximum(r).element;
	}

	private NodeBST maximum(NodeBST r) {
		while (r.right != null)
			r = r.right;
		return r;
	}

	@Override
	public String successor(String k) {
		succs++;
		NodeBST node = findNode(k, rootBST);
		if (node == null) {
			System.err.println("Wezel" + k + " nie istnieje, nie ma succesora");
			return "";
		}

		if (node.right != null) {
			return minimum(node.right).element;
		} else {
			NodeBST p = node.parent;
			while (p != null && node == p.right) {
				node = p;
				p = p.parent;
			}
			if (p == null)
				return "";
			return p.element;

		}
	}

	@Override
	public void inorder() {
		inorders++;
		Stack<NodeBST> stack = new Stack<NodeBST>();
		NodeBST n = rootBST;
		if(n == null) {
			System.out.println("");
			return;
		}
		while (!stack.empty() || n != null) {
			while (n != null) {
				stack.push(n);
				n = n.left;
			}
			n = stack.pop();
			for (int i = 0; i < n.count; i++)
				System.out.print(n.element + ", ");
			n = n.right;
		}
	}

	@Override
	public void deleteTest(String fileName) { // funkcja odwrotna do load(), usuwa z drzewa wszystkie slowa z tekstu
		File f = new File(fileName);
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku " + fileName);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fos));
		long totalTime = 0;
		int start = deletes;
		long begin = System.currentTimeMillis();
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {
					delete(w);
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan delete: " + (deletes - start));
			System.err.println("Sredni czas: " + (double) totalTime / (deletes - start) + " ms");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void findTest(String fileName) {// funkcja wyszukujaca w drzewie wszystkich wyrazow w tekscie
		File f = new File(fileName);
		FileInputStream fos = null;
		try {
			fos = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			System.err.println("Nie znaleziono pliku " + fileName);
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fos));
		long totalTime = 0;
		int start = finds;
		long begin = System.currentTimeMillis();
		long maxComps = Long.MIN_VALUE;
		long minComps = Long.MAX_VALUE;
		totalComps = 0;
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {
					find(w);
					if(numComps == 0)
						continue;
					if (numComps < minComps)
						minComps = numComps;
					if (numComps > maxComps)
						maxComps = numComps;
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan find: " + (finds - start));
			System.err.println("Sredni czas: " + (double) totalTime / (finds - start) + " ms");
			System.err.println("Srednia liczba porowanan: " + (double) totalComps / (finds - start));
			System.err.println("Maksymalna liczba porowanan: " + maxComps);
			System.err.println("Minimalna liczba porowanan: " + minComps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getInserts() {
		return inserts;
	}

	@Override
	public int getDeletes() {
		return deletes;
	}

	@Override
	public int getFinds() {
		return finds;
	}

	@Override
	public int getMins() {
		return mins;
	}

	@Override
	public int getMaxes() {
		return maxes;
	}

	@Override
	public int getSuccs() {
		return succs;
	}

	@Override
	public int getInorders() {
		return inorders;
	}

}