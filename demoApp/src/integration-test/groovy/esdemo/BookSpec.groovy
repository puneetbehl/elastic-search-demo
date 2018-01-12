package esdemo

import grails.plugins.elasticsearch.ElasticSearchAdminService
import grails.plugins.elasticsearch.ElasticSearchService
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import spock.lang.Stepwise

@Stepwise
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
        books << new Book(title: "Hello World", content: "Elasticsearch Introduction", datePublished: new Date()).save(flush: true, failOnError: true)

        when:
        elasticSearchService.index(Book)
        elasticSearchAdminService.refresh()
        def results = Book.search("Elasticsearch", [score: true])

        then:
        results.scores["1"] == 2 * results.scores["2"]

        cleanup:
        elasticSearchService.unindex(Book)
        Book.deleteAll()
    }

    void "dynamic boosting, hits on docs with a certain term should be ranked higher than hits on docs with other terms in the query"() {
        setup:
        List<Book> books =[]
        books << new Book(title: "Elasticsearch Introduction", content: "basic introduction", datePublished: new Date()).save(flush: true, failOnError: true)
        books << new Book(title: "Hello Lucene", content: "Elasticsearch", datePublished: new Date()).save(flush: true, failOnError: true)
        books << new Book(title: "Hello World", content: "Elasticsearch", datePublished: new Date()).save(flush: true, failOnError: true)

        when:
        elasticSearchService.index(Book)
        elasticSearchAdminService.refresh()
        Closure query = {
            bool {
                should {
                    match {
                        title(query: "Elasticsearch", boost: 3.0)
                    }
                }
                should {
                    match {
                        title(query: "Lucene", boost: 2.0)
                    }
                }
            }
        }
        def results = Book.search (query, null, [score: true])

        then:
        (2 * results.scores["3"])?.round(6) == (3 * results.scores["4"])?.round(6)

        cleanup:
        elasticSearchService.unindex(Book)
        Book.deleteAll()
    }

}
