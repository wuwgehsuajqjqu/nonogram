package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Start {
    private JFrame frame;
    private String userId;

    public static void main(String[] args) {
        new Start().createAndShowGUI();
    }

    public void createAndShowGUI() {
        frame = new JFrame("노노그램 게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        userId = JOptionPane.showInputDialog(frame, "아이디를 입력하세요:", "ID 입력", JOptionPane.PLAIN_MESSAGE);
        if (userId == null) {
            JOptionPane.showMessageDialog(frame, "아이디가 입력되지 않았습니다");
            userId = "익명"; // 입력 없으면 익명으로
        }

        //난이도 선택창
        showDifficultyOptions();

        frame.setVisible(true);
    }

    public void showDifficultyOptions() {
        frame.getContentPane().removeAll();

        JPanel difficultyPanel = new JPanel(new FlowLayout());
        JButton easyButton = new JButton("쉬움");
        JButton normalButton = new JButton("보통");
        JButton hardButton = new JButton("어려움");

        easyButton.addActionListener(e -> startGame("easy.txt"));
        normalButton.addActionListener(e -> startGame("normal.txt"));
        hardButton.addActionListener(e -> startGame("hard.txt"));

        difficultyPanel.add(easyButton);
        difficultyPanel.add(normalButton);
        difficultyPanel.add(hardButton);

        frame.add(difficultyPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    private void startGame(String filename) {
        frame.setVisible(false);
        MainFrame gameFrame = new MainFrame(userId, filename); // 파일 이름전달
        gameFrame.createAndShowGUI();
    }
}
