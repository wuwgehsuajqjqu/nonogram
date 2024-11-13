package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {
    static int rowSize;
    static int colSize;
    AlternateButton[][] cellPanel;
    int totalIscells = 0; 
    int clickedIscells = 0;
    JFrame mainFrame; 
    String userId; 
    long startTime;

   
    public Board(JFrame mainFrame, String userId, long startTime) {
        this.mainFrame = mainFrame;
        this.userId = userId;
        this.startTime = startTime;
    }

    void init(String[] img) {
        rowSize = img.length;
        colSize = img.length;
        cellPanel = new AlternateButton[rowSize][colSize];

        setLayout(new GridLayout(rowSize, colSize));

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                cellPanel[i][j] = new AlternateButton(img[i].charAt(j));
                cellPanel[i][j].setBorder(BorderFactory.createBevelBorder(0));
                add(cellPanel[i][j]);

                if (cellPanel[i][j].isCell) {
                    totalIscells++; 
                }
            }
        }
    }

    private class AlternateButton extends JButton {
        boolean isCell;
        boolean isClicked = false;

        AlternateButton(char isCell) {
            this.isCell = (isCell == '1');
            setBackground(Color.DARK_GRAY);

           
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleClick();
                }
            });
        }

        private void handleClick() { //클릭 다룰떄
            if (isCell) {
                if (isClicked) {
                    setBackground(Color.darkGray); 
                    clickedIscells--; 
                } else {
                    setBackground(Color.lightGray); 
                    clickedIscells++; 
                    checkClearCondition(); 
                }
                isClicked = !isClicked; 
            } else {
             
                setBackground(isClicked ? Color.darkGray : Color.lightGray);
                isClicked = !isClicked;
            }
        }

      
        private void checkClearCondition() { 
            if (clickedIscells == totalIscells && allNonIscellsUnclicked()) {
                long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; 
                JOptionPane.showMessageDialog(this, "클리어!\nID: " + userId + "\nTime: " + elapsedTime + " seconds");
                
                mainFrame.dispose(); // 현재 프레임 닫기
                Start.main(new String[]{}); // 초기 화면으로 이동
            }
        }

        
        private boolean allNonIscellsUnclicked() {
            for (int i = 0; i < rowSize; i++) {
                for (int j = 0; j < colSize; j++) {
                    if (!cellPanel[i][j].isCell && cellPanel[i][j].isClicked) {
                        return false; // isCell이 아닌 셀 클릭 경우 false 반환
                    }
                }
            }
            return true; //iscell 아닌 셀 클릭 안된 경우
        }
    }
}
