
import java.awt.*;
public class MaleStudent extends Student {
    public MaleStudent(int x, int y) {
        super(x, y);
    }

    @Override
    protected void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(x,y,50,50);
    }

    @Override
    public void move() {

    }

    @Override
    public String toString() {
        return "MaleStudent{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}