# Operation Contract for addObject(..)

- **Contract** : addObject
- **Operation**: addObject(title: String, description: String, type: String, isOwned: Boolean)
- **Cross reference(s)**: Use Case Add Object
- **Preconditions**:
    - The Administrator must be logged into the system
    - The provided title and description must be non-empty
    - The object type must be a valid category (e.g. manuscript, painting, ceramics, etc.)
    - The ownership of the object by the institution must be explicitly defined (true/false)
- **Postconditions**:
    - A new `ObjectOfInterest` is instantiated with the given attributes [Instance Creation]
    - The new object is added to the `ObjectCatalog` [Modification of Variable]
    - The newly created `ObjectOfInterest` is associated with the `ObjectCatalog`, ensuring it is retrievable when needed [Association Formed]
