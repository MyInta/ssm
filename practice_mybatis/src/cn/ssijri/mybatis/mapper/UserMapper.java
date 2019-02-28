package cn.ssijri.mybatis.mapper;

import java.util.List;

import cn.ssijri.mybatis.po.User;
import cn.ssijri.mybatis.po.UserCustom;
import cn.ssijri.mybatis.po.UserQueryVo;

public interface UserMapper {
    //����id��ѯ�û���Ϣ
    public User findUserById(int id) throws Exception;

    //�����û����в�ѯ�û��б�
    public List<User> findUserByName(String name) throws Exception;

    //����û���Ϣ
    public void insertUser(User user) throws Exception;

    //ɾ���û���Ϣ
    public void deleteUser(int id) throws Exception;

    //�����û�
    public void updateUser(User user) throws Exception;

    //�û���Ϣ�ۺϲ�ѯ
    public List<UserCustom> findUserList(UserQueryVo userQueryVo) throws Exception;

    //�û���Ϣ�ۺϲ�ѯ����
    public int findUserCount(UserQueryVo userQueryVo) throws Exception;

    //����id��ѯ�û���Ϣ��ʹ��resultMap���
    public User findUserByIdResultMap(int id) throws Exception;
}
