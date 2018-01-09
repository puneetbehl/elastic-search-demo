package esdemo

class Book {
    String title
    String content
    String datePublished

    static searchable = {
        title boost: 2.0
        content boost: 1.0
    }
}