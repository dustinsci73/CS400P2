// starter class for a BalancedSearchTree
// you may implement AVL, Red-Black, 2-3 Tree, or 2-3-4 Tree
// be sure to include in class header which tree you have implemented
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

	// inner node class used to store key items and links to other nodes
	protected class Treenode<K extends Comparable<K>> {
		public Treenode(K item) {
			this(item,null,null,false);
		}
		public Treenode(K item, Treenode<K> left, Treenode<K> right, boolean color) {
			key = item;
			this.left = left;
			this.right = right;
			this.color = color;
		}
		
		boolean color;
		K key;
		Treenode<K> left;
		Treenode<K> right;
		Treenode<K> parent;
	}

    private static final boolean RED   = true;
    private static final boolean BLACK = false;
	protected Treenode<T> root;

	public int size(Treenode x)
//	{
//		if (x == null)
//		{
//			return 0;	
//		}
//		return x.size;
//	}
//	
//	private boolean isRed(Treenode x)
//	{
//		if (x == null)
//		{
//			return false;
//		}
//		return x.color == RED;
//	}
//	
//	public T get(T item)
//	{
//		if (item == null)	
//		{
//			throw new IllegalArgumentException();
//		}
//		return get(root, item);
//	}
//	
//	
//	private Treenode get(Treenode x, T item)
//	
//	{
//		
//	}
	public String inAscendingOrder() {
		//TODO : must return comma separated list of keys in ascending order
		return AscendingOrderHelper(root);
	}
	
	private String AscendingOrderHelper(Treenode<T> node)
	{
		String result = "";
		if (isEmpty())
		{
			return "";
		}
		else 
		{
			if (node.left != null)
			{
				result += AscendingOrderHelper(node.left);
			}
			result += node.key + " ";
			if (node.right != null)
			{
				result += AscendingOrderHelper(node.right);
			}
			return result;
		}
	}

	public boolean isEmpty() {
		//TODO return empty if there are no keys in structure
		return root == null;
	}

	public int height() {
		//TODO return the height of this tree
		return 0; 
	}

	public boolean lookup(T item) {
		//TODO must return true if item is in tree, otherwise false
		return get(item) != null;
	}

	public void insert(T item) {
		//TODO if item is null throw IllegalArgumentException, 
		// otherwise insert into balanced search tree
		if (item == null)
		{
			throw new IllegalArgumentException();
		}
		
		if (isEmpty())
		{
			root = new Treenode<T>(item);
			root.color = false;
		}
		else
		{
			Treenode<T> node = insertHelper(item, root);
			balance(node);
			root.color = false;
		}
	}

	private Treenode<T> insertHelper(T item, Treenode <T> node)
	{
		if (item.compareTo(node.key) > 0)
		{
			if (node.right == null)
			{
				Treenode<T> newTreenode = new Treenode<T> (item);
				newTreenode.parent = node;
				node.right = newTreenode;
				return newTreenode;
			}
			else 
			{
				return insertHelper(item, node.right);
			}
		}
		
		else 
		{
			if (node.left == null)
			{
				Treenode<T> newTreenode = new Treenode<T> (item);
				newTreenode.parent = node;
				node.left = newTreenode;
				return newTreenode;
			}
			else
			{
				return insertHelper(item, node.left);
			}
		}
	}
	public void delete(T item) {
		//TODO if item is null or not found in tree, return without error
		// else remove this item key from the tree and rebalance

		// NOTE: if you are unable to get delete implemented
		// it will be at most 5% of the score for this program assignment
		if (item == null || lookup(item) == false)
		{
			return;
		}
		else
		{
			deleteHelper(item, root);
		}
	}
	
	private Treenode<T> deleteHelper(T item, Treenode<T> node)
	{
		if (item.compareTo(node.key) > 0)
		{
			node.right = deleteHelper(item, node.right);
		}
		else if (item.compareTo(node.key) < 0)
		{
			node.left = deleteHelper(item, node.left);
		}
		else
		{
			
		}
	}


	// HINT: define this helper method that can find the smallest key 
	// in a sub-tree with "node" as its root
	// PRE-CONDITION: node is not null
	private T leftMost(Treenode<T> node) {
		// TODO return the key value of the left most node in this subtree
		// or return node's key if node does not have a left child
		return node.key;
	}

}

