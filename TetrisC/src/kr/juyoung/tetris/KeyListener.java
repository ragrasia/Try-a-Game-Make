package kr.juyoung.tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 * ��Ʈ�������� ���Ǵ� Ű �Է¿� ���� �̺�Ʈ�� ���� �⺻������ �޴� ȭ�鿡 ���� �Է��� �⺻ �����̴�. setKeyListener��
 * �̿��Ͽ� ������ �����ϴ�.
 * 
 * @author hio
 * 
 */
public class KeyListener extends KeyAdapter {

	final static int SET_MENU = 1;
	final static int SET_GAME = 2;

	private int keySetting = SET_MENU;

	/**
	 * type�� ���� �Է� �޴� Ű�� ó������� ���Ѵ�.
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
