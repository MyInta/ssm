package testUtil;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

public class test {
	
	@Test
	public void test01() {
		String str = "wyt15914452386@163.com";//d3l0MTU5MTQ0NTIzODZAMTYzLmNvbQ==
//		String str = "wangyi2018";//172.17.196.254//d2FuZ3lpMjAxOA==
		//����
		byte[] encoderBytes=Base64.encodeBase64(str.getBytes());
		String encoder=new String(encoderBytes);
		System.out.println("commonsCode���ܣ�"+encoder);

		//����
		byte[] decoderBytes=Base64.decodeBase64(encoder);
		String decoder=new String(decoderBytes);
		System.out.println("commonsCode���ܺ�"+decoder);
	}
}
