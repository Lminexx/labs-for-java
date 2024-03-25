import java.awt.*;
public class MaleStudent extends Student {


    public MaleStudent(int x, int y, int timeToBorn, int timeToDie) {
        super(x, y, timeToBorn, timeToDie);
    }
    @Override
    protected void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(x,y,50,50);
    }
    @Override
    public void move() {

    }
}