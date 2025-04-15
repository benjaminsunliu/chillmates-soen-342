# Operation Contract for viewObjects(..)

- **Contract** : viewObjects
- **Operation**: viewObjects()
- **Cross reference(s)**: Use Case View Institution Information
- **Preconditions**:
    - User must be logged into the system
- **Postconditions**:
    - System retrieves and compiles a list of available `ObjectOfInterest` from the `ObjectCatalog` [Modification of Variable]
    - Temporary association is established between the user session and the retrieved objects to display the list [Association Formed]