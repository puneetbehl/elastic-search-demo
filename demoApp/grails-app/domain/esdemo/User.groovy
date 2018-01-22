package esdemo

class User {

    String firstName
    String lastName

    static transients = ['fullName']
    static searchable = {
        // This will work if includeTransients: false(default)
        only = ['fullName', 'firstName', 'lastName']
        fullName boost: 2.0
    }

    String getFullName() {
        return firstName + " " + lastName
    }
}
