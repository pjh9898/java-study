package kr.co.hanbit.prroduct.management.service;

import kr.co.hanbit.prroduct.management.domain.Product;
import kr.co.hanbit.prroduct.management.dto.ProductDto;
import kr.co.hanbit.prroduct.management.repository.DatabaseProductRepository;
import kr.co.hanbit.prroduct.management.repository.ListProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleProductService {

    private final ModelMapper modelMapper;
//    private ListProductRepository listProductRepository;
    private DatabaseProductRepository databaseProductRepository;
    private ValidationService validationService;

    @Autowired
    SimpleProductService(DatabaseProductRepository databaseProductRepository, ModelMapper modelMapper, ValidationService validationService) {
        this.databaseProductRepository = databaseProductRepository;
        this.modelMapper = modelMapper;
        this.validationService = validationService;;
    }

    public ProductDto add(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        validationService.checkValid(product);

        Product savedProduct = databaseProductRepository.add(product);

        return modelMapper.map(savedProduct, ProductDto.class);
    }

    public ProductDto findById(Long id) {
        Product product = databaseProductRepository.findById(id);
        return modelMapper.map(product, ProductDto.class);
    }

    public List<ProductDto> findAll() {
        List<Product> products = databaseProductRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public List<ProductDto> findByName(String name) {
        List<Product> products = databaseProductRepository.findByName(name);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    public ProductDto update(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product updatedProduct = databaseProductRepository.update(product);
        return modelMapper.map(updatedProduct, ProductDto.class);
    }

    public void delete(Long id) {
        databaseProductRepository.delete(id);
    }
}