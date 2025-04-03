# Operation Contract for login(..)

- **Contract** : login
- **Operation**: login(email: String, password:String)
- **Cross reference(s)**: Use Case Login
- **Preconditions**:
    - `email` and `password` must be provided as valid non-empty strings
    - The `UserCatalog` must be initialized and contain registered users
- **Postconditions**:
    - Successful login associates the `User` with an active session, granting access based on `User` role association formed between `User` and `Institution` system