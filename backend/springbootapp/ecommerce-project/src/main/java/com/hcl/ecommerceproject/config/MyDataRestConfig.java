
package com.hcl.ecommerceproject.config;

import com.hcl.ecommerceproject.entity.Product;
import com.hcl.ecommerceproject.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {


    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);
        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};


        config.getExposureConfiguration()
            .forDomainType(Product.class)
            .withItemExposure((metadata, HttpMethods)->HttpMethods.disable(theUnsupportedActions))
            .withCollectionExposure((metadata, HttpMethods)->HttpMethods.disable(theUnsupportedActions));


        config.getExposureConfiguration()
            .forDomainType(ProductCategory.class)
            .withItemExposure((metadata, HttpMethods)->HttpMethods.disable(theUnsupportedActions))
            .withCollectionExposure((metadata, HttpMethods)->HttpMethods.disable(theUnsupportedActions));

    }

 

}