package elcbayramov.onlinemarket.repository.mapper;

import elcbayramov.onlinemarket.model.Category;
import elcbayramov.onlinemarket.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface ProductMapper {

    @Results(value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "category",column = "category_id",javaType = Category.class,
            many = @Many(select = "elcbayramov.onlinemarket.repository.mapper.CategoryMapper.getCatagoryById")),
            @Result(property = "images",column = "id",javaType = Set.class,
                    many = @Many(select = "elcbayramov.onlinemarket.repository.mapper.ImageMapper.getByProductId"))
    })
    @Select("Select * from shopping.product where is_active=1 and id = #{id}")
    Product getProductByid(Long id);


    @Select("Select * from shopping.product where is_active=1 ")
    List<Product> getActiveProduct();

    @Select("Select * from shopping.product ")
    List<Product> getAllProduct();
}