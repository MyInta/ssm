package cn.ssijri.mybatis.po;

import java.util.List;

public class UserQueryVo {

    //在这里包装所需要的查询条件

	private List<Integer> ids;
    //用户查询条件
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
