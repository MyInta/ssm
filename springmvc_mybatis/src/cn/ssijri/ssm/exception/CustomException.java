package cn.ssijri.ssm.exception;

/**
 * �Զ����쳣��,���Ԥ�ڵ��쳣����Ҫ�ڳ������׳������쳣
 * @author ����
 *
 */
public class CustomException extends Exception{
	
	//�쳣��Ϣ
	public String message;
	
	public CustomException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}