package esdemo

class Store {

    String name
    String description = "A description of a store"
    String storeOwner = "Owner of the store"

    static searchable = true

    static constraints = {
        name blank: false
        description nullable: true
        storeOwner nullable: true
    }

    static mapping = {
        autoImport(false)
    }

    public String toString() {
        name
    }
}