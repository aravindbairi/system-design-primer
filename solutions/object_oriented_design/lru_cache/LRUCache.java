
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {
    private Node head;
    private Node tail;
    private Map<String,Node> cacheMap;
    private int size=0;
    private int maxSize;

    public int getSize() {
        return size;
    }

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        LinkedList l = new LinkedList();
        cacheMap = new HashMap<>();
    }

    public String getCachedValue(String key){
        if (!cacheMap.containsKey(key)){
            throw new RuntimeException("Key Not found"+ key);
        }
        Node cachedNode = cacheMap.get(key);
        moveNodeToLast(cachedNode);
        return cachedNode.value;
    }

    private void moveNodeToLast(Node cachedNode) {
        Node nextNode = cachedNode.nextNode;
        Node prevNode = cachedNode.prevNode;
        if(cachedNode==tail){
            return;
        } else if(cachedNode==head) {
            head = cachedNode.nextNode;
        } else{
            prevNode.nextNode = nextNode;
        }
        nextNode.prevNode = prevNode;
        tail.nextNode = cachedNode;
        cachedNode.prevNode = tail;
        cachedNode.nextNode = null;
        tail = cachedNode;
    }

    public void insertCache(String key, String value){
        Node cachedNode = new Node(value);
        cacheMap.put(key,cachedNode);
        insertNode(cachedNode);
        if (size>maxSize){
            deleteLastUsed();
        }
    }
    private void insertNode(Node node){
        if (head==null) {
            head = node;
            tail = node;
        }
        tail.nextNode = node;
        node.prevNode = tail;
        tail = node;
        size++;
    }
    private void deleteLastUsed(){
        head = head.nextNode;
        head.prevNode = null;
        size--;
    }


    private class Node {
        String value;
        Node nextNode;
        Node prevNode;

        public Node(String value) {
            this.value = value;
        }
    }
}
