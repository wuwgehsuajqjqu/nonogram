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

		private void handleClick() {
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
				JOptionPane.showMessageDialog(mainFrame, "클리어!");
				if (mainFrame instanceof MainFrame) {
					((MainFrame) mainFrame).endGame();
				}
			}
		}

		private boolean allNonIscellsUnclicked() {
			for (int i = 0; i < rowSize; i++) {
				for (int j = 0; j < colSize; j++) {
					if (!cellPanel[i][j].isCell && cellPanel[i][j].isClicked) {
						return false;
					}
				}
			}
			return true;
		}
	}
}
