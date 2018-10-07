package elcbayramov.onlinemarket.repository.mapper;

import elcbayramov.onlinemarket.model.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

@Mapper
public interface ImageMapper {

    @Select("Select * from shopping.image product_id = #{productId}")
    Set<Image> getByProductId(Long productId);





}