package kr.juyoung.tetris.game;

import java.awt.Color;

import javax.swing.JPanel;

import kr.juyoung.tetris.TetrisFrame;
import kr.juyoung.tetris.menu_main.TetrisMenu;

public class TetrisGame extends JPanel {
	

	public TetrisGame(TetrisFrame parent) {
		System.out.println("start game");
		setBackground(Color.white);
		setFocusable(true);
	}

}
