
package com.hcl.ecommerceproject.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import com.hcl.ecommerceproject.entity.Product;
import com.hcl.ecommerceproject.entity.ProductCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }


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


        // call an internal helper method to help us expose the id
            exposeIds(config);
            

    }


    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a List of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the eneity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the enity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}