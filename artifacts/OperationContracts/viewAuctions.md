# Operation Contract for viewAllAuctions(..)

- **Contract** : View Auctions
- **Operation**: viewAllAuctions()
- **Cross reference(s)**: Client's view auctions use case
- **Preconditions**:
    - User must be logged into the system
- **Postconditions**:
    - System retrieves and compiles a list of available `Auction` from the `AuctionCatalog` [Modification of Variable]
    - Temporary association is established between the user session and the retrieved auctions to display the list [Association Formed]