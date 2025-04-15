# Operation Contract for

- **Contract** : addAuction
- **Operation**: addAuction(auctionType, auctionHouse, isOnline, timeSlot, objectOfInterest)
- **Cross reference(s)**: Use Case addAuction
- **Preconditions**:
  - The user must be logged in as an Admin
  - The auctionType, auctionHouse, isOnline, timeSlot, and objectOfInterest must be provided
- **Postconditions**:
  - An `auction` instance was created with a unique ID [Instance Creation]
  - The new `auction` is associated with an `AuctionHouse` [Association formed]
  - The new `auction` is associated with a `TimeSlot` [Association formed]
  - The new `auction` is associated with an `ObjectOfInterest` [Association formed]