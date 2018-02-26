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
 * code. Uses typical red-black methods to follow given properties. This program 
 * allows one to insert, delete, lookup, as well as rotate when the integrety of a 
 * such red-black properties are lost.
 *
 * @author Dustin Li, Brennan Fife
 */
public class BalancedSearchTree<T extends Comparable<T>> implements SearchTreeADT<T> {

	/**
	 * Class for nodes within the binary tree. Stores key items and links to 
	 * sibling or parent nodes.
	 * 
	 * @author Dustin Li, Brennan Fife
	 * @param <K> Generic comparable object for key
	 */
	protected class Treenode<K extends Comparable<K>> {
		public Treenode(K item) {
			this(item,null,null,null,false);
		}
		public Treenode(K item, Treenode<K> left, Treenode<K> right, Treenode<K> parent, boolean color) {
			key = item; //item value.
			this.left = left; //left child
			this.right = right; //right child
			this.color = color; //node color
			this.parent = parent; //node parent
		}
		
		boolean color; //true, false to check if program is following Red-Black property
		K key;
		Treenode<K> left; //links to left subtree
		Treenode<K> right; //links to right subtree
		Treenode<K> parent; //links to parent
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
	
	/**
	 * Allows us to descend down a given route of tree. Will recursively call until
	 * we have the final result.
	 *
	 * @param node The node being questioned
	 * @return result The chain of nodes
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
			result += node.key + " ";
			if (node.right != null) {
				result += AscendingOrderHelper(node.right);
			}
			return result;
		}
	}

	/**
	 * Returns true if our tree is empty (root is null)
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the height of our tree by calling the heightHelper method.
	 */
	public int height() {
		return heightHelper(root); 
	}
	
	/**
	 * Returns the height of our subtree. If the node is not there, we subtract 1
	 * with respect to how we count the height. Otherwise, it'll recursively call to 
	 * find the max point of the subtree height.
	 *
	 * @param node Where we are starting the count for height
	 * @return The height of our subtree 
	 */
	private int heightHelper(Treenode<T> node) {
		if (node == null) {
			return -1;
		}
		else {
			return 1 + Math.max(heightHelper(node.left), heightHelper(node.right));
		}
	}

	/**
	 * Returns true if the tree contains the node with the item in question.
	 * It will throught an IllegalArgumentException when the item sought to be 
	 * lookedup is not within our tree.
	 *
	 * @param item The item we are looking up.
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
	 * Given the node, are trying to determine if the value is within our tree.
	 * If it is, returns true. Continuously searches through tree by caluculating lower
	 * or higher values.
	 *
	 * @param node The node to begin off 
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
	 * of the insertHelper. Will give the node the default color red. Will through an 
	 * IllegalArgumentException anytime the item is not available (null)
	 *
	 * @param item The item looking to be inserted
	 */
	public void insert(T item) {
		if (item == null) {
			throw new IllegalArgumentException();
		}
		
		if (isEmpty()) {
			root = new Treenode<T>(item);
			root.color = false;
		}
		else {
			Treenode<T> node = insertHelper(item, root);
			balance(node);
			root.color = false;
		}
	}

	/**
	 * The insertHelper for the insert methiod,
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
	 * Method that will call the deleteHelper method. Allows us to delete said node. 
	 *
	 * @param item The item looking to be deleted. We make use of the deleteHelper method.
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
	 * Helper method for delete.
	 *
	 * @param item The node which is looking to be deleted.
	 * @param node The node in which our helper method is checking on
	 */
	private Treenode<T> deleteHelper(T item, Treenode<T> node) {
		if (item.compareTo(node.key) > 0) {
			node.right = deleteHelper(item, node.right);
		}
		else if (item.compareTo(node.key) < 0) {
			node.left = deleteHelper(item, node.left);
		}
		else {
			if (node.left == null || node.right == null) {
				if (node.left == null && node.right == null) {
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
	 * Balances the red-black after we are inserting. This does not check when we are deleting from the tree.
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
	 * Method which performs left-rotation. This is performed when the given tree is not balanced. 
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
	 * Method which performs right-rotation. This is performed when the given tree is not balanced. 
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
