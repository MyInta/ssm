package cn.ssijri.ssm.po;

public class Orderdetail {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderdetail.id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderdetail.orders_id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    private Integer ordersId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderdetail.items_id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    private Integer itemsId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column orderdetail.items_num
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    private Integer itemsNum;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderdetail.id
     *
     * @return the value of orderdetail.id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderdetail.id
     *
     * @param id the value for orderdetail.id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderdetail.orders_id
     *
     * @return the value of orderdetail.orders_id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public Integer getOrdersId() {
        return ordersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderdetail.orders_id
     *
     * @param ordersId the value for orderdetail.orders_id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderdetail.items_id
     *
     * @return the value of orderdetail.items_id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public Integer getItemsId() {
        return itemsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderdetail.items_id
     *
     * @param itemsId the value for orderdetail.items_id
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public void setItemsId(Integer itemsId) {
        this.itemsId = itemsId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column orderdetail.items_num
     *
     * @return the value of orderdetail.items_num
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public Integer getItemsNum() {
        return itemsNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column orderdetail.items_num
     *
     * @param itemsNum the value for orderdetail.items_num
     *
     * @mbggenerated Fri Sep 07 10:47:25 CST 2018
     */
    public void setItemsNum(Integer itemsNum) {
        this.itemsNum = itemsNum;
    }
}