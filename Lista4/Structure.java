
public interface Structure {
	public void insert(String s);
	public void load(String fileName);
	public int delete(String s);
	public int find(String s);
	public String min();
	public String max();
	public String successor(String k);
	public void inorder();
	public int getInserts();
	public int getDeletes();
	public int getFinds();
	public int getMins();
	public int getMaxes();
	public int getSuccs();
	public int getInorders();
	public void deleteTest(String fileName);
	public void findTest(String fileName);
}
	
