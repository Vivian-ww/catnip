package cn.tedu.user.mapper;

import com.jt.common.pojo.User;

public interface UserMapper {

	Integer queryUserName(String userName);

	void userSave(User user);

	User queryUserByNameAndPassword(User user);
}
