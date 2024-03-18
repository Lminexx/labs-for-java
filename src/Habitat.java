import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
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
    private ArraySing students;
    private Random random;
    private boolean simulationRunning =false;

    public Habitat(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.students = ArraySing.getInstance();
        initialize();
    }
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("laba first");
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
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
        frame.add(panel, BorderLayout.CENTER);
        panel.requestFocusInWindow();
        controlPanel();
        frame.setVisible(true);
        frame.addKeyListener(this);

    }

    public void controlPanel(){
        JPanel controlpanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0,0, getWidth(), getHeight());
            }
        };
        controlpanel.setPreferredSize(new Dimension(200,frame.getHeight()));
        controlpanel.setLayout(new FlowLayout());
        //Создание объектов для контрольной панельки.
        JButton buttonStart = new JButton("Start");
        JButton buttonStop = new JButton("Stop");
        JCheckBox checkInfo = new JCheckBox("Show info");
        JRadioButton showTime = new JRadioButton("Showing time");
        JRadioButton hideTime = new JRadioButton("Hiding time");
        JLabel textForFirst = new JLabel("Шанс появления первого:");
        JComboBox<Integer> firstVib = new JComboBox<>();
        JLabel periodForFirst = new JLabel("Периодичность первого(сек):");
        JTextField inputTextFirst = new JFormattedTextField("1");
        JLabel textForSecond = new JLabel("Шанс появления второго:");
        JComboBox<Integer> secondVib = new JComboBox<>();
        JLabel periodForSecond = new JLabel("Периодичность второго(сек):");
        JTextField inputTextSecond = new JFormattedTextField("1");
        JLabel textB = new JLabel("B - start simulation");
        JLabel textE = new JLabel("E - stop simulation");
        JLabel textT = new JLabel("T - time on/off");

        //В выборку добавляю значения шанса
        for (int i = 1; i <= 100; i++) {
            firstVib.addItem(i);
            secondVib.addItem(i);
        }


        //Установил пустой цвет клика. Так симпатичнее имхо.
        buttonStart.setUI(new CustomButtonUI());
        buttonStop.setUI(new CustomButtonUI());
        //Нажатие кнопок, что делают и т.д.
        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!simulationRunning){
                    startSimulation();
                    buttonStart.setBackground(Color.GRAY);
                    buttonStop.setBackground(Color.BLACK);
                    simulationRunning = !simulationRunning;
                }
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(simulationRunning){
                    stopSimulation();
                    buttonStart.setBackground(Color.BLACK);
                    buttonStop.setBackground(Color.GRAY);
                    simulationRunning = !simulationRunning;
                }


            }
        });

        //Убрал фокус с кнопок, а то не работали нажатия с клавы из-за этого.
        buttonStart.setFocusable(false);
        buttonStop.setFocusable(false);
        checkInfo.setFocusable(false);
        showTime.setFocusable(false);
        hideTime.setFocusable(false);
        firstVib.setFocusable(false);
        inputTextFirst.setFocusable(false);
        secondVib.setFocusable(false);
        //Установка размеров
        buttonStart.setPreferredSize(new Dimension(200,50));
        buttonStop.setPreferredSize(new Dimension(200,50));
        checkInfo.setPreferredSize(new Dimension(130,20));
        showTime.setPreferredSize(new Dimension(130,20));
        hideTime.setPreferredSize(new Dimension(130,20));
        firstVib.setPreferredSize(new Dimension(190,40));
        inputTextFirst.setPreferredSize(new Dimension(190,40));
        secondVib.setPreferredSize(new Dimension(190,40));
        inputTextSecond.setPreferredSize(new Dimension(190,40));
        //Установил задний фон и цвет текста. Получилось красиво :).
        buttonStart.setBackground(Color.BLACK);
        buttonStop.setBackground(Color.BLACK);
        buttonStart.setForeground(Color.CYAN);
        buttonStop.setForeground(Color.CYAN);
        textForFirst.setFont(new Font("Arial", Font.PLAIN, 17));
        periodForFirst.setFont(new Font("Arial",Font.PLAIN,15));
        inputTextFirst.setFont(new Font("Arial", Font.BOLD,15));
        textForSecond.setFont(new Font("Arial",Font.PLAIN,17));
        periodForSecond.setFont(new Font("Arial", Font.PLAIN,15));
        inputTextSecond.setFont(new Font("Arial", Font.BOLD,15));
        textB.setFont(new Font("Arial",Font.BOLD, 14));
        textE.setFont(new Font("Arial",Font.BOLD, 14));
        textT.setFont(new Font("Arial",Font.BOLD, 14));


        controlpanel.add(buttonStart);
        controlpanel.add(buttonStop);
        controlpanel.add(checkInfo);
        controlpanel.add(showTime);
        controlpanel.add(hideTime);
        controlpanel.add(textForFirst);
        controlpanel.add(firstVib);
        controlpanel.add(periodForFirst);
        controlpanel.add(inputTextFirst);
        controlpanel.add(textForSecond);
        controlpanel.add(secondVib);
        controlpanel.add(periodForSecond);
        controlpanel.add(inputTextSecond);
        controlpanel.add(textB);
        controlpanel.add(textE);
        controlpanel.add(textT);
        frame.add(controlpanel, BorderLayout.EAST);
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
            students.addObj(new MaleStudent(random.nextInt(width), random.nextInt(height)));
        }
        if (Math.random() < femaleProbability) {
            students.addObj(new FemaleStudent(random.nextInt(width), random.nextInt(height)));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_B){
            System.out.println("нажал");
            startSimulation();
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            System.out.println("нажал");
            stopSimulation();
        }
        if(e.getKeyCode() == KeyEvent.VK_T){
            System.out.println("нажал");
            timeLabel = !timeLabel;
            label.setVisible(timeLabel);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
