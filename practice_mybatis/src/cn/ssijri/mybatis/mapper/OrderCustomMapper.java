package cn.ssijri.mybatis.mapper;

import java.util.List;

import cn.ssijri.mybatis.po.Orders;
import cn.ssijri.mybatis.po.OrdersCustom;

public interface OrderCustomMapper {
    //��ѯ����������ѯ�û���Ϣ
    public List<OrdersCustom> findOrdersUser() throws Exception;
    //��ѯ����������ѯ�û�ʹ��resultMap
    public List<Orders> findOrdersUserResultMap() throws Exception;
    //��ѯ����(�����û�)��������ϸ
    public List<Orders> findOrdersAndOrderDetailResultMap() throws Exception;
    //��ѯ����������ѯ�û����û���Ϣ���ӳټ���
    public List<Orders> findOrdersUserLazyLoading() throws Exception;
}