package kr.juyoung.tetris.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import kr.juyoung.tetris.TetrisFrame;
import kr.juyoung.tetris.menu_main.TetrisMenu;

public class TetrisGame extends JPanel implements ActionListener{

	Timer timer;
	TetrisFrame parent;
	
	int pointX = 0;
	int pointY = 0;
	
	int score = 0;

	public TetrisGame(TetrisFrame parent) {
		System.out.println("start game");
		this.parent = parent;
		
		timer = new Timer(600, this);
		timer.start();
		
		//jpanel 설정
		setBackground(Color.white);
		setFocusable(true);
	}

	@Override
	public void paint(Graphics g) {
		// TODO 주기적으로 게임 화면을 그려 주는 부분
		super.paint(g);
		System.out.println("print");
		// 사용자가 조정하는 블록
		
		// 이미 쌓여 있는 블록
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//게임 오버 확인
		
		//라인 제거 확인
	}
	
}
