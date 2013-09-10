package kr.juyoung.tetris.test;

import static org.junit.Assert.*;
import kr.juyoung.tetris.game.TetrisBlock;

import org.junit.Test;

public class TetrisTest {

	@Test
	public void test() {
		TetrisBlock block = new TetrisBlock();
		System.out.println(block.getSelectedBlock());
		
	}

}
