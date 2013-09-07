package kr.juyoung.tetris;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tetris extends JFrame {
	
	JLabel selectLabel = new JLabel("START");

	public Tetris() {
		// ����
		setTitle("Tetris!");

		// ȭ�� ����
		setSize(200, 380);
		setResizable(false); // â ũ�� ����

		setPanelLocation();

		// ����� ����� �߾ӿ� ��ġ
		setLocationRelativeTo(null);

		//��Ŀ��
		setFocusable(true);
		
		// key �̺�Ʈ ����
		KeyListener key = new KeyListener();
		addKeyListener(key);

		// ���� �̺�Ʈ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private void setPanelLocation() {
		
		JLabel stateLabel = new JLabel("hellow Tetris Game!");
		JLabel scoreLabel = new JLabel("scroe : -");
		
		selectLabel.setHorizontalAlignment(JLabel.CENTER);
		
		// head part
		JPanel headPanel = new JPanel();
		// headPanel.setBackground(Color.BLUE);
		headPanel.setLocation(0, 0);
		headPanel.setSize(200, 50);
		headPanel.add(stateLabel);
		add(headPanel);

		// body part
		JPanel bodyPanel = new JPanel();
		//bodyPanel.setBackground(Color.orange);
		bodyPanel.setLayout(new BorderLayout());
		bodyPanel.setLocation(0, 50);
		bodyPanel.setSize(200, 250);
		bodyPanel.add(selectLabel);
		add(bodyPanel);

		// end part
		JPanel endPanel = new JPanel();
		// endPanel.setBackground(Color.GREEN);
		endPanel.setLocation(0, 300);
		endPanel.setSize(200, 50);
		endPanel.add(scoreLabel);
		add(endPanel);

		JPanel backgroundPanel = new JPanel();
		add(backgroundPanel);
	}

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setVisible(true);
	}

}
