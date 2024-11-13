package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static JLabel timerLabel;
    private int secondsElapsed = 0;
    private String userId;
    private String filename;
    private long startTime;
    private Timer timer;  

    public MainFrame(String userId, String filename) {
        this.userId = userId;
        this.filename = filename;
    }

    public void setupMainPanel() {
        JPanel top = new JPanel(new GridLayout(3, 1));
        setTitle("Demo");
        Container pane = getContentPane();
        top.setPreferredSize(new Dimension(200, 1));
        JLabel topLabel = new JLabel("Title");
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setFont(topLabel.getFont().deriveFont(24.0f));
        timerLabel = new JLabel("걸린 시간 : 0 seconds");
        timerLabel.setFont(timerLabel.getFont().deriveFont(24.0f));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(timerLabel, BorderLayout.CENTER);
        top.add(topLabel, BorderLayout.CENTER);
        pane.add(top, BorderLayout.LINE_START);

        String[] puzzleArray = loadPuzzle(filename);
        startTime = System.currentTimeMillis();

        FullBoard fullBoard = new FullBoard(this); 
        fullBoard.init(puzzleArray, this, userId, startTime);

        JPanel north = new JPanel();
        north.setPreferredSize(new Dimension(WIDTH, 50));
        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(50, HEIGHT));
        JPanel south = new JPanel();
        south.setPreferredSize(new Dimension(WIDTH, 50));

        pane.add(north, BorderLayout.PAGE_START);
        pane.add(fullBoard, BorderLayout.CENTER);
        pane.add(right, BorderLayout.LINE_END);
        pane.add(south, BorderLayout.PAGE_END);

        startTimer();
    }

    private String[] loadPuzzle(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try (Scanner filein = openFile(filename)) {
            while (filein.hasNextLine()) {
                lines.add(filein.nextLine());
            }
        }
        return lines.toArray(new String[0]);
    }

    private Scanner openFile(String filename) {
        Scanner filein = null;
        try {
            filein = new Scanner(new File(filename));
        } catch (Exception e) {
            System.out.printf("파일 오픈 실패: %s\n", filename);
            throw new RuntimeException(e);
        }
        return filein;
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {  // 타이머를 클래스 변수로 설정
            secondsElapsed++;
            if (secondsElapsed > 60)
                timerLabel.setText("걸린 시간: " + secondsElapsed / 60 + "분 " + secondsElapsed % 60 + "초");
            else
                timerLabel.setText("걸린 시간 : " + secondsElapsed + "초");
        });
        timer.start();
    }

    private void saveRanking(String filename, String userId, int timeInSeconds) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
            out.println(userId + "," + timeInSeconds);
            System.out.println("랭킹 저장: " + filename + " - " + userId + " - " + timeInSeconds + "초");
        } catch (IOException e) {
            System.out.println("랭킹 저장 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void endGame() {
        if (timer != null) {
            timer.stop();  
        }
        int timeInSeconds = secondsElapsed;
        String rankingFile = getRankingFileForDifficulty();
        System.out.println("게임 종료. 랭킹 파일에 저장을 시도합니다.");
        saveRanking(rankingFile, userId, timeInSeconds);
        JOptionPane.showMessageDialog(this, "게임이 종료되었습니다. 기록이 저장되었습니다.");
        this.revalidate();
        this.repaint();
        dispose();
        new Start().createAndShowGUI();
    }

    private String getRankingFileForDifficulty() {
        if (filename.equals("easy.txt"))
            return "easy_ranking.txt";
        if (filename.equals("normal.txt"))
            return "normal_ranking.txt";
        return "hard_ranking.txt";
    }

    void createAndShowGUI() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setupMainPanel();
        setPreferredSize(new Dimension(800, 700));
        pack();
        setVisible(true);
    }
}
