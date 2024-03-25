import java.util.*;

public class ArraySing implements Iterable<Student> {

    private static ArraySing instance = null;

    private LinkedList<Student> linkedList;
    private HashSet<Integer> indexHash;
    private TreeMap<Double, Student> timeToBornTree;

    private ArraySing(){
        linkedList = new LinkedList<>();
        indexHash = new HashSet<>();
        timeToBornTree = new TreeMap<>();
    }

    public static ArraySing getInstance(){
        if(instance == null){
            instance = new ArraySing();
        }
        return instance;
    }

    public void addObj(Student student){
        linkedList.add(student);
        indexHash.add(student.hashCode());
        timeToBornTree.put(student.timeToBorn, student);
    }

    public LinkedList<Student> getLinkedList(){
        return linkedList;
    }

    public HashSet<Integer> getIndexHash() {
        return indexHash;
    }

    public TreeMap<Double, Student> getTimeToBornTree() {
        return timeToBornTree;
    }

    @Override
    public Iterator<Student> iterator() {
        return linkedList.iterator();
    }

    public void clear(){
        linkedList.clear();
        indexHash.clear();
        timeToBornTree.clear();
    }
    public void clearObject(Student student){
        linkedList.remove(student);
        indexHash.remove(student.hashCode());
        timeToBornTree.remove(student.timeToBorn,student);
    }
}
