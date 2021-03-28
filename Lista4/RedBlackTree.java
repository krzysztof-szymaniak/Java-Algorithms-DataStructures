import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class RedBlackTree implements Structure {
	private class NodeRB {
		NodeRB left;
		NodeRB right;
		NodeRB parent;
		String element;
		int count;
		int color;

		public NodeRB(String e) {
			left = right = parent = nil;
			count = 1;
			element = e;
			color = BLACK;
		}

	}

	private static final int RED = 0;
	private static final int BLACK = 1;
	private final NodeRB nil;
	private NodeRB rootRB;
	int inserts;
	int deletes;
	int finds;
	int maxes;
	int mins;
	int succs;
	int inorders;
	int numComps;
	int totalComps;
	

	public RedBlackTree() {
		nil = new NodeRB("");
		rootRB = nil;
		inserts = deletes = finds = maxes = mins = succs = inorders = totalComps = numComps = 0;
	}

	@Override
	public void insert(String s) {
		String e = s;
		if(!e.matches("[a-zA-Z]+"))
			return;
		inserts ++;
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
		NodeRB tmp = rootRB;
		NodeRB node = nil;
		if (rootRB == nil) {
			node = new NodeRB(e);
			rootRB = node;
		} else {
			while (true) { 
				if (e.compareTo(tmp.element) == 0) {
					tmp.count++;
					break;
				} else if (e.compareTo(tmp.element) < 0) {
					if (tmp.left == nil) {
						node = new NodeRB(e);
						tmp.left = node;
						node.color = RED; // nowy wezel jest czerwony
						node.parent = tmp;
						break;
					} else
						tmp = tmp.left;
				} else {
					if (tmp.right == nil) {
						node = new NodeRB(e);
						tmp.right = node;
						node.color = RED;
						node.parent = tmp;
						break;
					} else
						tmp = tmp.right;
				}
			}
			restoreRedProperty(node); // dodajac wezel moze powstajc konflikt dwoch czerwonych wezlow z rzedu, ktory trzeba wyprostowac
		}

	}
	// w celu naprawienia drzewa bedziemy kierowac sie kolorem wujka
	private void restoreRedProperty(NodeRB node) { 
		if (node == nil) // zostal dodany wezel ktory juz jest w drzewie, nie potrzeba przebudowy
			return;
		while (node.parent.color == RED) { // kolor nowododanego wezla jest czerwony, wiec jego rodzic nie moze tez byc
											// czerwony
			NodeRB uncle = nil;
			if (node.parent == node.parent.parent.left) { // jesli rodzic jest lewym synem dziadka
				uncle = node.parent.parent.right;

				if (uncle != nil && uncle.color == RED) { // jesli wujek jest czrwony
					node.parent.color = BLACK; // zmien kolor rodzica i wujka na czarny
					uncle.color = BLACK;
					node.parent.parent.color = RED; // a dziadka na czerwony
					node = node.parent.parent; // przejdz do dziadka
					continue;
				}
				if (node == node.parent.right) { // jesli wujek jest czarny, nalezy dokonac rotacji
					node = node.parent;
					rotateLeft(node);
				}
				node.parent.color = BLACK; // zmien kolor rodzica na czarny
				node.parent.parent.color = RED; // a dziadka na czerwony
				rotateRight(node.parent.parent);
			} else { // symetrycznie jesli rodzic jest prawym synem dziadka
				uncle = node.parent.parent.left;
				if (uncle != nil && uncle.color == RED) {
					node.parent.color = BLACK;
					uncle.color = BLACK;
					node.parent.parent.color = RED;
					node = node.parent.parent;
					continue;
				}
				if (node == node.parent.left) {
					node = node.parent;
					rotateRight(node);
				}
				node.parent.color = BLACK;
				node.parent.parent.color = RED;
				rotateLeft(node.parent.parent);
			}
		}
		rootRB.color = BLACK; // korzen zawsze jest czarny
	}

	private void rotateLeft(NodeRB node) { // rotacja wzgledem krawedzi miedzy node i node.parent
		if (node.parent != nil) {
			if (node == node.parent.left)
				node.parent.left = node.right;
			else
				node.parent.right = node.right;

			node.right.parent = node.parent;
			node.parent = node.right;
			if (node.right.left != nil) {
				node.right.left.parent = node;
			}
			node.right = node.right.left;
			node.parent.left = node;
		} else { // rotacja korzenia
			NodeRB r = rootRB.right;
			rootRB.right = r.left;
			r.left.parent = rootRB;
			rootRB.parent = r;
			r.left = rootRB;
			r.parent = nil;
			rootRB = r;
		}
	}

	private void rotateRight(NodeRB node) { // opracja symetryczna wzgledem lewej rotacji
		if (node.parent != nil) {
			if (node == node.parent.left) {
				node.parent.left = node.left;
			} else {
				node.parent.right = node.left;
			}

			node.left.parent = node.parent;
			node.parent = node.left;
			if (node.left.right != nil) {
				node.left.right.parent = node;
			}
			node.left = node.left.right;
			node.parent.right = node;
		} else { // rotacja korzenia
			NodeRB l = rootRB.left;
			rootRB.left = rootRB.left.right;
			l.right.parent = rootRB;
			rootRB.parent = l;
			l.right = rootRB;
			l.parent = nil;
			rootRB = l;
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
			System.err.println("Liczba wykonan insert: "+ (inserts - start));
			System.err.println("Sredni czas: "+ (double) totalTime/(inserts - start) +" ms");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int delete(String s) {
		String e = s;
		if(!e.matches("[a-zA-Z]+"))
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
		NodeRB z = findNode(e, rootRB); // wezel do usuniecia
		if (z == nil)
			return 0; // nie ma takiego wezla w drzewie
		z.count --;
		deletes ++;
		if(z.count != 0) 
			return 1;
		NodeRB x = nil;
		NodeRB y = z;
		int color = y.color;

		if (z.left != nil && z.right != nil) { // wezel ma oboje dzieci
			y = minimum(z.right);
			color = y.color;
			x = y.right;
			if (y.parent == z)
				x.parent = y;
			else {
				replace(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			replace(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.color = z.color;
		} else if (z.left != nil) { // wezel ma jedno dziecko
			x = z.left;
			replace(z, z.left);
		} else {
			x = z.right;
			replace(z, z.right); // jesli wezel ma prawe dziecko to zostanie nim zastapiony w przeciwnym wypadku nilem
		}
		if (color == BLACK) //  jesli usuwany wezel byl czarny to zostala zaburzona czarna wysokosc drzewa
			restoreBlackProperty(x);
		return 1;

	}

	private void replace(NodeRB a, NodeRB b) {
		if (a.parent == nil) // a jest korzeniem
			rootRB = b;
		else if (a == a.parent.left)
			a.parent.left = b;
		else
			a.parent.right = b;
		b.parent = a.parent;

	}
	// w celu naprawienia drzewa bedziemy kierowac sie kolorem brata
	private void restoreBlackProperty(NodeRB node) {
		while (node != rootRB && node.color == BLACK) {
			if (node == node.parent.left) { // przypadek jesli n jest lewym synem
				NodeRB sibling = node.parent.right; // jesli brat jest czerwony, nalezy zmienic go na czarnego, rodzica na czerwonego 
				if (sibling.color == RED) {
					sibling.color = BLACK;
					node.parent.color = RED;
					rotateLeft(node.parent); // i wykonac rotacje w kierunku polozenia wezla n wokol jego rodzica
					sibling = node.parent.right;
				}
				if (sibling.left.color == BLACK && sibling.right.color == BLACK) {// jesli brat nie jest czerwony, i jego dzieci tez nie sa, to pokoloruj go na czerwono
					sibling.color = RED;
					node = node.parent; // przejdz do rodzica
					continue;
				} else if (sibling.right.color == BLACK) {// jesli lewe dziecko brata jest czerwone a prawe czarne
					sibling.left.color = BLACK;
					sibling.color = RED;
					rotateRight(sibling); // dokonaj rotacji i zamien brata jego prawym bratem
					sibling = node.parent.right;
				}
				if (sibling.right.color == RED) { // jesli brat jest czerwony, to przypisz mu kolor jego rodzica
					sibling.color = node.parent.color;
					node.parent.color = BLACK;
					sibling.right.color = BLACK;
					rotateLeft(node.parent);
					node = rootRB; // przejdz do korzenia i zakoncz
				}
			} else {  	// przypadek symetryczny jesli n jest prawym synem
				NodeRB w = node.parent.left;
				if (w.color == RED) {
					w.color = BLACK;
					node.parent.color = RED;
					rotateRight(node.parent);
					w = node.parent.left;
				}
				if (w.right.color == BLACK && w.left.color == BLACK) {
					w.color = RED;
					node = node.parent;
					continue;
				} else if (w.left.color == BLACK) {
					w.right.color = BLACK;
					w.color = RED;
					rotateLeft(w);
					w = node.parent.left;
				}
				if (w.left.color == RED) {
					w.color = node.parent.color;
					node.parent.color = BLACK;
					w.left.color = BLACK;
					rotateRight(node.parent);
					node = rootRB;
				}
			}
		}
		node.color = BLACK; // korzen zawsze jest czarny

	}

	@Override
	public int find(String s) { // pozostale operacje takie same jak dla drzew BST		
		String e = s;
		if(!e.matches("[a-zA-Z]+"))
			return 0;
		finds ++;
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
		if (findNode(e, rootRB) == nil) {
			return 0;
		} else
			return 1;
	}

	private NodeRB findNode(String e, NodeRB r) {
		if (r == nil) {
			return nil;
		}
		numComps ++;
		totalComps ++;		
		if (e.compareTo(r.element) == 0) {	
			return r;
		}
		numComps ++;
		totalComps ++;		
		if (e.compareTo(r.element) < 0) {
			return findNode(e, r.left);
		}
		else
			return findNode(e, r.right);
	}
	
	@Override
	public void deleteTest(String fileName) {// funkcja odwrotna do load(), usuwa z drzewa wszystkie wystapienia slowa w tekscie
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
			System.err.println("Liczba wykonan delete: "+ (deletes- start));
			System.err.println("Sredni czas: "+ (double)totalTime/(deletes - start) +" ms");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void findTest(String fileName) { // funkcja wyszukujaca w drzewie wszystkich wystapien wyrazu w tekscie
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
		int maxComps = Integer.MIN_VALUE;
		int minComps = Integer.MAX_VALUE;
		totalComps = 0;
		try {
			String line = br.readLine();
			while (line != null) {
				String[] words = line.split(" ");
				for (String w : words) {			
					find(w);
					if(numComps == 0)
						continue;
					if(numComps < minComps)
						minComps = numComps;
					if(numComps > maxComps)
						maxComps = numComps;
				}
				line = br.readLine();
			}
			totalTime = System.currentTimeMillis() - begin;
			br.close();
			System.err.println("Liczba wykonan find: "+ (finds - start));
			System.err.println("Sredni czas: "+ (double)totalTime/(finds - start) +" ms");
			System.err.println("Srednia liczba porowanan: "+(double)totalComps/(finds - start));
			System.err.println("Maksymalna liczba porowanan: "+maxComps);
			System.err.println("Minimalna liczba porowanan: "+minComps);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String min() {
		mins ++;
		NodeRB r = rootRB;
		if (r == nil)
			return "";
		return minimum(r).element;
	}

	private NodeRB minimum(NodeRB r) {
		while (r.left != nil)
			r = r.left;
		return r;
	}

	@Override
	public String max() {
		maxes ++;
		NodeRB r = rootRB;
		if (r == nil)
			return "";
		return maximum(r).element;
	}

	private NodeRB maximum(NodeRB r) {
		while (r.right != nil)
			r = r.right;
		return r;
	}

	@Override
	public String successor(String k) {
		succs ++;
		NodeRB node = findNode(k, rootRB);
		if (node == nil) {
			System.err.println("Wezel" + k + " nie istnieje, nie ma succesora");
			return "";
		}

		if (node.right != nil) {
			return minimum(node.right).element;
		} else {
			NodeRB p = node.parent;
			while (p != nil && node == p.right) {
				node = p;
				p = p.parent;
			}
			if (p == nil)
				return "";
			return p.element;

		}
	}

	@Override
	public void inorder() {
		inorders ++;
		if(rootRB == nil)
			System.out.println("");
		else {
			traverse(rootRB);
		}
	}

	private void traverse(NodeRB r) {
	
		if (r.left != nil)
			traverse(r.left);
		for (int i = 0; i < r.count; i++)
			System.out.print(r.element+", ");
		if (r.right != nil)
			traverse(r.right);
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
