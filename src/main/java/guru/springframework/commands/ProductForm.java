package guru.springframework.commands;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductForm {
    private Integer productId;
    private Integer productVersion;

    @NotEmpty
    @Size(min = 5, max = 200)
    private String productDescription;

    @NotNull
    @Min(0)
    @Max(5000)
    private BigDecimal productPrice;

    @NotEmpty
    @URL
    private String productImageUrl;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductVersion() {
        return productVersion;
    }

    public void setProductVersion(Integer productVersion) {
        this.productVersion = productVersion;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(String productImageUrl) {
        this.productImageUrl = productImageUrl;
    }
}
