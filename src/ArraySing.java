import java.util.ArrayList;
import java.util.Iterator;

public class ArraySing implements Iterable<Student> {

    private static ArraySing instance = null;

    private ArrayList<Student> arrayList;

    private ArraySing(){
        arrayList = new ArrayList<>();
    }

    public static ArraySing getInstance(){
        if(instance == null){
            instance = new ArraySing();
        }
        return instance;
    }

    public void addObj(Student student){
        arrayList.add(student);
    }

    public ArrayList<Student> getArrayList(){
        return arrayList;
    }


    @Override
    public Iterator<Student> iterator() {
        return arrayList.iterator();
    }

    public void clear(){
        arrayList.clear();
    }
}
