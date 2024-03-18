import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
public class Habitat{
    private int width;
    private int height;
    private final int UPDATE_INTERVAL = 1000;

    private int update_male = 1000;
    private int update_female= 1000;
    private int time;
    private Timer timer;
    private Timer timerMale;
    private Timer timerFemale;
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
    private JButton buttonStart;
    private JButton buttonStop;
    private boolean flagForInfo = false;
    private JRadioButton showTime;
    private JRadioButton hideTime;
    private double maleProbability = 0.6;
    private double femaleProbability = 0.4;

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
        keyBoardClick();
        frame.setVisible(true);
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
        buttonStart = new JButton("Start");
        buttonStop = new JButton("Stop");
        JCheckBox checkInfo = new JCheckBox("Show info");
        showTime = new JRadioButton("Showing time");
        hideTime = new JRadioButton("Hiding time");
        ButtonGroup groupRadio = new ButtonGroup();
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
        for (int i = 10; i <= 100; i+=10) {
            firstVib.addItem(i);
            secondVib.addItem(i);
        }
        firstVib.setSelectedIndex(5);
        secondVib.setSelectedIndex(3);
        groupRadio.add(showTime);
        groupRadio.add(hideTime);

        showTime.setSelected(true);


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

        checkInfo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    flagForInfo = true;
                }else{
                    flagForInfo = false;
                }
            }
        });

        showTime.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    timeLabel = true;
                    label.setVisible(timeLabel);
                }
            }
        });

        hideTime.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    timeLabel = false;
                    label.setVisible(timeLabel);
                }
            }
        });

        firstVib.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = (int) firstVib.getSelectedItem();
                maleProbability = selected/100.0;
            }
        });
        secondVib.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selected = (int) secondVib.getSelectedItem();
                femaleProbability = selected/100.0;
            }
        });

        inputTextFirst.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newValue = Integer.parseInt(inputTextFirst.getText());
                    if (newValue >= 0) {
                        timerMale.setDelay(newValue * 1000);
                        panel.requestFocusInWindow();
                        panel.setFocusable(true);
                        frame.setFocusable(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Введите положительное значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Введите допустимое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        inputTextSecond.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int newValue = Integer.parseInt(inputTextSecond.getText());
                    if (newValue >= 0) {
                        timerFemale.setDelay(newValue * 1000);
                        panel.requestFocusInWindow();
                        panel.setFocusable(true);
                        frame.setFocusable(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Введите положительное значение", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Введите допустимое число", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                time+=UPDATE_INTERVAL;
                label.setText(String.format("Time %d seconds",time/1000));
                System.out.println(time/1000 + " seconds");
            }
        });
        timerMale = new Timer(update_male, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateMale();
                panel.repaint();
            }
        });
        timerFemale = new Timer(update_female, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFemale();
                panel.repaint();
            }
        });
        timer.start();
        timerMale.start();
        timerFemale.start();
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
        timerMale.stop();
        timerFemale.stop();
        countStudentsText.setText("Количество студентов = " + cntMale);
        countFemaleStudents.setText("Количество студенток = " + cntFemale);
        countLabelMale = true;
        countLabelFemale = true;
        countStudentsText.setVisible(countLabelMale);
        countFemaleStudents.setVisible(countLabelFemale);
        if(flagForInfo){
            JOptionPane.showMessageDialog(frame, "Количество студентов = " + cntMale + "\n" + "Количество студенток = " + cntFemale + "\n" + "Время выполнения: " + time/1000 + " секунд", "Информация", JOptionPane.INFORMATION_MESSAGE);
        }
        time = 0;

    }
    private void updateMale(){
        if (Math.random() < maleProbability) {
            students.addObj(new MaleStudent(random.nextInt(width), random.nextInt(height)));
        }
    }
    private void updateFemale(){
        if (Math.random() < femaleProbability) {
            students.addObj(new FemaleStudent(random.nextInt(width), random.nextInt(height)));
        }
    }
    
    private void keyBoardClick(){
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (!simulationRunning) {
                        if (e.getKeyCode() == KeyEvent.VK_B) {
                            startSimulation();
                            buttonStart.setBackground(Color.GRAY);
                            buttonStop.setBackground(Color.BLACK);
                            simulationRunning = true;
                        }
                    } else {
                        if (e.getKeyCode() == KeyEvent.VK_E) {
                            stopSimulation();
                            buttonStart.setBackground(Color.BLACK);
                            buttonStop.setBackground(Color.GRAY);
                            simulationRunning = false;
                        }
                    }
                    if (e.getKeyCode() == KeyEvent.VK_T) {
                        timeLabel = !timeLabel;
                        label.setVisible(timeLabel);
                        if (showTime.isSelected()) {
                            hideTime.setSelected(true);
                        } else if (hideTime.isSelected()) {
                            showTime.setSelected(true);
                        }
                    }
                }
                return false;
            }
        });
    }


}
