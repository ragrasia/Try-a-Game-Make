package kr.juyoung.tetris.exception;

public class KeyTypeException extends Exception{

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "key type error 설정 되어 있는 키 입력에 대한 처리가 등록되어 있지 않습니다.";
	}
}
