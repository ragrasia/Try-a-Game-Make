package old;

import static org.junit.Assert.*;


import org.junit.Test;

public class TetrisTest {

	@Test
	public void createBlockShapeClassTest() {
		TetrisBlock block = new TetrisBlock();
		System.out.println(block.getNowBlockShape());
		block.setRandomShape();
		int coords[][] = block.getCoords();
		for (int[] is : coords) {
			for (int i : is) {
				System.out.print(i);
			}
			System.out.print(" ");
		}
		System.out.println("");
		System.out.println(block.getNowBlockShape());
		
	}
	
	@Test
	public void countTest(){
		System.out.println("ex1");
		
		for(int i= 0; i < 4; ++i ){
			System.out.println(i);
		}
		
		System.out.println("ex2");
		
		for(int i= 0; i < 4; i++ ){
			System.out.println(i);
		}
	}

}
