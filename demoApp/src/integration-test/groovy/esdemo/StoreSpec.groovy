package esdemo

import grails.plugins.elasticsearch.ElasticSearchAdminService
import grails.plugins.elasticsearch.ElasticSearchContextHolder
import grails.plugins.elasticsearch.ElasticSearchService
import grails.plugins.elasticsearch.mapping.SearchableClassMapping
import grails.testing.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class StoreSpec extends Specification {

    @Autowired
    ElasticSearchService elasticSearchService
    @Autowired
    ElasticSearchAdminService elasticSearchAdminService
    @Autowired
    ElasticSearchContextHolder elasticSearchContextHolder


    void "default mapping, should index all properites of domain class"() {

        setup:
        Store store = new Store(name: 'Eltern-Elternelement', owner: 'Horst')

        when:
        elasticSearchService.index(Store)
        elasticSearchAdminService.refresh(Store)
        SearchableClassMapping storeMapping = elasticSearchContextHolder.getMappingContextByType(Store)

        then:
        storeMapping.propertiesMapping*.grailsProperty*.name.containsAll(['name', 'description', 'owner'])

        cleanup:
        elasticSearchService.unindex(Store)
        Store.deleteAll()
    }

}
