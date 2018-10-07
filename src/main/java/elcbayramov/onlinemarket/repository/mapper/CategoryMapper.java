package elcbayramov.onlinemarket.repository.mapper;

import elcbayramov.onlinemarket.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("Select * from shopping.category where is_active=1 and id = #{id}")
    Category getCatagoryById(Long id);

    @Select("Select * from shopping.category where is_active=1")
    List<Category> getActiveCategory();

    @Select("Select * from shopping.category")
    List<Category> getAllCategory();
}
