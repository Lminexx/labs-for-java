import java.awt.*;
public class FemaleStudent extends Student {


    public FemaleStudent(int x, int y, int timeToBorn, int timeToDie) {
        super(x, y, timeToBorn, timeToDie);
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
    public int hashCode() {
        return super.hashCode();
    }
    @Override
    public String toString() {
        return "FemaleStudent{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}