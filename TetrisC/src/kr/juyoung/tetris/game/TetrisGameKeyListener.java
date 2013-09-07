package kr.juyoung.tetris.game;

import java.awt.event.KeyEvent;

import kr.juyoung.tetris.TetrisFrame;
import kr.juyoung.tetris.TetrisKeyListener;

public class TetrisGameKeyListener implements TetrisKeyListener {

	TetrisFrame parent;

	public TetrisGameKeyListener(TetrisFrame parent) {
		this.parent = parent;
		parent.setScore(0);
		System.out.println("game key set");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			System.out.println("lee");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("ree");
			break;
		case KeyEvent.VK_ENTER:
			break;
		default:
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
