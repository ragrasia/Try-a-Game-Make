package tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tetris extends JFrame {

	JLabel statusbar;

	public Tetris() {

		statusbar = new JLabel(" 0");
		add(statusbar, BorderLayout.SOUTH);
		Board board = new Board(this);// tetris 게임에 대한 정의가 되어 있다.
		add(board);
		board.start();

		setSize(200, 400);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public JLabel getStatusBar() {
		return statusbar;
	}

	public static void main(String[] args) {

		Tetris game = new Tetris();
		// 실행시 화면 중앙에 배치
		game.setLocationRelativeTo(null);
		game.setVisible(true);

	}
}