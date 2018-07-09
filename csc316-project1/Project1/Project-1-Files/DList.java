/**
 * @author Goodrich &amp; Tamassia
 */
public class DList<E>
{
	/** maximum number of list items to print */
	protected int size;
	protected DNode<E> header, trailer;
	
	public DList()
	{
		size = 0;
		header = new DNode<E>(null, null, null);
		trailer = new DNode<E>(null, header, null);
		header.setNext(trailer);
	}
	
	public int size()
	{
		return size;
	}
	
	public boolean isEmpty()
	{
		return (size == 0);
	}
	
	public DNode<E> getFirst() throws IllegalStateException
	{
		if (isEmpty())
			throw new IllegalStateException("List is empty");
		return header.getNext();
	}
	
	public DNode<E> getLast() throws IllegalStateException
	{
		if (isEmpty())
			throw new IllegalStateException("List is empty");
		return trailer.getPrev();
	}
	
	public DNode<E> getPrev(DNode<E> v) throws IllegalArgumentException
	{
		if (v == header)
			throw new IllegalArgumentException("Cannot move back past the header of the list");
		return v.getPrev();
	}
	
	public DNode<E> getNext(DNode<E> v) throws IllegalArgumentException
	{
		if (v == trailer)
			throw new IllegalArgumentException("Cannot move forward past the trailer of the list");
		return v.getNext();
	}
	
	/**
	 * Inserts the given node newNode before the given node v.
	 * An error occurs if v is the header
	 */
	public void addBefore(DNode<E> oldNode, DNode<E> newNode)
			throws IllegalArgumentException
	{
		DNode<E> leftNode = getPrev(oldNode); // may throw an IllegalArgumentException
		newNode.setPrev(leftNode);
		newNode.setNext(oldNode);
		oldNode.setPrev(newNode);
		leftNode.setNext(newNode);
		size++;
		
		// Modification by Bennett below...
		// If we call addLast on an empty list, we will subsequently call addBefore(trailer, newNode)
		// if (oldNode == trailer)
		// {
		// // oldNode.setNext(null);
		// oldNode = null;
		// trailer = newNode;
		// }
	}
	
	/**
	 * Inserts the given node newNode after the given node oldNode.
	 * An error occurs if v is the trailer
	 */
	public void addAfter(DNode<E> oldNode, DNode<E> newNode)
	{
		DNode<E> rightNode = getNext(oldNode); // may throw an IllegalArgumentException
		newNode.setPrev(oldNode);
		newNode.setNext(rightNode);
		rightNode.setPrev(newNode);
		oldNode.setNext(newNode);
		size++;
		
		// Modification by Bennett below...
		// If we call addFirst on an empty list, we will subsequently call addAfter(header, newNode)
		// if (oldNode == header)
		// {
		// // oldNode.setPrev(null);
		// oldNode = null;
		// header = newNode;
		// }
	}
	
	public void addFirst(DNode<E> newNode)
	{
		addAfter(header, newNode);
	}
	
	public void addLast(DNode<E> newNode)
	{
		addBefore(trailer, newNode);
	}
	
	public void remove(DNode<E> nodeToRemove)
	{
		DNode<E> u = getPrev(nodeToRemove); // may throw an IllegalArgumentException
		DNode<E> w = getNext(nodeToRemove); // may throw an IllegalArgumentException
		w.setPrev(u);
		u.setNext(w);
		nodeToRemove.setPrev(null);
		nodeToRemove.setNext(null);
		size--;
	}
	
	public boolean hasPrev(DNode<E> node)
	{
		return node != header;
		
		// Modification by Bennett below... (since this implementation of DList is not updating header/trailer on insertions)
		// if (node == header || node.getPrev() == header)
		// return false;
		// else
		// return true;
	}
	
	public boolean hasNext(DNode<E> node)
	{
		return node != trailer;
		
		// Modification by Bennett below... (since this implementation of DList is not updating header/trailer on insertions)
		// if (node == trailer || node.getNext() == trailer)
		// return false;
		// else
		// return true;
	}
	
	/**
	 * @return a string representation of the list for debugging and seeing
	 *         how the heuristic works
	 */
	public String toString()
	{
		String s = "";
		DNode<E> v = header.getNext();
		// int count = 0;
		while (v != trailer)
		{
			s += v.getEntry().toString() + "\n";
			v = v.getNext();
		}
		return s;
	}
	
	public static void main(String[] args)
	{
		DList<WordWithCount> dl = new DList<WordWithCount>();
		dl.addLast(new DNode<WordWithCount>(new WordWithCount("apple", 3),
				null, null));
		dl.addLast(new DNode<WordWithCount>(new WordWithCount("ball", 2),
				null, null));
		dl.addLast(new DNode<WordWithCount>(new WordWithCount("cat", 1),
				null, null));
		System.out.println(dl);
	}
}

// [Last modified: 2010 08 25 at 13:18:46 GMT]
