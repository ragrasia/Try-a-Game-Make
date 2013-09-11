package kr.juyoung.tetris.test;

import static org.junit.Assert.*;
import kr.juyoung.tetris.game.TetrisBlock;

import org.junit.Test;

public class TetrisTest {

	@Test
	public void createBlockShapeClassTest() {
		TetrisBlock block = new TetrisBlock();
		System.out.println(block.getSelectedBlock());
		int coords[][] = block.getCoords();
		for (int[] is : coords) {
			for (int i : is) {
				System.out.print(i);
			}
			System.out.println("");
		}
		
	}

}
