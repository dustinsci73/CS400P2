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
// Known Bugs:	      No known
//
///////////////////////////////////////////////////////////////////////////////

/**
 * The program makes use of a balanced search tree, specifically RED-BLACK for rebalancing purposes. 
 * As for rebalancing after a new node is inserted, the program will work as expected. Yet, deleting and then rebalancing 
 * was not expected out of the p2 assignment. It should be noted P2 Makes use of the given skeleton code and uses a typical
 * red-black implementation to abide by given such properties. Program ultimately allows one to insert, delete, lookup, as well 
 * as rotate when the program has lost its integrity.
 *
 * @author Dustin Li, Brennan Fife
 */
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

	/**
	 * Inner class for nodes within the binary tree. Stores key items and links to 
	 * sibling, parent nodes.
	 * 
	 * @author Dustin Li, Brennan Fife
	 * @param <K> Generic comparable object for key when evaluating.
	 */
	protected class Treenode<K extends Comparable<K>> {
		public Treenode(K item) {
			this(item,null,null,null,false);
		}
		public Treenode(K item, Treenode<K> left, Treenode<K> right, Treenode<K> parent, boolean color) {
			key = item;           //key value
			this.left = left;     //left child
			this.right = right;   //right child
			this.color = color;   //node color
			this.parent = parent; //node parent
		}
		
		boolean color; //true/false to check if node is of a certain color. For verifying Red-Black property
		K key;
		Treenode<K> left; //links to left subtree
		Treenode<K> right; //links to right subtree
		Treenode<K> parent; //links to parent
	}

    private static final boolean RED   = true; 
    private static final boolean BLACK = false;
	protected Treenode<T> root;
	
	/**
	 * Wrapper method to call AscendingOrderHelper.
	 *
	 * @return AscendingOrder makes call to the helper method (AscendingOrderHelper)
	 */
	public String inAscendingOrder() {
		return AscendingOrderHelper(root);
	}
	
	/**
	 * Allows the program to descend down a given route. Will recursively call itself until
	 * given the final result, which at that point it will have nothing to its left
	 * or right.
	 *
	 * @param node The node that the program is on
	 * @return result The list of nodes
	 */
	private String AscendingOrderHelper(Treenode<T> node) {
		String result = "";
		if (isEmpty()) {
			return "";
		}
		else  {
			if (node.left != null) {
				result += AscendingOrderHelper(node.left);
			}
			result += node.key + ",";
			if (node.right != null) {
				result += AscendingOrderHelper(node.right);
			}
			return result;
		}
	}

	/**
	 * Returns true if our tree is empty (as when root is null)
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns height of tree (root = 0) by calling heightHelper method.
	 */
	public int height() {
		return heightHelper(root); 
	}
	
	/**
	 * Returns the height of our subtree. If the node is not there, the height is 0. Then recursively make 
	 * calls to into the height to the end height until i6 hit a point when the current node is null. At this point.
	 * the program then stops running.
	 *
	 * @param node Where the height starts the count
	 * @return The height of our subtree 
	 */
	private int heightHelper(Treenode<T> node) {
		if (node == null) {
			return 0;
		}
		else {
			return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
		}
	}

	/**
	 * Returns true if the tree contains the node with the item in question.
	 * Throws an IllegalArgumentException when the item sought is not within the tree.
	 *
	 * @param item The item to be looked up.
	 */
	public boolean lookup(T item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		else {
			return lookupHelper(root, item);
		}
	}
	
	/**
	 * Given the node, determine if the value is within our tree.
	 * If it is, returns true. Continuously searches through tree by moving down to lower
	 * or higher values as compared to the given value..
	 *
	 * @param node The node to start from
	 * @param item What value is contained within the node
	 */
	private boolean lookupHelper(Treenode<T> node, T item) {
		if (node == null) {
			return false;
		}
		if (node.key == item) {
			return true;
		}
		if (node.left != null) {
			return lookupHelper(node.left, item);
		}
		else {
			return lookupHelper(node.right, item);
		}
	}

	/**
	 * Inserts the new node with item as its key in the correct position. Makes use
	 * of the insertHelper. Will give the node the default color red. Throw an 
	 * IllegalArgumentException when the item is already in the tree (as no duplicates
	 * are allowed).
	 *
	 * @param item The item looking to be inserted
	 */
	public void insert(T item) {
		if (item == null || lookup(item) == true) {
			throw new IllegalArgumentException();
		}
		
		if (isEmpty()) {
			root = new Treenode<T>(item);
			root.color = false;
		}
		else {
			Treenode<T> node = insertHelper(item, root);
			if (!(node.parent == root)) {
				balance(node);
				root.color = false;
			}
		}
	}

	/**
	 * The insertHelper for the insert method. Recursivly called to evaluate where the given node needs to 
	 * be inserted within the tree by comparing it's value with the right and left child nodes. When there is
	 * none (and the node needs to be inserted), it is placed in the respective left or right null location.
	 *
	 * @param item The item to be inserted
	 * @param node The node which we are on
	 * @return newTreeNode insert the node when it is not found
	 * @return insertHelper Recursive call
	 */
	private Treenode<T> insertHelper(T item, Treenode <T> node) {
		if (item.compareTo(node.key) > 0) {
			if (node.right == null) {
				Treenode<T> newTreenode = new Treenode<T> (item);
				newTreenode.parent = node;
				node.right = newTreenode;
				return newTreenode;
			}
			else {
				return insertHelper(item, node.right);
			}
		}
		
		else  {
			if (node.left == null) {
				Treenode<T> newTreenode = new Treenode<T> (item);
				newTreenode.parent = node;
				node.left = newTreenode;
				return newTreenode;
			}
			else {
				return insertHelper(item, node.left);
			}
		}
	}
	
	/**
	 * Method that will call the deleteHelper method.  
	 *
	 * @param item The item looking to be deleted. Makes use of the deleteHelper method.
	 */
	public void delete(T item) {
		if (item == null || lookup(item) == false) {
			return;
		}
		else {
			deleteHelper(item, root);
		}
	}
	
	/**
	 * Helper method for delete. Allows the program to remove said node.
	 *
	 * @param item The node which is looking to be deleted.
	 * @param node The node in which the helper method is checking on
	 * @return The needed node to compile the 
	 */
	private Treenode<T> deleteHelper(T item, Treenode<T> node) {
		if (item.compareTo(node.key) > 0) {
			node.right = deleteHelper(item, node.right);
		}
		else if (item.compareTo(node.key) < 0) {
			node.left = deleteHelper(item, node.left);
		}
		else {
			if (node == root) {
				node = null;
				root = null;
			}
			else if (node.left == null || node.right == null) {
				if (node.left == null && node.right == null) {
					node.key = null;
					node = null;
				}
				else if (node.left != null)
				{
					return node.left;
				}
				else {
					return node.right;
				}
			}
			else {
				T succNode = leftMost(node.right);
				node.key = succNode;
				node.right = deleteHelper(succNode, node.right);
			}
		}
		return node;
	}
	/**
	 * Method which'll allow balance to find the uncle of the given node
	 *
	 * @param node The node which it begins with.
	 * @return The value of the uncle node
	 */
	private Treenode<T> getNode(Treenode<T> node) {
		if (node == root) {
			return null;
		}
		else if (node.parent.parent.left == node.parent) {
			return node.parent.parent.right;
		}
		else if (node.parent.parent.right == node.parent) {
			return node.parent.parent.left;
		}
		return null;
	}
	/**
	 * Method which will balances the red-black after after one is to perform insert. 
	 * This does not align when one is deleting from the tree. Uses the uncle 
	 * and grandparent of the given nodes to correctly assert where the node needs to move to.
	 *
	 * @param node The node of focal point
	 */
	private void balance(Treenode<T> node) {
		Treenode<T> balNode = getNode(node);
		if (node == root) {
			root.color = false;
			return;
		}
		if (node.parent.color != false) {
			if (balNode == null || balNode.color == false) {
				if (node.parent.parent.left == node.parent) {
					if (node.parent.left == node) {
						rotateRight(node.parent.parent);
						
						if (node.parent.color != node.parent.right.color) {
							boolean colorTemp = node.parent.color;
							node.parent.color = node.parent.right.color;
							node.parent.right.color = colorTemp;
						}
					}
					else {
						rotateLeft(node.parent);
						rotateRight(node.left);
						
						if (node.color != node.right.color) {
							boolean colorTemp = node.color;
							node.color = node.right.color;
							node.right.color = colorTemp;
						}
					}
				}
				else {
					if (node.parent.left == node) {
						rotateRight(node.parent);
						rotateLeft(node.left);
						
						if (node.color != node.left.color) {
							boolean colorTemp = node.color;
							node.color = node.left.color;
							node.left.color = colorTemp;
						}
					}
					else {
						rotateLeft(node.parent.parent);
						
						if (node.parent.color != node.parent.left.color) {
							boolean colorTemp = node.parent.color;
							node.parent.color = node.parent.left.color;
							node.parent.left.color = colorTemp;
						}
					}
				}
			}
			else if (balNode.color == true) {
				balNode.color = false;
				node.parent.color = false;
				node.parent.parent.color = true;
				balance(node.parent.parent);
			}
		}
	}
	
	/**
	 * Method which performs left-rotation. This is performed when the given tree is not balanced. Swaps
	 * values with temp to move values inside our node.
	 *
	 * @param node The node which we are rotating from (when the tree needs to be balanced from).
	 */
	private void rotateLeft(Treenode<T> node) {
		Treenode<T> temp;
		temp = node.right;
		node.right = temp.left;
		if (temp.left != null) {
			temp.left.parent = node;
		}
		temp.parent = node.parent;
		if (node.parent == null) {
			root = temp;
		}
		else {
			if(node == (node.parent).left) {
				node.parent.left = temp;
			}
			else {
				node.parent.right = temp;
			}
		}
		temp.left = node;
		node.parent = temp;
	}
	
	/**
	 * Method which performs right-rotation. This is performed when the given tree is not balanced. Swaps
	 * values with temp to move values inside our node.
	 *
	 * @param node The node which we are rotating from (when the tree needs to be balanced from).
	 */
	private void rotateRight(Treenode<T> node) {
		Treenode<T> temp;
		temp = node.left;
		node.left = temp.right;
		if (temp.right != null) {
			temp.right.parent = node;
		}
		temp.parent = node.parent;
		if (node.parent == null) {
			root = temp;
		}
		else {
			if(node == (node.parent).right) {
				node.parent.right = temp;
			}
			else {
				node.parent.left = temp;
			}
		}
		temp.right = node;
		node.parent = temp;
	}
	
	/**
	 * Method which'll recursively be called. Looks for our left-most node (smallest item). 
	 * Used when we are attempting to use successor to delete a node from our tree
	 *
	 * @param node The node being used to find it's most left node.
	 * @return The left-most node of our node
	 */
	private T leftMost(Treenode<T> node) {
		if (node.left == null) {
			return node.key; //found the given node and thus return.
		}
		return leftMost(node.left); 
	}
}
