package kr.juyoung.tetris.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import kr.juyoung.tetris.TetrisFrame;
import kr.juyoung.tetris.game.TetrisBlock.BlocksShape;
import kr.juyoung.tetris.menu.TetrisMenu;

public class TetrisGame extends JPanel implements ActionListener{

	Timer timer;
	TetrisFrame parent;
	
	final int boardWidth = 10;
	final int boardHeight = 22;
	
	int pointX = 0;
	int pointY = 0;
	
	TetrisBlock tetrisBlock;
	BlocksShape[] tetrisBoard;
	
	boolean isNewBlock = false;
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
		
		//jpanel 설정
		setBackground(Color.white);
		setFocusable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 새 블록을 생성하는지 확인
		if(isNewBlock){
			isNewBlock = false;
			newBlock();
		}else{
			
		}
		
	}

	private void newBlock() {
		tetrisBlock.setRandomShape();
		//블록 생성 위치 정의
		pointX = boardHeight / 2;
		pointY = boardWidth - 1;
		
		//블록 생성 및 게임 오버 조건 확인
		if(!tryMove(tetrisBlock, pointX, pointY)){
			tetrisBlock.setShape(BlocksShape.NoBlock);
			timer.stop();
			isStart = false;
			parent.setStateLabel("game over");
		}
	}

	private boolean tryMove(TetrisBlock block, int newX, int newY) {
		
		for(int i = 0; i < 4; ++i){
			int x = newX + block.x(i);
			int y = newY + block.y(i);
			
			if( x < 0 || x >= boardWidth || y < 0 || y >= boardHeight){
				return false;
			}
			
			if(shapeAt(x, y) != BlocksShape.NoBlock){
				return false;
			}
		}
		
		return true;
	}

	/**
	 * tetrisBoard 배열에서 
	 * @param x 라인에서 있는 순서
	 * @param y 라인을 뜻함
	 * @return
	 */
	private BlocksShape shapeAt(int x, int y) {
		return tetrisBoard[y * boardWidth + x];
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		System.out.println("print");
		// 사용자가 조정하는 블록
		
		// 이미 쌓여 있는 블록
	}

}
