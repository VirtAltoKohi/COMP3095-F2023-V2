package ca.gbc.productservice.service;

import ca.gbc.productservice.dto.ProductRequest;
import ca.gbc.productservice.dto.ProductResponse;
import ca.gbc.productservice.model.Product;
import ca.gbc.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void createProduct(ProductRequest productRequest) {

        log.info("Creating a new product {}", productRequest.getName());

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);

        log.info("product {} is saved", product.getId());

    }

    @Override
    public String updateProduct(String productId, ProductRequest productRequest) {

        log.info("Updating a product with Id {}", productId);

        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(productId));
        Product product = mongoTemplate.findOne(query, Product.class);

        if (product != null) {
            product.setName(productRequest.getName());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());

            log.info("Product {} is updated", product.getId());
            return productRepository.save(product).getId();

        }

        return productId.toString();

    }

    @Override
    public void deleteProduct(String productId) {

        log.info("Product {} is deleted", productId);
        productRepository.deleteById(productId);

    }

    @Override
    public List<ProductResponse> getAllProducts() {

        log.info("Returning a list of products");

        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductReponse).toList();
    }

    private ProductResponse mapToProductReponse(Product product) {
        return ProductResponse.builder()
                .Id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
