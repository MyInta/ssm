package cn.ssijri.mybatis.po;

import java.util.List;

public class UserQueryVo {

    //�������װ����Ҫ�Ĳ�ѯ����

	private List<Integer> ids;
    //�û���ѯ����
    private UserCustom userCustom;

    public UserCustom getUserCustom() {
        return userCustom;
    }

	public void setUserCustom(UserCustom userCustom) {
		this.userCustom = userCustom;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}
