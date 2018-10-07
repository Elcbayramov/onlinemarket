package elcbayramov.onlinemarket.repository.mapper;

import elcbayramov.onlinemarket.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("Select * from shopping.user where is_active=1 and id = #{id}")
    User getUserByid(Long id);

    @Select("Select * from shopping.user where is_active=1")
    User getActiveUsers(Long id);

    @Select("Select * from shopping.user")
    User getAllUser(Long id);
}