import java.util.*;

public class BSTMap<K extends Comparable<K>, V > implements  Map<K, V>  {
	
	private BinarySearchTree<K, V> bst;

	public BSTMap () {
		bst = new BinarySearchTree<K, V>();
	}

	public boolean containsKey(K key) {
		try {
			return bst.find(key) != null;
		} catch (KeyNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public V get (K key) throws KeyNotFoundException {
		return bst.find(key);
	}

	public List<Entry<K,V> >	entryList() {
		return bst.entryList();
	}

	public void put (K key, V value) {
		bst.insert(key, value);
	}

	public int size() {
		return bst.size();
	}

	public void clear() {
		bst.clear();
	}

	public int getGetLoopCount() {
		return bst.getFindLoopCount();
	}

	public int getPutLoopCount() {
		return bst.getInsertLoopCount();
	}

	public void resetGetLoops() {
		bst.resetFindLoops();
	}
	public void resetPutLoops() {
		bst.resetInsertLoops();
	}
}
