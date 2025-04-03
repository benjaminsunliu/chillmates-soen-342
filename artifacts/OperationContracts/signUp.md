# Operation Contract for signUp(...)

- **Contract** : signUp
- **Operation**: signUp()
- **Cross reference(s)**: Use Case User sign up
- **Preconditions**:
    - User must not exist in the database
    - email, password, affiliation, contact and intent must be provided
- **Postconditions**:
    - A `User` instance was created [Instance Creation]
    - The `User`'s "status" attribute is set to "Pending" [Attribute Modification]
    - The new `User` is added to `UserCatalog` [Attribute Modification]
    - The new `User` is now associated with the `UserCatalog`, ensuring that it is retrievable when needed [Association Formed]