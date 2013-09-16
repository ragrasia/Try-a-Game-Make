package old;

import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;



public class TetrisMenuKeyListener implements TetrisKeyListener {

	TetrisFrame parent;

	public TetrisMenuKeyListener(TetrisFrame parent) {
		this.parent = parent;
		System.out.println("menu key set");
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			System.out.println("l");
			parent.exchangeSelectLabel();
			//parent.setSelectLabel("START");
			break;
		case KeyEvent.VK_RIGHT:
			System.out.println("r");
			parent.exchangeSelectLabel();
			//parent.setSelectLabel("EXIT");
			break;
		case KeyEvent.VK_ENTER:
			if (parent.getMenuText() == "START") {
				parent.setSelectLabel("");
				parent.cleanBodyPenal();
				parent.startGame();
			} else {
				System.exit(0);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
