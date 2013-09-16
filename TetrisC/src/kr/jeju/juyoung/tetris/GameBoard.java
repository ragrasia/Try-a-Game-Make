package kr.jeju.juyoung.tetris;

import javax.swing.JPanel;
import javax.swing.Timer;

import kr.jeju.juyoung.tetris.Shape.TetrisBlocks;

public class GameBoard extends JPanel{
	
	/**
	 * 작은 사각형의 모임을 테트리스 블록이라고 하고 
	 * 이 테트리스 블록을 이루는 작은 사각형을 블록이라 한다.
	 */

	//가상 위치 정보
	/**
	 * 가상 블록 필드의 길이 - 10개의 블록이 들어간다.
	 */
	final int BoardWidth = 10;
	
	/**
	 * 가상 블록 필드의 높이 - 22개의 블록이 들어간다.
	 */
	final int BoardHeight = 22;
	
	//게임 컨트롤 변수
	/**
	 * 주기적인 이벤트를 실행하는 타이머
	 */
	Timer timer;
	
	/**
	 * 테트리스 블록이 바닥 혹은 다른 블록에 대한 충돌 판정 단, 좌우 충돌은 아니다.
	 */
	boolean isFallingFinised = false;
	
	/**
	 * 게임의 시작 상태
	 */
	boolean isStarted = false;
	
	/**
	 * 게임의 정지 요청 상태
	 */
	boolean isPaused = false;
	
	/**
	 * 조작이 가능한 블록의 X 좌표
	 */
	int locationX = 0;
	
	/**
	 * 테트리스 블록의 형태 정보와 현재 테트리스 블록의 정보를 가지고 있다.
	 */
	Shape blockShape;
	
	/**
	 * 테트리스 블록을 이름으로 정의 하는 enum
	 */
	TetrisBlocks[] board;
	
	/**
	 * 조작이 가능한 블록의 Y 좌표
	 */
	int locationY = 0;
	
	/**
	 * 게임 시작 - 게임 정보 초기화
	 */
	public void start() {
		
		
	}

}
