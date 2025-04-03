# Operation Contract for register() for clients

- **Contract** : register client
- **Operation**: register()
- **Cross reference(s)**:Client's SignUp use case
- **Preconditions**:
    - input credentials (email) must not exist
    - input must be non-empty
- **Postconditions**:
    - New `Client` user is created [Instance Creation]
    - New object `Client` is added to the `UserCatalog`
    - New `User` object `Client` is associated with `UserCatalog`. [Association Formed]