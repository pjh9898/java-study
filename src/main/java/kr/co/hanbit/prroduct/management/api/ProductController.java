package kr.co.hanbit.prroduct.management.api;

import jakarta.validation.Valid;
import kr.co.hanbit.prroduct.management.dto.ProductDto;
import kr.co.hanbit.prroduct.management.service.SimpleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private SimpleProductService simpleProductService;

    @Autowired
    ProductController(SimpleProductService simpleProductService) {
        this.simpleProductService = simpleProductService;
    }

    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        return simpleProductService.add(productDto);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    public ProductDto findProductById(@PathVariable Long id) {
        return simpleProductService.findById(id);
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<ProductDto> findProducts(@RequestParam(required = false) String name) {
        if (name == null)
            return simpleProductService.findAll();
        return simpleProductService.findByName(name);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        productDto.setId(id);
        return simpleProductService.update(productDto);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    public void updateProduct(@PathVariable Long id) {
        simpleProductService.delete(id);
    }
}
