package kr.juyoung.tetris.game;

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

		nowBlockShape = shape;
	}
	
	public void setRandomShape() {
		// TODO Auto-generated method stub
		
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

	public BlocksShape getSelectedBlock() {
		return nowBlockShape;
	}

	public void setSelectedBlock(BlocksShape selectedBlock) {
		this.nowBlockShape = selectedBlock;
	}

	public int x(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int y(int i) {
		// TODO Auto-generated method stub
		return 0;
	}

}
