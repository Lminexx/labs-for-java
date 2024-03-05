import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
public class Habitat implements KeyListener {
    private int width;
    private int height;
    private final int UPDATE_INTERVAL = 1000;
    private int time;
    private Timer timer;
    private JFrame frame;
    private JPanel panel;
    private JLabel label;
    private JLabel countStudentsText;
    private JLabel countFemaleStudents;
    private boolean timeLabel = true;
    private boolean countLabelMale = false;
    private boolean countLabelFemale = false;
    private ArrayList<Student> students;
    private Random random;

    public Habitat(int width, int height) {
        this.width = width;
        this.height = height;
        random = new Random();
        students = new ArrayList<>();
        initialize();
    }
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("laba first");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Student student : students) {
                    student.draw(g);
                }
            }
        };
        label = new JLabel();
        label.setSize(100,100);
        label.setForeground(Color.green);
        countStudentsText = new JLabel();
        countStudentsText.setSize(150,150);
        countStudentsText.setForeground(Color.ORANGE);
        countFemaleStudents = new JLabel();
        countFemaleStudents.setSize(150,150);
        countFemaleStudents.setForeground(Color.red);
        panel.add(label);
        panel.add(countStudentsText);
        panel.add(countFemaleStudents);
        frame.add(panel);
        frame.setVisible(true);
        frame.addKeyListener(this);
    }
    public void startSimulation() {
        students.clear();
        timer = new Timer(UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                panel.repaint();
                time+=UPDATE_INTERVAL;
                label.setText(String.format("Time %d seconds",time/1000));
                System.out.println(time/1000 + " seconds");
            }
        });
        timer.start();
        countLabelMale = false;
        countLabelFemale = false;
        countStudentsText.setVisible(countLabelMale);
        countFemaleStudents.setVisible(countLabelFemale);
    }
    public void stopSimulation() {
        int cntMale = 0;
        int cntFemale = 0;
        for (Student student : students){
            if(student instanceof MaleStudent){
                cntMale++;
            }
            if(student instanceof FemaleStudent){
                cntFemale++;
            }
        }
        frame.repaint();
        timer.stop();
        countStudentsText.setText("Количество студентов = " + cntMale);
        countFemaleStudents.setText("Количество студенток = " + cntFemale);
        time = 0;
        countLabelMale = true;
        countLabelFemale = true;
        countStudentsText.setVisible(countLabelMale);
        countFemaleStudents.setVisible(countLabelFemale);
    }
    private void update() {
        double maleProbability = 0.6;
        double femaleProbability = 0.4;
        if (Math.random() < maleProbability) {
            students.add(new MaleStudent(random.nextInt(width), random.nextInt(height)));
        }
        if (Math.random() < femaleProbability) {
            students.add(new FemaleStudent(random.nextInt(width), random.nextInt(height)));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_B){
            startSimulation();
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            stopSimulation();
        }
        if(e.getKeyCode() == KeyEvent.VK_T){
            timeLabel = !timeLabel;
            label.setVisible(timeLabel);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
