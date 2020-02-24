package com.jt.common.utils;

public class PrefixUtils {
	//定义业务常量的前缀---避免在代码中出现字符串，减少耦合
	//商品查询
	public static final String PRODUCT_QUERY_PREFIX = "prod_query_";
	public static final String PRODUCT_REDISLOCK_PREFIX = "prod_update_";
	//用户相关
	public static final String USER_TICKET_PREFIX = "EM_TICKET";
	public static final String USER_LOGINTICKETS_PREFIX = "user_logined_tickets_";
}
