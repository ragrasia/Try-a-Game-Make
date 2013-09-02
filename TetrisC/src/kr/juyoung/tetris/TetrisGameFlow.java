package kr.juyoung.tetris;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TetrisGameFlow {
	
	static String inputKetString = "none";

	public static void main(String[] args) {
		menuDisplay();
	}

	private static void menuDisplay() {
		// ����
		JFrame menu = new JFrame("Tetris! - menu");
		// ȭ�� ����
		final JLabel inputKeyTest = new JLabel(inputKetString, JLabel.CENTER);
		
		menu.add(inputKeyTest);
		
		//Ű �Է�
		menu.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				inputKeyTest.setText(KeyEvent.getKeyText(e.getKeyCode()));
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				inputKetString = e.toString();
			}
		});
		
		// ȭ�� ũ��
		menu.setSize(500, 600);

		// ����� ����� �߾ӿ� ��ġ
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		final int x = (screenSize.width - menu.getWidth()) / 2;
		final int y = (screenSize.height - menu.getHeight()) / 2;
		menu.setLocation(x, y);

		// ���� �̺�Ʈ ����
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ���̱�
		menu.setVisible(true);
	}

}
