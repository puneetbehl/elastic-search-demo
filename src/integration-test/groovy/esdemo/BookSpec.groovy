package esdemo

import grails.plugins.elasticsearch.ElasticSearchAdminService
import grails.plugins.elasticsearch.ElasticSearchService
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class BookSpec extends Specification {

    @Autowired
    ElasticSearchService elasticSearchService
    @Autowired
    ElasticSearchAdminService elasticSearchAdminService

    void "should double the score of search hit when 'Elasticsearch` is found in title field as compared to content field"() {
        setup:
        List<Book> books =[]
        books << new Book(title: "Elasticsearch Introduction", content: "basic introduction", datePublished: new Date()).save(flush: true, failOnError: true)
        books << new Book(title: "Hello World", content: "Elasticsearch", datePublished: new Date()).save(flush: true, failOnError: true)

        when:
        elasticSearchService.index(Book)
        elasticSearchAdminService.refresh()
        def results = Book.search("Elasticsearch", [score: true])

        then:
        results.scores["1"] == 2 * results.scores["2"]

        cleanup:
        Book.deleteAll()
    }
}
