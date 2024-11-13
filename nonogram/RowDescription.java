package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RowDescription extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	void init(int rows,int cols,String[] img) {
            setLayout(new GridLayout(1, cols));

            String[] transposed = new String[img.length];

            //transpose img
            for (int i = 0; i < img.length; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < cols; j++) {
                    sb.append(img[j].charAt(i));
                }
                transposed[i] = sb.toString();
            }

        for (String string : transposed) {
            System.out.println(string);
        }

            for (int i = 0; i < cols; i++) {
                JPanel col = new JPanel();
                col.setLayout(new GridLayout(rows, 1));
                col.setBorder(BorderFactory.createBevelBorder(1));

                String[] split = transposed[i].split("0");

                for (int j = 0; j < split.length; j++) {
                    split[j] = split[j].length()+"";
                }

                ArrayList<String> list = new ArrayList<>(Arrays.stream(split).toList());

                while (list.contains("0")) {
                    list.remove("0");
                }

                Collections.reverse(list);
                String[] array = list.toArray(new String[0]);

                for (int j = rows-1; j >= 0; j--) {
                    JPanel tmp = new JPanel();

                    tmp.setBorder(BorderFactory.createEmptyBorder());
                    tmp.setBackground(Color.white);

                    JLabel tmpLabel = new JLabel();
                    String string = array.length <= j ? "" : array[j];
                    tmpLabel.setText(string);
                    tmpLabel.setFont(tmpLabel.getFont().deriveFont(10.0f));

                    tmp.add(tmpLabel);
                    col.add(tmp);

                    this.add(col);
                }
            }
        }
}