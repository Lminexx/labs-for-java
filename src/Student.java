import java.awt.*;
public abstract class Student implements IBehaviour {
    protected int x;
    protected int y;

    public Student(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected abstract void draw(Graphics g);
}

