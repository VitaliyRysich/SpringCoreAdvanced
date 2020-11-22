package guru.springframework.services.reposervices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.Product;
import guru.springframework.repositories.ProductRepository;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile({"springdatajpa", "jpadao"})
public class ProductServiceRepoImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductFormToProduct productFormToProduct;
    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct;
    }

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {
        this.productToProductForm = productToProductForm;
    }

    @Override
    public List<?> listAll() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        return productRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        productRepository.delete(id);
    }


    @Override
    public ProductForm saveOrUpdateProductForm(ProductForm productForm) {
        if(productForm.getProductId() != null) {
            Product productToUpdate = this.getById(productForm.getProductId());

            productToUpdate.setVersion(productForm.getProductVersion());
            productToUpdate.setDescription(productForm.getProductDescription());
            productToUpdate.setVersion(productForm.getProductVersion());
            productToUpdate.setImageUrl(productForm.getProductImageUrl());

            return productToProductForm.convert(this.saveOrUpdate(productToUpdate));
        }
        else {
            return productToProductForm.convert(this.saveOrUpdate(productFormToProduct.convert(productForm)));
        }
    }
}
