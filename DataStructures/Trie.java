package DataStructures;

import java.util.HashMap;

public class Trie {

    static class TrieNode {
        // can be used array ** Map is because u can use any character not just small-case **
        HashMap<Character, TrieNode> child;
        boolean end;

        public TrieNode() {
            end = false;
            child = new HashMap<>();
        }
    }

    static TrieNode root;

    static void insert(String s/*char c[]*/) {
        char c[] = s.toCharArray();
        TrieNode curr = root;

        for(int i=0;i<c.length;i++) {
            TrieNode node = curr.child.get(c[i]);

            if(node == null) {
                node = new TrieNode();
                curr.child.put(c[i], node);
            }

            curr = node;
        }

        curr.end = true;        // End of word
    }

    static boolean search(String s /*char c[]*/) {
        char c[] = s.toCharArray();
        TrieNode curr = root;

        for(int i=0;i<c.length;i++) {
            TrieNode node = curr.child.get(c[i]);

            if(node == null) return false;

            curr = node;
        }

        return curr.end;        // If end of word
    }

    // pass "root" as curr initially
    static boolean delete(TrieNode curr, String s /*char c[]*/, int ind) {      // type is boolean just for logic ** It will delete word if possible **
        if(ind == s.length()) {     // End of word                              // return of this method is of "NO" use..!
            if(!curr.end) return false; // If not end

            curr.end = false;               // word is no more
            return curr.child.isEmpty();    // return if there was just 1 element in this node so to know if delete this node or not..!
        }

        TrieNode node = curr.child.get(s.charAt(ind));
        if(node == null) {
            return false;
        }

        boolean ok = delete(node, s, ind + 1);

        if(ok) {
            curr.child.remove(s.charAt(ind));
            return curr.child.isEmpty();        // return if there was just 1 element in this node so to know if delete this node or not..!
        }

        return false;
    }

    public static void main(String args[]) {

        String keys[] = {"the", "a", "there", "answer", "any", "by", "bye", "their"};
        root = new TrieNode();

        // Construct trie
        for (int i = 0; i < keys.length ; i++) {
            insert(keys[i]);
        }

        // Search for different keys
        if(search("the")) {
            System.out.println("Present in Trie");
        }
        else {
            System.out.println("Not present in Trie");
        }

        if(search("they")) {
            System.out.println("Present in Trie");
        }
        else {
            System.out.println("Not present in Trie");
        }

        // delete and then search..
        delete(root, "the", 0);

        if(search("the")) {
            System.out.println("Present in Trie");
        }
        else {
            System.out.println("Not present in Trie");
        }
    }
}
