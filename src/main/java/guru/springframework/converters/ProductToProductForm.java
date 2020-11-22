package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductForm implements Converter<Product, ProductForm> {
    @Override
    public ProductForm convert(Product product) {
        ProductForm productForm = new ProductForm();
        productForm.setProductId(product.getId());
        productForm.setProductDescription(product.getDescription());
        productForm.setProductPrice(product.getPrice());
        productForm.setProductImageUrl(product.getImageUrl());
        productForm.setProductVersion(product.getVersion());
        return productForm;
    }
}
