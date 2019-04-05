import java.util.*;

//
// An implementation of a binary search tree.
//
// This tree stores both keys and values associated with those keys.
//
// More information about binary search trees can be found here:
//
// http://en.wikipedia.org/wiki/Binary_search_tree
//
// Note: Wikipedia is using a different definition of
//       depth and height than we are using.  Be sure
//       to read the comments in this file for the
//	 	 height function.
//
class BinarySearchTree <K extends Comparable<K>, V>  {

	public static final int BST_PREORDER  = 1;
	public static final int BST_POSTORDER = 2;
	public static final int BST_INORDER   = 3;

	// These are package friendly for the TreeView class
	BSTNode<K,V>	root;
	int		count;

	int		findLoops;
	int		insertLoops;

	public BinarySearchTree () {
		root = null;
		count = 0;
		resetFindLoops();
		resetInsertLoops();
	}

	public int getFindLoopCount() {
		return findLoops;
	}

	public int getInsertLoopCount() {
		return insertLoops;
	}

	public void resetFindLoops() {
		findLoops = 0;
	}
	public void resetInsertLoops() {
		insertLoops = 0;
	}

	//
	// Purpose:
	//
	// Insert a new Key:Value Entry into the tree.  If the Key
	// already exists in the tree, update the value stored at
	// that node with the new value.
	//
	// Pre-Conditions:
	// 	the tree is a valid binary search tree
	//
	public void insert (K k, V v) {
		root = insert(root, k, v);
	}
	
	//recursive insert function
	private BSTNode<K, V> insert(BSTNode<K, V> node, K k, V v) {
		if (node == null) {
			 count++;
	         return new BSTNode<K, V>(k, v);
		}
	      if (k.compareTo(node.key) == 0) {
	    	  node.value = v;
	    	  return node;
	      }
	      if (k.compareTo(node.key) < 0)
	    	  node.left = insert(node.left, k, v);
	      if(k.compareTo(node.key) > 0)
	    	  node.right = insert(node.right, k, v);
	      
	      //Increment loop
	      insertLoops++;
	      
	      return node;
	}

	//
	// Purpose:
	//
	// Return the value stored at key.  Throw a KeyNotFoundException
	// if the key isn't in the tree.
	//
	// Pre-conditions:
	//	the tree is a valid binary search tree
	//
	// Returns:
	//	the value stored at key
	//
	// Throws:
	//	KeyNotFoundException if key isn't in the tree
	//
	public V find (K key) throws KeyNotFoundException {
		return find(key, root).value;
	}
	
	//recursive find function
	private BSTNode<K, V> find(K key, BSTNode<K, V> node) throws KeyNotFoundException {
		//Increment loop
		findLoops++;
		
		if (node == null)
	         throw new KeyNotFoundException();
	      else
	      if (key.compareTo(node.key) == 0)
	      	return node;
	      else
	      if (key.compareTo(node.key) < 0)
	         return find(key, node.left);
	      else
	         return find(key, node.right);
	}

	//
	// Purpose:
	//
	// Return the number of nodes in the tree.
	//
	// Returns:
	//	the number of nodes in the tree.
	public int size() {
		return count;
	}
	

	//
	// Purpose:
	//	Remove all nodes from the tree.
	//
	public void clear() {
		root = null;
	}

	//
	// Purpose:
	//
	// Return the height of the tree.  We define height
	// as being the number of nodes on the path from the root
	// to the deepest node.
	//
	// This means that a tree with one node has height 1.
	//
	// Examples:
	//	See the assignment PDF and the test program for
	//	examples of height.
	//
	public int height() {
		return height(root);
	}

	private int height(BSTNode<K, V> node) {
		if(node == null) return 0;
		else
		return 1 + Math.max(height(node.left), height(node.right));
	}
	//
	// Purpose:
	//
	// Return a list of all the key/value Entrys stored in the tree
	// The list will be constructed by performing a level-order
	// traversal of the tree.
	//
	// Level order is most commonly implemented using a queue of nodes.
	//
	//  From wikipedia (they call it breadth-first), the algorithm for level order is:
	//
	//	levelorder()
	//		q = empty queue
	//		q.enqueue(root)
	//		while not q.empty do
	//			node := q.dequeue()
	//			visit(node)
	//			if node.left != null then
	//			      q.enqueue(node.left)
	//			if node.right != null then
	//			      q.enqueue(node.right)
	//
	// Note that we will use the Java LinkedList as a Queue by using
	// only the removeFirst() and addLast() methods.
	//
	public List<Entry<K,V>> entryList() {
		List<Entry<K,V> > l = new LinkedList<Entry<K,V> >();
		LinkedList<BSTNode<K, V>> queue = new LinkedList<BSTNode<K, V>>();
		queue.addLast(root);
		
		while(!queue.isEmpty()) {
			BSTNode<K, V> temp = queue.removeFirst();
			l.add(new Entry<K, V>(temp.key, temp.value));
			
			if(temp.left != null) 
				queue.addLast(temp.left);
			if(temp.right != null) 
				queue.addLast(temp.right);
		}
		return l;
	}

	//
	// Purpose:
	//
	// Return a list of all the key/value Entrys stored in the tree
	// The list will be constructed by performing a traversal 
	// specified by the parameter which.
	//
	// If which is:
	//	BST_PREORDER	perform a pre-order traversal
	//	BST_POSTORDER	perform a post-order traversal
	//	BST_INORDER	perform an in-order traversal
	//
	public List<Entry<K,V> > entryList (int which) {
		List<Entry<K,V> > l = new LinkedList<Entry<K,V> >();
		
		switch(which) {
		case BST_INORDER:
			inOrder(root, l);
			break;
		case BST_POSTORDER:
			postOrder(root, l);
			break;
		case BST_PREORDER:
			preOrder(root, l);
			break;
		}
			
		return l;
	}
	
	private void postOrder(BSTNode<K, V> node, List<Entry<K,V>> list) {
		if(node == null) return;
		//left
		postOrder(node.left, list);
		//right
		postOrder(node.right, list);
		//add to list
		list.add(new Entry<K, V>(node.key, node.value));
	}
	
	private void inOrder(BSTNode<K, V> node, List<Entry<K,V>> list) {
		if(node == null) return;
		//left
		inOrder(node.left, list);
		//add to list
		list.add(new Entry<K, V>(node.key, node.value));
		//right
		inOrder(node.right, list);
	}
	
	private void preOrder(BSTNode<K, V> node, List<Entry<K,V>> list) {
		if(node == null) return;
		//add to list
		list.add(new Entry<K, V>(node.key, node.value));
		//left
		preOrder(node.left, list);
		//right
		preOrder(node.right, list);
	}

	// Your instructor had the following private methods in his solution:
	// private void doInOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private void doPreOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private void doPostOrder (BSTNode<K,V> n, List <Entry<K,V> > l);
	// private int doHeight (BSTNode<K,V> t)
	
	public static void main(String[] args) {
		BinarySearchTree<String,String>	t = new BinarySearchTree<String,String>();

		t.insert("dick", "h");
		t.insert("s", "h");
		t.insert("d", "h");
		t.insert("sd", "ddfsad");
		t.insert("fg", "sa");
		
		t.entryList(1);
		System.out.println(t);
		
	}
}
