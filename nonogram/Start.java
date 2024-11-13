package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        if (userId == null || userId.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "아이디가 입력되지 않았습니다");
            userId = "익명";
        }

        showDifficultyOptions();

        JPanel topPanel = new JPanel(new BorderLayout());
        JButton rankButton = new JButton("랭킹");
        JButton endButton = new JButton("게임 종료");

        rankButton.addActionListener(e -> showRanking());
        endButton.addActionListener(e -> System.exit(0));

        topPanel.add(rankButton, BorderLayout.WEST);
        topPanel.add(endButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private void showDifficultyOptions() {
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

        frame.add(difficultyPanel, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private void startGame(String filename) {
        frame.setVisible(false);
        MainFrame gameFrame = new MainFrame(userId, filename);
        gameFrame.createAndShowGUI();
    }

    private void showRanking() {
        JFrame rankingFrame = new JFrame("랭킹");
        rankingFrame.setSize(400, 400);
        rankingFrame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        JButton easyButton = new JButton("쉬움");
        JButton normalButton = new JButton("보통");
        JButton hardButton = new JButton("어려움");

        JTextArea rankingArea = new JTextArea(15, 30);
        rankingArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(rankingArea);

        easyButton.addActionListener(e -> loadRanking("easy_ranking.txt", rankingArea));
        normalButton.addActionListener(e -> loadRanking("normal_ranking.txt", rankingArea));
        hardButton.addActionListener(e -> loadRanking("hard_ranking.txt", rankingArea));

        topPanel.add(easyButton);
        topPanel.add(normalButton);
        topPanel.add(hardButton);

        rankingFrame.add(topPanel, BorderLayout.NORTH);
        rankingFrame.add(scrollPane, BorderLayout.CENTER);
        rankingFrame.revalidate();
        rankingFrame.repaint();
        rankingFrame.setVisible(true);
    }

    private void loadRanking(String filename, JTextArea rankingArea) {
        List<String> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                records.add(scanner.nextLine());
            }
        } catch (Exception e) {
            rankingArea.setText("랭킹 파일을 찾을 수 없습니다: " + filename);
            return;
        }

        // 걸린 시간 순으로 정렬
        records.sort(Comparator.comparingInt(record -> Integer.parseInt(record.split(",")[1])));

        rankingArea.setText("랭킹:\n");
        rankingArea.append("-----------------\n");
        int rank = 1;
        for (String record : records) {
            String[] data = record.split(",");
            rankingArea.append(rank + "등: " + data[0] + " - " + data[1] + "초\n");
            rank++;
        }
    }
}
