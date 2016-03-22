package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
		size = 0;
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should ignore the word's case.
	 * That is, you should convert the string to all lower case as you insert it. */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		String newWord = word.toLowerCase();
		TrieNode temp = root;
		for(int i = 0; i < newWord.length(); i++){
			char c = newWord.charAt(i);
			if(temp.getValidNextCharacters().contains(c)){
				temp = temp.getChild(c);
			}
			else{
				temp = temp.insert(c);
			}
		}
		if (!temp.endsWord()) {
			temp.setEndsWord(true);
			size++;
			return true;
		}
	    return false;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		String newWord = s.toLowerCase();
		TrieNode temp = root;
		for(int i = 0; i < newWord.length(); i++){
			char c = newWord.charAt(i);
			if(temp.getValidNextCharacters().contains(c)){
				temp = temp.getChild(c);
			}
			else{
				return false;
			}
		}
		if(temp.endsWord()){
			return true;
		}
		return false;
	}

	/** 
	 *  * Returns up to the n "best" predictions, including the word itself,
     * in terms of length
     * If this string is not in the trie, it returns null.
     * @param text The text to use at the word stem
     * @param n The maximum number of predictions desired.
     * @return A list containing the up to n best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> newList = new LinkedList<String>();
    	 String newWord = prefix.toLowerCase();
    	 TrieNode temp = root;
    	 for(int i = 0; i < prefix.length(); i++){
    		 char c = newWord.charAt(i);
    		 if(temp.getValidNextCharacters().contains(c)){
    			 temp = temp.getChild(c);
    		 }
    		 else{
    			 return newList;
    		 }
    	 }
    	 int count = 0;
    	 if(temp.endsWord()){
    		 newList.add(temp.getText());
    		 count++;
    	 }
    	 //BFS
    	 List<TrieNode> nodeQueue = new LinkedList<TrieNode>();
    	 List<Character> childrenC = new LinkedList<Character>(temp.getValidNextCharacters());
    	 for(int i = 0; i < childrenC.size(); i++){
    		 char c = childrenC.get(i);
    		 nodeQueue.add(temp.getChild(c));
    	 }
    	 while(!nodeQueue.isEmpty() && count < numCompletions){
    		 TrieNode tn = nodeQueue.remove(0);
    		 if(tn.endsWord()){
    			 newList.add(tn.getText());
    			 count++;
    		 }
    		 
    		 List<Character> cs = new LinkedList<Character>(tn.getValidNextCharacters());
        	 for (int i = 0; i < cs.size(); i++) {
        		 char c = cs.get(i);
        		 nodeQueue.add(tn.getChild(c));
        	 }
    	 }
    	 return newList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}