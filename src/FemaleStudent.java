
import java.awt.*;
public class FemaleStudent extends Student {
    public FemaleStudent(int x, int y) {
        super(x, y);
    }

    @Override
    protected void draw(Graphics g) {
        g.setColor(Color.pink);
        g.fillOval(x,y,50,50);
    }

    @Override
    public void move() {

    }

    @Override
    public String toString() {
        return "FemaleStudent{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}