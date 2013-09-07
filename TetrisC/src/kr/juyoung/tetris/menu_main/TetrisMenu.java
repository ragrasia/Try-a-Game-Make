package kr.juyoung.tetris.menu_main;

import javax.swing.JLabel;

public class TetrisMenu extends JLabel {
	
	public TetrisMenu() {
		setText("START");
		setHorizontalAlignment(JLabel.CENTER);
	}

	public void setSelectLabel(String setText) {
		setText(setText);
		
	}

	public void exchangeSelectLabel() {
		if (getText() == "START") {
			setText("EXIT");
		} else {
			setText("START");
		}
	}

}
