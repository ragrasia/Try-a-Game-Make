package kr.juyoung.tetris.game;

public class TetrisBlock {

	enum BlocksShape {
		NoBlock, LineBlock, SquareBlock, TBlock, ZBlock, ReverseZBlock, LBlock, ReverseLBlock
	}

	private BlocksShape selectedBlock;

	private int coords[][];
	private int coordsTable[][][];

	public TetrisBlock() {
		setCoords(new int[4][2]);
		setShape(BlocksShape.NoBlock);
	}

	private void setShape(BlocksShape shape) {
		coordsTable = new int[][][] {
				{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },// no shape
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

		setSelectedBlock(shape);
	}

	public int[][] getCoords() {
		return coords;
	}

	public void setCoords(int coords[][]) {
		this.coords = coords;
	}

	public BlocksShape getSelectedBlock() {
		return selectedBlock;
	}

	public void setSelectedBlock(BlocksShape selectedBlock) {
		this.selectedBlock = selectedBlock;
	}

}
