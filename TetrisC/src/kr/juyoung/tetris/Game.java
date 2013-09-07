package kr.juyoung.tetris;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends JPanel {

	final JLabel startText = new JLabel("Start");
	final JLabel exitText = new JLabel("exit");
	final JLabel stateText = new JLabel("input");
	private static String input;

	public Game() {
		
//		// 화면 구성 요소 설정
//		JPanel tetrisBoard = new JPanel();
//		tetrisBoard.setSize(240, 360);
//		int tetrisLocationX = (getHeight() - tetrisBoard.getHeight()) / 2;
//		int tetrisLocationY = (getWidth() - tetrisBoard.getWidth()) - 90;
//		tetrisBoard.setLocation(tetrisLocationX, tetrisLocationY);
		
		setSize(240, 360);
		setBackground(Color.white);
		setFocusable(true);
		
		startText.setSize(20, 20);
		startText.setLocation(HEIGHT, HEIGHT);

		add(startText);
		add(exitText);
		add(stateText);
		// 키 입력
		addKeyListener(new inputKeyListener());
	}

	class inputKeyListener extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			input = KeyEvent.getKeyText(e.getKeyCode());

			stateText.setText(input + " : " + e.getKeyCode());

			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				startText.setText("[start]");
				exitText.setText("exit");
				break;
			case KeyEvent.VK_DOWN:
				startText.setText("start");
				exitText.setText("[exit]");
				break;
			default:
				break;
			}
		}

	}

}
