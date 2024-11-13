package main;

import javax.swing.*;
import java.awt.*;

public class FullBoard extends JPanel {
    GridBagConstraints gbc = new GridBagConstraints();
    GridBagLayout grid = new GridBagLayout();

    static int DIFFICULTY_WEIGHT;
    static double WEIGHT; 

    void init(String[] img, JFrame mainFrame, String userId, long startTime) {
        DIFFICULTY_WEIGHT = img.length;
        WEIGHT = (double) DIFFICULTY_WEIGHT / (Math.round(DIFFICULTY_WEIGHT / 2.0));

        setLayout(grid);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

       
        Board board = new Board(mainFrame, userId, startTime);
        board.init(img);

       
        ColumnDescription columnDescription = new ColumnDescription();
        columnDescription.init((int) Math.round(DIFFICULTY_WEIGHT / 2.0), DIFFICULTY_WEIGHT, img);

        RowDescription rowDescription = new RowDescription();
        rowDescription.init((int) Math.round(DIFFICULTY_WEIGHT / 2.0), DIFFICULTY_WEIGHT, img);

       
        make(columnDescription, 0, 1, 1, 1, 1.0, WEIGHT); 
        make(rowDescription, 1, 0, 1, 1, WEIGHT, 1.0); 
        make(board, 1, 1, 1, 1, WEIGHT, WEIGHT); 
    }

    private void make(JComponent c, int x, int y, int w, int h, double weightx, double weighty) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = w;
        gbc.gridheight = h;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        grid.setConstraints(c, gbc);
        add(c);
    }
}
