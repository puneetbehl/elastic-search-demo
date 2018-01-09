package esdemo

import grails.plugins.elasticsearch.ElasticSearchService
import grails.transaction.Transactional
import org.elasticsearch.index.query.BoolQueryBuilder
import org.elasticsearch.index.query.MatchQueryBuilder
import org.elasticsearch.index.query.QueryBuilders
import org.elasticsearch.index.query.SimpleQueryStringBuilder

@Transactional
class QueryTimeBoostingService {

    ElasticSearchService elasticSearchService

    def queryTimeBoostOnBool() {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.termQuery("fullName", "test").boost(2.0f))
                .should(QueryBuilders.termQuery("firstName", "test"))
        println(queryBuilder)
        elasticSearchService.search(queryBuilder)
    }

    def queryTimeBoostOnTermUsingClosure() {

        elasticSearchService.search({
            term(fullName: 'test', boost: 2)
        }, null, [indices: 'esdemo', types: User])

    }


    def queryTimeBoostSimpleQueryString() {
        SimpleQueryStringBuilder queryStringBuilder = QueryBuilders.simpleQueryStringQuery("test").field("fullName").boost(2.0f)
        println(queryStringBuilder)
        elasticSearchService.search(queryStringBuilder);
    }

    def queryTimeBoostSimpleQueryStringParserTest() {
        SimpleQueryStringBuilder queryStringBuilder = QueryBuilders.simpleQueryStringQuery("test").field("fullName").field("firstName^2")
        println(queryStringBuilder)
        elasticSearchService.search(queryStringBuilder);
    }

    def queryTimeBoostOnMatch() {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("fullName", "test").boost(2.0f)
        println(matchQueryBuilder)
        elasticSearchService.search(matchQueryBuilder)
    }

    def queryTimeBoostOnMatchUsingClosure() {
        elasticSearchService.search({
            match {
                firstName(query: 'test', boost: 2)
            }
        }, null, [indices: 'esdemo', types: User])
    }


}
