package esdemo

class Category {

    Date createdDate
    Calendar lastUpdated
    Set<String> names

    static transients = ['lastUpdated' , 'names']
    static searchable = {
        only = ['lastUpdated', 'createdDate' , 'names']
    }
}
