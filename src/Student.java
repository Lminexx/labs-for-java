import java.awt.*;

public abstract class Student implements IBehaviour {
    protected int x;
    protected int y;
    protected double timeToBorn;
    protected double timeToDie;



    public Student(int x, int y, int timeToBorn, int timeToDie) {
        this.x = x;
        this.y = y;
        this.timeToBorn = timeToBorn;
        this.timeToDie = timeToDie;
    }

    protected abstract void draw(Graphics g);

    @Override
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return x == student.x && y == student.y && timeToBorn == student.timeToBorn && timeToDie == student.timeToDie;
    }
}


