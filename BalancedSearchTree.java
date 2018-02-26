///////////////////////////////////////////////////////////////////////////////
// Title:            P2 (Red-Black Tree)
// Files:            BalancedSearchTree.java, SearchTreeADT.java, 
//		     TestSearchTree.java
// Semester:         CS 400 Spring 2018
//
// Author:           (your name)
// Email:            (your email address)
// Lecturer's Name:  Deb Deppeler
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// Known Bugs:	      None
//
///////////////////////////////////////////////////////////////////////////////

/**
 * The program makes use of a RED-BLACK TREE. Makes use of the given skeleton
 * code.
 *
 * @author (your name)
 */
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

	/**
	 * Class for nodes in the binary tree. Stores key items and links to 
	 * other nodes
	 * @author (your name)
	 * @param <K> generic comparable object for key
	 */
	protected class Treenode<K extends Comparable<K>> {
		public Treenode(K item) {
			this(item,null,null,null,false);
		}
		public Treenode(K item, Treenode<K> left, Treenode<K> right, Treenode<K> parent, boolean color) {
			key = item;
			this.left = left; //left child
			this.right = right; //right child
			this.color = color; //node color
			this.parent = parent; //node parent
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
	
	/**
	 * Used to call AscendingOrderHelper.
	 *
	 * @return AscendingOrderHelper call to the helper AscendingOrderHelper
	 */
	public String inAscendingOrder() {
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

	/**
	 * Returns true if our tree is empty
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the height of our tree
	 */
	public int height() {
		return heightHelper(root); 
	}
	
	/**
	 * Returns the height of our subtree.
	 *
	 * @param node Where we are starting the count on height
	 * @return The height of our subtree 
	 */
	private int heightHelper(Treenode<T> node)
	{
		if (node == null)
		{
			return -1;
		}
		else
		{
			return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
		}
	}

	/**
	 * Returns true if the tree contains a node with the item in question
	 *
	 * @param item The item we are looking up.
	 */
	public boolean lookup(T item) {
		if (item == null)
		{
			throw new IllegalArgumentException();
		}
		else
		{
			return lookupHelper(root, item);
		}
	}
	
	/**
	 * Given the node, are trying to determine if the value is within our tree.
	 * If it is, then return true.
	 *
	 * @param node The node to begin off of
	 * @param item What value is contained within the node
	 */
	private boolean lookupHelper(Treenode<T> node, T item)
	{
		if (node == null)
		{
			return false;
		}
		if (node.key == item)
		{
			return true;
		}
		if (node.left != null)
		{
			return lookupHelper(node.left, item);
		}
		else
		{
			return lookupHelper(node.right, item);
		}
		
	}

	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
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

	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param item (Describe the first parameter here)
	 * @param node (Do the same for each additional parameter)
	 * @return newTreeNode 
	 * @return insertHelper
	 */
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
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
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
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
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
			if (node.left == null || node.right == null)
			{
				if (node.left == null && node.right == null)
				{
					node = null;
				}
				else if (node.left != null)
				{
					return node.left;
				}
				else
				{
					return node.right;
				}
			}
			else
			{
				T succNode = leftMost(node.right);
				node.key = succNode;
				node.right = deleteHelper(succNode, node.right);
			}
		}
		return node;
	}
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	private void balance(Treenode<T> node)
	{
		
	}
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	private void rotateLeft(Treenode<T> node)
	{
		Treenode<T> temp;
		temp = node.right;
		node.right = temp.left;
		if (temp.left != null)
		{
			temp.left.parent = node;
		}
		temp.parent = node.parent;
		if (node.parent == null)
		{
			root = temp;
		}
		else
		{
			if(node == (node.parent).left)
			{
				node.parent.left = temp;
			}
			else
			{
				node.parent.right = temp;
			}
		}
		temp.left = node;
		node.parent = temp;
	}
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	private void rotateRight(Treenode<T> node)
	{
		Treenode<T> temp;
		temp = node.left;
		node.left = temp.right;
		if (temp.right != null)
		{
			temp.right.parent = node;
		}
		temp.parent = node.parent;
		if (node.parent == null)
		{
			root = temp;
		}
		else
		{
			if(node == (node.parent).right)
			{
				node.parent.right = temp;
			}
			else
			{
				node.parent.left = temp;
			}
		}
		temp.right = node;
		node.parent = temp;
	}
	
	/**
	 * (Write a succinct description of this method here.  If necessary,
	 * additional paragraphs should be preceded by <p>, the html tag for
	 * a new paragraph.)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	private T leftMost(Treenode<T> node) {
		// TODO return the key value of the left most node in this subtree
		// or return node's key if node does not have a left child
		return node.key;
	}
}
