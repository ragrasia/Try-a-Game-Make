package kr.juyoung.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * 테트리스에서 사용되는 키 입력에 대한 이벤트를 정의 기본적으로 메뉴 화면에 대한 입력이 기본 설정이다. setKeyListener를
 * 이용하여 변경이 가능하다.
 * 
 * @author hio
 * 
 */
public class KeyListener extends KeyAdapter {

	final static int SET_MENU = 1;
	final static int SET_GAME = 2;

	private int keySetting = SET_MENU;

	/**
	 * type에 따라 입력 받는 키의 처리방식을 정한다.
	 * 
	 * @param type
	 */
	public void setKeyListenerType(int type) {
		keySetting = type;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		super.keyReleased(e);

		if (keySetting == SET_MENU) {

		} else if (keySetting == SET_GAME) {

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		super.keyTyped(e);
	}

}
