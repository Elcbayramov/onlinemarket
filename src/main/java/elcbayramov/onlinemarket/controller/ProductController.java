package elcbayramov.onlinemarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import elcbayramov.onlinemarket.model.Image;
import elcbayramov.onlinemarket.model.Product;
import elcbayramov.onlinemarket.repository.ImageRepository;
import elcbayramov.onlinemarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    private static final String UPLOADED_FOLDER = "C:\\Users\\Tamagafk\\Desktop\\ecommerce\\src\\main\\resources\\upload\\";

    /**
     * @return return all registered products in the system.
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> productList = (List<Product>) productRepository.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    /**
     * @param id - user id
     * @return - user entity
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) throws EntityNotFoundException {
        Product product = productRepository.findOne((long) id);
        if (product.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    /**
     *
     * @param category_id - get products category id
     * @return - get all products in the category
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, value = "/category/{category_id}")
    public ResponseEntity<?> getProductsOfCategory(@PathVariable long category_id) throws EntityNotFoundException {
        List<Product> productList = productRepository.findByCategoryId(category_id);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }



    /**
     *
     * @param files - product's image or images type multipart
     * @param strPojo - standart product json request
     * @return - get all products in the system
     * @throws EntityNotFoundException
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public ResponseEntity<List<Product>> addProduct(@RequestParam(value = "file") MultipartFile[] files,
                                                    @RequestParam(value = "product") String strPojo) throws EntityNotFoundException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        Product product = mapper.readValue(strPojo, Product.class);
        productRepository.save(product);

        for (MultipartFile file : files) {

            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);

            Image image = new Image(file.getOriginalFilename(), path.toString(), product);
            imageRepository.save(image);
        }

        List<Product> productList = (List<Product>) productRepository.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }


    /**
     * @param product - passing editable product object
     * @param id - the id of edit product object
     * @return - edited product object
     * @throws EntityNotFoundException
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<List<Product>> updateProduct(@RequestBody Product product, @PathVariable long id) throws EntityNotFoundException {
        Product product1 = productRepository.findOne(id);
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setStock(product.getStock());
        productRepository.save(product1);
        System.out.println(product1);
        List<Product> productList = (List<Product>) productRepository.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    /**
     * @param id -  product id
     * @return - HTTP OK - deleted product object
     * @throws EntityNotFoundException - does not exits product object
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) throws EntityNotFoundException {
        productRepository.delete((long) id);
        List<Product> productList = (List<Product>) productRepository.findAll();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

}