package kr.jeju.juyoung.tetris;

import java.util.Random;

public class BoxsShape {

	/**
	 * 블록의 형태에 대한 이름을 정의한 열거형
	 */
	enum TetrisBlocks {
		NoShape, ZShape, SShape, LineShape, TShape, SquareShape, LShape, MirroredLShape
	}

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록의 형태
	 */
	private TetrisBlocks nowShape;

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록을 구성하는 블록의 좌표들 [블록 인덱스][좌표]
	 */
	private int coords[][];

	/**
	 * 이 객체가 가지고 있는 테트리스 블록을 구성하는 블록의 좌표들의 전체 모임 [형태 인덱스][블록 인덱스][좌표]
	 */
	private int coordsTable[][][];

	public BoxsShape() {
		coords = new int[4][2];
		setShape(TetrisBlocks.NoShape);
	}

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록의 형테를 입력 받은 형태로 변경한다.
	 * 
	 * @param TetrisBlocks
	 */
	public void setShape(TetrisBlocks shape) {

		coordsTable = new int[][][] {
				{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, //NoShape
				{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } },//SquareShape
				{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },//LShape
				{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } },//Line
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },
				{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } },
				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },
				{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } };
//				{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } }, // NoShape
//				{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } },// ZShape
//				{ { 0, -1 }, { 0, 0 }, { 1, 0 }, { 1, 1 } },// ZShape
//				{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } },// Line
//				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },// TShape
//				{ { 0, 0 }, { 1, 0 }, { 0, 1 }, { 1, 1 } },// SquareShape
//				{ { -1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } },// LShape
//				{ { 1, -1 }, { 0, -1 }, { 0, 0 }, { 0, 1 } } }; // MirroredLShape

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; ++j) {
				coords[i][j] = coordsTable[shape.ordinal()][i][j];
			}
		}
		nowShape = shape;
	}

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록에 대한 형태를 랜덤으로 변경한다.
	 */
	public void setRandomShape() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		TetrisBlocks[] values = TetrisBlocks.values();
		setShape(values[x]);
	}

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록의 위치 관계에 대한 i 번째 블록의 X 좌표를 가져온다.
	 * 
	 * @param i
	 * @return X 좌표
	 */
	public int getX(int i) {
		return coords[i][0];
	}

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록의 위치 관계에 대한 i 번째 블록의 Y 좌표를 가져온다.
	 * 
	 * @param i
	 * @return Y 좌표
	 */
	public int getY(int i) {
		return coords[i][1];
	}

	/**
	 * 현재 이 객체가 가지고 있는 테트리스 블록의 형태를 반환한다.
	 * 
	 * @return TetrisBlocks의 열거형
	 */
	public TetrisBlocks getShape() {
		return nowShape;
	}

	/**
	 * 블록을 이루는 작은 블록의 X 좌표을 저장한다. i에 따라 작은 블록을 선택이 가능하다.
	 * @param i
	 * @param x
	 */
	private void setX(int i, int x) {
		coords[i][0] = x;
	}

	/**
	 * 블록을 이루는 작은 블록의 Y 좌표을 저장한다. i에 따라 작은 블록을 선택이 가능하다.
	 * @param i
	 * @param Y
	 */
	private void setY(int i, int y) {
		coords[i][1] = y;
	}
	
	/**
	 * 블록이 가지고 있는 제일 작은 X 좌표
	 * @return X 좌표 
	 */
	public int minX() {
		int m = coords[0][0];//첫 번째 X 좌표
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][0]); // 각각의 X 좌표와 비교한다.
		}
		return m;
	}

	/**
	 * 블록이 가지고 있는 제일 작은 Y 좌표
	 * @return Y 좌표
	 */
	public int minY() {
		int m = coords[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	/**
	 * 선택되어 있는 블록의 좌표를 오른쪽으로 돌려서 반환한다.
	 * @return BoxsShape
	 */
	public BoxsShape rotateRight() {
		if (nowShape == TetrisBlocks.SquareShape)
			return this;

		BoxsShape result = new BoxsShape();
		result.nowShape = nowShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, getY(i));
			result.setY(i, -getX(i));
		}
		return result;
	}

	/**
	 * 선택되어 있는 블록의 좌표를 왼쪽으로 돌려서 반환한다.
	 * @return BoxsShape
	 */
	public BoxsShape rotateLeft() {
		if (nowShape == TetrisBlocks.SquareShape)
			return this;

		BoxsShape result = new BoxsShape();
		result.nowShape = nowShape;

		for (int i = 0; i < 4; ++i) {
			result.setX(i, -getY(i));
			result.setY(i, getX(i));
		}
		return result;
	}
}
