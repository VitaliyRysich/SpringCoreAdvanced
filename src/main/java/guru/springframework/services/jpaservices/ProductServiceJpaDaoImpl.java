package guru.springframework.services.jpaservices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by jt on 12/9/15.
 */
@Service
@Profile("jpadao-dontuse")
public class ProductServiceJpaDaoImpl extends AbstractJpaDaoService implements ProductService {
    private ProductFormToProduct productFormToProduct;
    private ProductToProductForm productToProductForm;

    @Autowired
    public void setProductFormToProduct(ProductFormToProduct productFormToProduct) {
        this.productFormToProduct = productFormToProduct;
    }

    @Autowired
    public void setProductToProductForm(ProductToProductForm productToProductForm) {
        this.productToProductForm = productToProductForm;
    }

    @Override
    public List<Product> listAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product getById(Integer id) {
        EntityManager em = emf.createEntityManager();

        return em.find(Product.class, id);
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product savedProduct = em.merge(domainObject);
        em.getTransaction().commit();

        return savedProduct;
    }

    @Override
    public void delete(Integer id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
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
