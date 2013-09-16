package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import tetris.Shape.Tetrominoes;

public class Board extends JPanel implements ActionListener {

	/**
	 * 가상적 테트리스 필드의 가로 길이
	 */
	final int BoardWidth = 10;
	/**
	 * 가상적 테트리스 필드의 세로 길이
	 */
	final int BoardHeight = 22;

	Timer timer;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	/**
	 * 제거한 라인 카운트 - 점수 계산
	 */
	int numLinesRemoved = 0;
	/**
	 * 블록의 X 좌표
	 */
	int curX = 0;
	/**
	 * 불록의 Y 좌표
	 */
	int curY = 0;
	JLabel statusbar;
	/**
	 * 블록의 형태
	 */
	Shape curPiece;
	/**
	 * 가상의 테트리스 필드
	 */
	Tetrominoes[] board;

	public Board(Tetris parent) {
		setFocusable(true);//포커스 설정
		curPiece = new Shape();
		timer = new Timer(400, this);//일정 시간마다 actionPerformed 메소드 실행(imp ActionListener) 400 ms
		timer.start();//타이머 시작

		// 테트리스 필드에서 점수 표기 라벨을 얻어옴
		statusbar = parent.getStatusBar();
		
		//쌓이는 블록에 대한 정보를 저장하는 필드 생성
		board = new Tetrominoes[BoardWidth * BoardHeight];
		//테트리스 게임에 대한 키보드 리스너 등록
		addKeyListener(new TAdapter());
		clearBoard();
	}

	/**
	 * 타이머에서 사용하는 ActionListener가 실행하는 메소드
	 */
	public void actionPerformed(ActionEvent e) {
		//떨어지는 상태 확인
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	/**
	 * 화면의 길이 / 게임화면의 전체 가로 길이
	 * @return 작은 블록의 가로 길이
	 */
	int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth;
	}

	/**
	 * 화면의 길이 / 게임화면의 전체 세로 길이
	 * @return 작은 블록의 세로 길이
	 */
	int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight;
	}

	/**
	 * 테트리스 필드에서 해당하는 좌표에 무엇이 존재하는 가 확인
	 * @param x
	 * @param y
	 * @return Tetrominoes 
	 */
	Tetrominoes shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x];
	}

	/**
	 * 테트리스 게임을 시작하는 함수
	 */
	public void start() {
		if (isPaused)// 정지 상태인 경우 정지
			return;

		isStarted = true;
		isFallingFinished = false;
		numLinesRemoved = 0;
		clearBoard();

		newPiece();
		timer.start();
	}

	/**
	 * 게임 정지 혹은 다시 시작(정지 풀기)
	 */
	private void pause() {
		if (!isStarted)
			return;

		isPaused = !isPaused;
		if (isPaused) {
			timer.stop();
			statusbar.setText("paused");
		} else {
			timer.start();
			statusbar.setText(String.valueOf(numLinesRemoved));
		}
		repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		//쌓여 있는 블록을 그린다.
		for (int i = 0; i < BoardHeight; ++i) {
			for (int j = 0; j < BoardWidth; ++j) {
				Tetrominoes shape = shapeAt(j, BoardHeight - i - 1);
				if (shape != Tetrominoes.NoShape)
					drawSquare(g, 0 + j * squareWidth(), boardTop + i
							* squareHeight(), shape);
			}
		}

		// 현재 떨어지고 있는 테트리스 블록을 그린다.
		if (curPiece.getShape() != Tetrominoes.NoShape) {
			//테트리스 블록을 그린다.
			for (int i = 0; i < 4; ++i) {
				int x = curX + curPiece.x(i);
				int y = curY - curPiece.y(i);
				drawSquare(g, 0 + x * squareWidth(), boardTop
						+ (BoardHeight - y - 1) * squareHeight(),
						curPiece.getShape());
			}
		}
	}

	/**
	 * 테트리스 블록 한번에 떨어 트리기
	 */
	private void dropDown() {
		int newY = curY;
		while (newY > 0) {
			if (!tryMove(curPiece, curX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}

	/**
	 * 블록 이동 처리
	 */
	private void oneLineDown() {
		if (!tryMove(curPiece, curX, curY - 1))
			pieceDropped();
	}

	/**
	 * 테트리스 필드 초기화
	 */
	private void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; ++i)
			board[i] = Tetrominoes.NoShape;
	}

	/**
	 * 테트리스 블록 쌓기
	 */
	private void pieceDropped() {
		for (int i = 0; i < 4; ++i) {
			int x = curX + curPiece.x(i);
			int y = curY - curPiece.y(i);
			board[(y * BoardWidth) + x] = curPiece.getShape();
		}

		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	/**
	 * 새로운 테트리스 블록을 생성한다.
	 */
	private void newPiece() {
		curPiece.setRandomShape();// 랜덤 블록 설정
		//처음 블록 생성 위치 - 상단 중앙
		curX = BoardWidth / 2 + 1;
		curY = BoardHeight - 1 + curPiece.minY();
		
		//게임 오버 조건 확인
		if (!tryMove(curPiece, curX, curY)) {
			curPiece.setShape(Tetrominoes.NoShape);
			timer.stop();
			isStarted = false;
			statusbar.setText("game over");
		}
	}

	/**
	 * 블록 이동 시도
	 * @param newPiece
	 * @param newX
	 * @param newY
	 * @return 문제가 없는 경우 true, 문제 발생시 false
	 */
	private boolean tryMove(Shape newPiece, int newX, int newY) {
		//4개의 작은 블록 확인
		for (int i = 0; i < 4; ++i) {
			//최초 좌표 0, 0 은 상단 왼쪽이다.0
			int x = newX + newPiece.x(i);//양옆이동
			int y = newY - newPiece.y(i);//위아래 이동
			//x혹은 y가 위, 아래, 양 옆으로 넘어가지 않는지 확인
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight)
				return false;
			//x y 좌표에 블록이 있는지 확인
			if (shapeAt(x, y) != Tetrominoes.NoShape)
				return false;
		}

		curPiece = newPiece;
		curX = newX;
		curY = newY;
		repaint();//다시 그리기
		return true;
	}

	private void removeFullLines() {
		int numFullLines = 0;

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;

			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == Tetrominoes.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++numFullLines;
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j)
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
				}
			}
		}

		if (numFullLines > 0) {
			numLinesRemoved += numFullLines;
			statusbar.setText(String.valueOf(numLinesRemoved));
			isFallingFinished = true;
			curPiece.setShape(Tetrominoes.NoShape);
			repaint();
		}
	}

	/**
	 * 사각형 상자를 그린다.
	 * @param g
	 * @param x
	 * @param y
	 * @param shape
	 */
	private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
		//색 정의 모양과 같은 위치에 해당하는 것의 색이 정의되어 있다.
		Color colors[] = { new Color(0, 0, 0), new Color(204, 102, 102),
				new Color(102, 204, 102), new Color(102, 102, 204),
				new Color(204, 204, 102), new Color(204, 102, 204),
				new Color(102, 204, 204), new Color(218, 170, 0) };

		//선택된 모양의 컬러 가져오기 
		Color color = colors[shape.ordinal()];

		//사각형의 내부
		g.setColor(color);
		g.fillRect(x + 1, y + 1, squareWidth() - 2, squareHeight() - 2);

		//사각형의 외부 상단, 왼쪽
		g.setColor(color.brighter());
		g.drawLine(x, y + squareHeight() - 1, x, y); //왼쪽 선
		g.drawLine(x, y, x + squareWidth() - 1, y); // 상단 선

		//사각형의 외부 하단, 오른쪽
		g.setColor(color.darker());
		g.drawLine(x + 1, y + squareHeight() - 1, x + squareWidth() - 1, y
				+ squareHeight() - 1);//하단
		g.drawLine(x + squareWidth() - 1, y + squareHeight() - 1, x
				+ squareWidth() - 1, y + 1);// 오른쪽

	}

	/**
	 * 키 입력 처리
	 * @author hio
	 *
	 */
	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {

			if (!isStarted || curPiece.getShape() == Tetrominoes.NoShape) {
				return;
			}

			int keycode = e.getKeyCode();

			if (keycode == 'p' || keycode == 'P') {
				pause();
				return;
			}

			if (isPaused)
				return;

			switch (keycode) {
			case KeyEvent.VK_LEFT:
				tryMove(curPiece, curX - 1, curY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(curPiece, curX + 1, curY);
				break;
			case KeyEvent.VK_DOWN:
				tryMove(curPiece.rotateRight(), curX, curY);
				break;
			case KeyEvent.VK_UP:
				tryMove(curPiece.rotateLeft(), curX, curY);
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