package esdemo

class QueryTimeBoostingController {

    QueryTimeBoostingService queryTimeBoostingService
    def test1() {
        render queryTimeBoostingService.queryTimeBoostOnBool()
    }

    def test2() {
        render queryTimeBoostingService.queryTimeBoostOnTermUsingClosure()
    }


    def test3() {
        render queryTimeBoostingService.queryTimeBoostSimpleQueryString()
    }

    def test4() {
        render queryTimeBoostingService.queryTimeBoostSimpleQueryStringParserTest()
    }

    def test5() {
        render queryTimeBoostingService.queryTimeBoostOnMatch()
    }

    def test6() {
        render queryTimeBoostingService.queryTimeBoostOnMatchUsingClosure()
    }
}
