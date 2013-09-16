package old;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import old.TetrisBlock.BlocksShape;


public class TetrisGame extends JPanel implements ActionListener {

	Timer timer;
	TetrisFrame parent;

	final int boardWidth = 10;
	final int boardHeight = 22;

	int pointX = 0;
	int pointY = 0;

	TetrisBlock tetrisBlock;
	BlocksShape[] tetrisBoard;

	boolean isRequestNewBlock = false;
	boolean isStart = false;
	boolean isStop = false;

	int score = 0;

	public TetrisGame(TetrisFrame parent) {
		System.out.println("start game");
		this.parent = parent;

		tetrisBlock = new TetrisBlock();
		tetrisBoard = new BlocksShape[boardWidth * boardHeight];

		timer = new Timer(600, this);
		timer.start();

		// jpanel 설정
		setBackground(Color.white);
		setFocusable(true);
		addKeyListener(new TetrisGameKeyListener());

		// 초기화 최초에는 필드가 비어 있다.
		clearBoard();
	}

	/**
	 * 테트리스 필드 초기화
	 */
	private void clearBoard() {
		System.out.println("clearBoard");
		for (int i = 0; i < boardHeight * boardWidth; ++i)
			tetrisBoard[i] = BlocksShape.NoBlock;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 새 블록을 생성하는지 확인
		if (isRequestNewBlock) {
			System.out.println("call new block");
			isRequestNewBlock = false;
			newBlock();
		} else {
			System.out.println("call block one line down");
			oneLineDown();
		}

	}

	private void newBlock() {
		System.out.println("new block");
		tetrisBlock.setRandomShape();
		// 블록 생성 위치 정의
		pointX = boardHeight / 2;
		pointY = boardWidth - 1;

		// 블록 생성 및 게임 오버 조건 확인
		if (!tryMove(tetrisBlock, pointX, pointY)) {
			tetrisBlock.setShape(BlocksShape.NoBlock);
			timer.stop();
			isStart = false;
			parent.setStateLabel("game over");
		}
	}

	private void oneLineDown() {
		if (!tryMove(tetrisBlock, pointX, pointY - 1)) {
			pieceDropped();
		}
	}

	public boolean tryMove(TetrisBlock block, int newX, int newY) {

		for (int i = 0; i < 4; ++i) {
			int x = newX + block.getX(i);
			int y = newY + block.getY(i);

			if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight) {
				return false;
			}

			if (shapeAt(x, y) != BlocksShape.NoBlock) {
				return false;
			}
		}
		return true;
	}

	private void pieceDropped() {
		for (int i = 0; i < 4; ++i) {
			int x = pointX + tetrisBlock.getX(i);
			int y = pointY - tetrisBlock.getY(i);
			tetrisBoard[(y * boardWidth) + x] = tetrisBlock.getNowBlockShape();
		}

		removeFullLines();

		if (!isRequestNewBlock) {
			newBlock();
		}
	}

	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = boardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < boardWidth; ++j) {
				if (shapeAt(j, i) == BlocksShape.NoBlock) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < boardHeight - 1; ++k) {
					for (int j = 0; j < boardWidth; ++j)
						tetrisBoard[(k * boardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			score += numFullLines;
			parent.setScoreTxt("score : " + score);
			isRequestNewBlock = true;
			tetrisBlock.setShape(BlocksShape.NoBlock);
			repaint();
		}
	}

	/**
	 * tetrisBoard 배열에서
	 * 
	 * @param x
	 *            라인에서 있는 순서
	 * @param y
	 *            라인을 뜻함
	 * @return
	 */
	private BlocksShape shapeAt(int x, int y) {
		return tetrisBoard[y * boardWidth + x];
	}

	/**
	 * 사용자가 움직이는 블록과 쌓여 있는 블록에 대해서 각각 그려준다.
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - boardHeight * squareHeight();
		// 이미 쌓여 있는 블록
		for (int height = 0; height < boardHeight; height++) {
			for (int width = 0; width < boardWidth; width++) {
				BlocksShape shape = shapeAt(width, boardHeight - height - 1);
				if (shape != BlocksShape.NoBlock) {
					drawSquare(g, 0 + width * squareWidth(), boardTop + height
							* squareHeight(), shape);
				}
			}
		}

		// 사용자가 조정하는 블록
		for (int shotBlock = 0; shotBlock < 4; shotBlock++) {
			int blockX = pointX + tetrisBlock.getX(shotBlock);
			int blockY = pointY + tetrisBlock.getY(shotBlock);

			drawSquare(g, 0 + blockX * squareWidth(), boardTop
					+ (boardHeight - blockY - 1) * squareHeight(),
					tetrisBlock.getNowBlockShape());
		}
	}

	/**
	 * 실질적으로 테트리스 블록을 그려주는 함수
	 * 
	 * @param g
	 *            실제 그림을 그려주는 graphics 객체
	 * @param i
	 *            높이 좌표
	 * @param j
	 *            길이 좌표
	 * @param nowBlockShape
	 *            블록의 형태 - 실질적으로는 블록의 색
	 */
	private void drawSquare(Graphics g, int x, int y, BlocksShape shape) {
		// 색 정의 모양과 같은 위치에 해당하는 것의 색이 정의되어 있다.
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102),
				new Color(102, 204, 102), new Color(102, 102, 204),
				new Color(204, 204, 102), new Color(204, 102, 204),
				new Color(102, 204, 204), new Color(218, 170, 0) };

		// 선택된 모양의 컬러 가져오기
		Color color = colors[shape.ordinal()];

		// 사각형의 내부
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		// 사각형의 외부 상단, 왼쪽
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y); // 왼쪽 선
		g.drawLine(x, y, x + squareWidth() - 1, y); // 상단 선

		// 사각형의 외부 하단, 오른쪽
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y
				+ squareHeight() - 1);// 하단
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x
				+ squareWidth() - 1, y + 1);// 오른쪽
	}

	/**
	 * 화면의 길이 / 게임화면의 전체 가로 길이
	 * 
	 * @return 작은 블록의 가로 길이
	 */
	int squareWidth() {
		return (int) getSize().getWidth() / boardWidth;
	}

	/**
	 * 화면의 길이 / 게임화면의 전체 세로 길이
	 * 
	 * @return 작은 블록의 세로 길이
	 */
	int squareHeight() {
		return (int) getSize().getHeight() / boardHeight;
	}

	/**
	 * 테트리스 게임을 시작하는 함수
	 */
	public void start() {
		if (isStart)// 정지 상태인 경우 정지
			return;

		isStart = true;
		isRequestNewBlock = false;
		score = 0;
		clearBoard();

		newBlock();
		timer.start();
	}

	/**
	 * 게임 정지 혹은 다시 시작(정지 풀기)
	 */
	public void stop() {
		if (!isStart)
			return;

		isStop = !isStop;
		if (isStop) {
			timer.stop();
			parent.setScoreTxt("stop");
		} else {
			timer.start();
			parent.setScoreTxt("score : " + score);
		}
		repaint();
	}

	private void dropDown() {

	}

	/**
	 * 키 입력 처리
	 * 
	 * @author hio
	 * 
	 */
	class TetrisGameKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			if (!isStop
					|| tetrisBlock.getNowBlockShape() == BlocksShape.NoBlock) {
				return;
			}

			int keycode = e.getKeyCode();

			if (keycode == 'p' || keycode == 'P') {
				stop();
				return;
			}

			if (isStop)
				return;

			switch (keycode) {
			case KeyEvent.VK_LEFT:
				tryMove(tetrisBlock, pointX - 1, pointY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(tetrisBlock, pointX + 1, pointY);
				break;
			case KeyEvent.VK_DOWN:
				tryMove(tetrisBlock.rotateRight(), pointX, pointY);
				break;
			case KeyEvent.VK_UP:
				tryMove(tetrisBlock.rotateLeft(), pointX, pointY);
				break;
			case KeyEvent.VK_SPACE:
				dropDown();
				break;
			case 'd':
				oneLineDown();
				break;
			case 'D':
				oneLineDown();
				break;
			}

		}
	}

}
