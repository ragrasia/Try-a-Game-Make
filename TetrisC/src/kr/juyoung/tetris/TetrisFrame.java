package kr.juyoung.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import kr.juyoung.tetris.game.TetrisGame;
import kr.juyoung.tetris.game.TetrisGameKeyListener;
import kr.juyoung.tetris.menu_main.TetrisMenu;
import kr.juyoung.tetris.menu_main.TetrisMenuKeyListener;

public class TetrisFrame extends JFrame{
	private JLabel stateLabel = new JLabel("hellow Tetris Game!");
	private JLabel scoreLabel = new JLabel("scroe : -");

	private JPanel bodyPanel = new JPanel();

	private TetrisMenu tetrisMenu = new TetrisMenu();

	private int score = 0;
	
	private TetrisKeyListener key;

	public static void main(String[] args) {
		TetrisFrame game = new TetrisFrame();
		game.setVisible(true);
	}

	public TetrisFrame() {
		// ����
		setTitle("Tetris!");

		// ȭ�� ����
		setSize(200, 410);
		setResizable(false); // â ũ�� ����

		setPanelLocation();

		// ����� ����� �߾ӿ� ��ġ
		setLocationRelativeTo(null);

		// ��Ŀ��
		setFocusable(true);

		// key �̺�Ʈ ����
		key = new TetrisMenuKeyListener(this);
		addKeyListener(key);

		// ���� �̺�Ʈ ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void setPanelLocation() {
		String infoText = "<html>---------------��� Ű--------------- <br />ȭ��ǥ Ű : �̵� <br /> ���� Ű : ����</html>";
		JLabel keyInfo = new JLabel(infoText);

		// head part
		JPanel headPanel = new JPanel();
		// headPanel.setBackground(Color.BLUE);
		headPanel.setLocation(0, 0);
		headPanel.setSize(200, 50);
		headPanel.add(stateLabel);
		add(headPanel);

		// body part
		// bodyPanel.setBackground(Color.orange);
		bodyPanel.setLayout(new BorderLayout());
		bodyPanel.setLocation(0, 50);
		bodyPanel.setSize(200, 250);
		bodyPanel.add(tetrisMenu);
		add(bodyPanel);

		// end part
		JPanel endPanel = new JPanel();
		// endPanel.setBackground(Color.GREEN);
		endPanel.setLocation(0, 300);
		endPanel.setSize(200, 100);
		endPanel.add(scoreLabel);
		endPanel.add(keyInfo);
		add(endPanel);

		JPanel backgroundPanel = new JPanel();
		add(backgroundPanel);
	}



	public String getMenuText() {
		return tetrisMenu.getText();
	}

	public void cleanBodyPenal() {
		bodyPanel.removeAll();
	}

	public void startGame() {
		removeKeyListener(key);
		key = new TetrisGameKeyListener(this);
		addKeyListener(key);
		bodyPanel.add(new TetrisGame(this));
	}

	public void setScore(int add_score) {
		score = score + add_score;
		scoreLabel.setText("score : " + score);
	}

	public void exchangeSelectLabel() {
		tetrisMenu.exchangeSelectLabel();
	}

	public void setSelectLabel(String set_text) {
		tetrisMenu.setText(set_text);
	}

}
