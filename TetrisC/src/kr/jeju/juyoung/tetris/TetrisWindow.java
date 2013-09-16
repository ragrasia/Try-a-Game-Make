package kr.jeju.juyoung.tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TetrisWindow extends JFrame {

	JLabel statusbar;

	public TetrisWindow() {
		statusbar = new JLabel("시작 대기중"); // 게임 상태
		add(statusbar, BorderLayout.SOUTH); // 라벨 삽입 및 위치 설정

		GameBoard board = new GameBoard();
		add(board);
		board.start(); // 게임 시작
		
		setSize(300, 600); // 전체 창 크기 설정
		setResizable(false); // 창 크기 고정
		setTitle("Tetris!"); // 타이틀 삽입
		setDefaultCloseOperation(EXIT_ON_CLOSE); //종료 버튼 설정
	}

	public static void main(String[] args) {
		TetrisWindow tetrisWindow = new TetrisWindow();
		tetrisWindow.setLocationRelativeTo(null); // 모니터 화면 중앙에 위치
		tetrisWindow.setVisible(true); // show 명령어 대신 사용한다.
	}

}
