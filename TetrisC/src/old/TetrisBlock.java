package old;

import java.util.Random;

public class TetrisBlock {

	enum BlocksShape {
		NoBlock, LineBlock, SquareBlock, TBlock, ZBlock, ReverseZBlock, LBlock, ReverseLBlock
	}

	private BlocksShape nowBlockShape;

	private int coords[][];
	private int coordsTable[][][];

	public TetrisBlock() {
		setCoords(new int[4][2]);
		setShape(BlocksShape.NoBlock);
	}

	public void setShape(BlocksShape shape) {
		coordsTable = new int[][][] {
				{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },// no
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 0, -2 } }, // Line
				{ { -1, 0 }, { 0, 0 }, { -1, -1 }, { 0, -1 } }, // Square
				{ { -1, 0 }, { 0, 0 }, { 1, 0 }, { 0, 1 } },// T
				{ { -1, 0 }, { 0, 0 }, { 0, -1 }, { 1, -1 } }, // Z
				{ { 1, 0 }, { 0, 0 }, { 0, -1 }, { -1, -1 } }, // ReverseZ
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { 1, 1 } },// L
				{ { 0, 1 }, { 0, 0 }, { 0, -1 }, { -1, -1 } } // ReverseL
		};

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				getCoords()[i][j] = coordsTable[shape.ordinal()][i][j];
			}
		}

		setNowBlockShape(shape);
	}

	public void setRandomShape() {
		Random r = new Random();
		int x = Math.abs(r.nextInt()) % 7 + 1;
		BlocksShape[] values = BlocksShape.values();
		setShape(values[x]);
	}

	public int getX(int index) {
		return coords[index][0];
	}

	public int getY(int index) {
		return coords[index][1];
	}

	public BlocksShape getNowBlockShape() {
		return nowBlockShape;
	}

	/**
	 * 블록이 가지고 있는 제일 작은 X 좌표
	 * 
	 * @return X 좌표
	 */
	public int minX() {
		int m = coords[0][0];// 첫 번째 X 좌표
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][0]); // 각각의 X 좌표와 비교한다.
		}
		return m;
	}

	/**
	 * 블록이 가지고 있는 제일 작은 Y 좌표
	 * 
	 * @return Y 좌표
	 */
	public int minY() {
		int m = coords[0][1];
		for (int i = 0; i < 4; i++) {
			m = Math.min(m, coords[i][1]);
		}
		return m;
	}

	/*
	 * 이하 테스트 용도의 코드
	 */
	public int[][] getCoords() {
		return coords;
	}

	public void setCoords(int coords[][]) {
		this.coords = coords;
	}

	public void setNowBlockShape(BlocksShape nowBlockShape) {
		this.nowBlockShape = nowBlockShape;
	}

	public TetrisBlock rotateRight() {
		// TODO Auto-generated method stub
		return null;
	}

	public TetrisBlock rotateLeft() {
		// TODO Auto-generated method stub
		return null;
	}

}
