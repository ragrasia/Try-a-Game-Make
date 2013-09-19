package kr.jeju.juyoung.tetris;

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

import kr.jeju.juyoung.tetris.BoxsShape.TetrisBlocks;

public class GameBoard extends JPanel implements ActionListener {

	/**
	 * 작은 사각형의 모임을 테트리스 블록이라고 하고 이 테트리스 블록을 이루는 작은 사각형을 블록이라 한다.
	 * 
	 * if문을 사용하는 경우 if 안의 코드가 1 줄인 경우 {}블록을 생략 할 수 있지만 이 코드에서는 생략하지 않는다.\
	 * 
	 * 하나의 테트리스 블록은 4개의 블록으로 이루어져 있다.
	 */

	// 가상 위치 정보
	/**
	 * 가상 블록 필드의 길이 - 10개의 블록이 들어간다.
	 */
	final int BoardWidth = 10;
	/**
	 * 가상 블록 필드의 높이 - 22개의 블록이 들어간다.
	 */
	final int BoardHeight = 22;

	Timer timer;
	/**
	 * 테트리스 블록이 떨어졌는지 확인한다.
	 */
	boolean isFallingFinised = false;
	/**
	 * 게임의 시작 상태
	 */
	boolean isStarted = false;
	/**
	 * 게임의 정지 요청 상태
	 */
	boolean isPaused = false;
	/**
	 * 조작이 가능한 블록의 X 좌표
	 */
	int locationX = 0;
	/**
	 * 조작이 가능한 블록의 Y 좌표
	 */
	int locationY = 0;
	/**
	 * 테트리스 블록의 형태 정보와 현재 테트리스 블록의 정보를 가지고 있다.
	 */
	BoxsShape pieceShape;
	/**
	 * 테트리스 블록을 이름으로 정의 하는 enum 이며 실제 블록들의 위치와 블록이 어떤 테트리스 블록인지 저장하는 역활을 한다.
	 */
	TetrisBlocks[] board;

	/**
	 * 제거한 라인의 수
	 */
	int countLinesRemoved = 0;
	/**
	 * TetrisWindow에서 가져온 게임 정보 표시 바
	 */
	JLabel statusbar;

	public GameBoard(TetrisWindow parent) {
		setFocusable(true); // 이 객체가 구현하는 창이 포커스를 가진다.
		pieceShape = new BoxsShape();
		timer = new Timer(400, this); // actionPerformed를 400 ms 마다 실행 설정
		timer.start(); // 타이머 실행

		statusbar = parent.getStatusbar();

		board = new TetrisBlocks[BoardWidth * BoardHeight];

		addKeyListener(new TetrisKeyListener());
		clearBoard();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (isFallingFinised) {
			isFallingFinised = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}
	/**
	 * 화면의 길이 / 게임화면의 전체 가로 길이
	 * 
	 * @return 작은 블록의 가로 길이
	 */
	int squareWidth() {
		return (int) getSize().getWidth() / BoardWidth;
	}
	/**
	 * 화면의 길이 / 게임화면의 전체 세로 길이
	 * 
	 * @return 작은 블록의 세로 길이
	 */
	int squareHeight() {
		return (int) getSize().getHeight() / BoardHeight;
	}
	
	/**
	 * board에 저장된 블록의 정보를 반환한다.
	 * 1차원 배열을 2차원 배열 처럼 사용한다.
	 * @param x 블록의 가로 위치
	 * @param y 블록의 세로 위치
	 * @return
	 */
	TetrisBlocks shapeAt(int x, int y) {
		return board[(y * BoardWidth) + x];
	}
	/**
	 * 게임을 시작하는 메소드
	 */
	public void start() {
		if (isStarted) { // 이미 시작하였는지 확인한다.
			return;
		}

		isStarted = true;
		isFallingFinised = false;
		countLinesRemoved = 0;
		clearBoard();

		newPiece();
		timer.start();
	}
	/**
	 * 게임 정지 혹은 다시 시작
	 */
	private void pause() {
		if (!isStarted) { // 시작상태가 false 로 되었는지 확인한다.
			return;
		}

		isPaused = !isPaused;

		if (isPaused) {
			timer.stop();
			statusbar.setText("paused");
		} else{
			timer.start();
			statusbar.setText("score : " + countLinesRemoved);
		}
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Dimension size = getSize();
		int boardTop = (int) size.getHeight() - BoardHeight * squareHeight();

		// 쌓여 있는 블록을 그린다.
		for (int nowHeight = 0; nowHeight < BoardHeight; ++nowHeight) {
			for (int nowWidth = 0; nowWidth < BoardWidth; ++nowWidth) {
				
				TetrisBlocks shape = shapeAt(nowWidth, BoardHeight - nowHeight - 1);
				if (shape != TetrisBlocks.NoShape)
					drawSquare(g, 0 + nowWidth * squareWidth(), boardTop + nowHeight
							* squareHeight(), shape);
			}
		}

		// 현재 떨어지고 있는 테트리스 블록을 그린다.
		if (pieceShape.getShape() != TetrisBlocks.NoShape) {
			// 테트리스 블록을 그린다.
			for (int i = 0; i < 4; ++i) {
				int x = locationX + pieceShape.getX(i);
				int y = locationY - pieceShape.getY(i);
				drawSquare(g, 0 + x * squareWidth(), boardTop
						+ (BoardHeight - y - 1) * squareHeight(),
						pieceShape.getShape());
			}
		}
	}
	/**
	 * 테트리스 블록을 바닥까지 한번에 이동 시킨다.
	 */
	private void dropDown() {
		int newY = locationY;
		while (newY > 0) {
			if (!tryMove(pieceShape, locationX, newY - 1))
				break;
			--newY;
		}
		pieceDropped();
	}
	/**
	 * 테트리스 블록을 아래로 이동 시킨다.
	 */
	private void oneLineDown() {
		if (!tryMove(pieceShape, locationX, locationY - 1)) {
			pieceDropped();
		}
	}
	/**
	 * board에 저장된 정보를 TetrisBlocks.NoShape로 전부 변경(초기화)한다.
	 * 
	 * 이 함수를 사용하지 않으면 nullpointer 관련 에러가 발생한다.
	 */
	private void clearBoard() {
		for (int i = 0; i < BoardHeight * BoardWidth; i++) {
			board[i] = TetrisBlocks.NoShape;
		}
	}
	/**
	 * 1. 테트리스 블록을 board 등록한다. 테트리스 블록을 쌓여 있는 조작이 불가능한 블록으로 변경한다. 2. 라인 제거 기능을
	 * 불러온다. 3. 테트리스 블록을 생성해야 하는지 확인하고 새로운 테트리스 블록을 생성한다.
	 */
	private void pieceDropped() {
		for (int i = 0; i < 4; ++i) { // 테트리스 블록은 4개의 블록으로 이루어져 있다.
			int x = locationX + pieceShape.getX(i);
			int y = locationY - pieceShape.getY(i);
			board[(y * BoardWidth) + x] = pieceShape.getShape();
			System.out.println("add : "+((y * BoardWidth) + x) + " shape : "+pieceShape.getShape());
		}

		removeFullLines();

		if (!isFallingFinised) {// 현재 블록의 상태 이다.
			newPiece();
		}
	}
	/**
	 * 새로운 테트리스 블록을 생성한다.
	 */
	private void newPiece() {
		pieceShape.setRandomShape();

		// 블록이 생성되는 위치 설정 - 상단 중앙
		locationX = BoardWidth / 2 + 1;
		locationY = BoardHeight - 1 + pieceShape.minY();
		System.out.println(pieceShape.getShape()+" : create location x : " + locationX + " y : "
				+ locationY);

		if (!tryMove(pieceShape, locationX, locationY)) {
			pieceShape.setShape(TetrisBlocks.NoShape);
			timer.stop();
			isStarted = false;
			statusbar.setText("game over");
			System.out.println("game over");
		}
	}
	/**
	 * 블록 이동 시도
	 * @param newPiece
	 * @param newX
	 * @param newY
	 * @return 문제가 없는 경우 true, 문제 발생시 false
	 */
	private boolean tryMove(BoxsShape newPiece, int newX, int newY) {
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.getX(i);
			int y = newY - newPiece.getY(i);
			System.out.println(newX+" "+newPiece.getX(i)+" "+newY+" "+newPiece.getY(i));
			// 테트리스 필드를 벗어나는 지 확인
			if (x < 0 || x >= BoardWidth || y < 0 || y >= BoardHeight) {
				System.err.println("필드 x : "+ x +" y : " + y);
				return false;
			}

			// 다른 테트리스 블록과 충돌하는 지 확인
			if (shapeAt(x, y) != TetrisBlocks.NoShape) {
				System.out.println("블록");
				return false;
			}
		}

		pieceShape = newPiece;
		locationX = newX;
		locationY = newY;
		repaint();
		return true;
	}
	/**
	 * board에서 하나의 라인이 꽉 차있는 라인을 제거한다.
	 */
	private void removeFullLines() {
		int countFullLines = 0; // 이 메소드 내에서 제거하는 라인의 수

		for (int i = BoardHeight - 1; i >= 0; --i) {
			boolean lineIsFull = true;// 라인에 대한 상태

			// 해당하는 라인에서 하나라도 빈 블록이 있는지 확인
			for (int j = 0; j < BoardWidth; ++j) {
				if (shapeAt(j, i) == TetrisBlocks.NoShape) {
					lineIsFull = false;
					break;
				}
			}

			if (lineIsFull) {
				++countFullLines;
				// 지워진 라인 위의 블록을 아래로 이동
				for (int k = i; k < BoardHeight - 1; ++k) {
					for (int j = 0; j < BoardWidth; ++j) {
						board[(k * BoardWidth) + j] = shapeAt(j, k + 1);
					}
				}
			}
		}

		if (countFullLines > 0) {
			countLinesRemoved += countFullLines;
			statusbar.setText("score : " + countLinesRemoved);
			isFallingFinised = true;
			pieceShape.setShape(TetrisBlocks.NoShape);
			repaint();
		}

	}
	/**
	 * 블록을 그려주는 함수
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param shape
	 */
	private void drawSquare(Graphics g, int x, int y, TetrisBlocks shape) {
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
	 * 키 입력에 대한 처리를 하는 클래스
	 */
	class TetrisKeyListener extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);

			if (!isStarted || pieceShape.getShape() == TetrisBlocks.NoShape) {
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
				tryMove(pieceShape, locationX - 1, locationY);
				break;
			case KeyEvent.VK_RIGHT:
				tryMove(pieceShape, locationX + 1, locationY);
				break;
			case KeyEvent.VK_DOWN:
				tryMove(pieceShape.rotateRight(), locationX, locationY);
				break;
			case KeyEvent.VK_UP:
				tryMove(pieceShape.rotateLeft(), locationX, locationY);
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
