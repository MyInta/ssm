package cn.ssijri.ssm.controller.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

/**
 * 
 * @author ����
 *
 */
public class CustomDateConverter implements Converter<String,Date>{

	@Override
	public Date convert(String source) {
		
		
		//ʵ�����ڴ�ת��Ϊ�������ͣ�yyyy-MM-dd HH:mm:ss��
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			return simpleDateFormat.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//��ʧ���򷵻�null
		return null;
	}

}
