package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductFormToProduct implements Converter<ProductForm, Product> {
    @Override
    public Product convert(ProductForm productForm) {
        Product product = new Product();
        product.setId(productForm.getProductId());
        product.setDescription(productForm.getProductDescription());
        product.setPrice(productForm.getProductPrice());
        product.setVersion(productForm.getProductVersion());
        product.setImageUrl(productForm.getProductImageUrl());
        return product;
    }
}
