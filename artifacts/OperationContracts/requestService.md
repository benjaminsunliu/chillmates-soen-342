# Operation Contract for requestService

- **Contract** : createServiceRequest
- **Operation**: createServiceRequest(requestType, expert, client, timeslot)
- **Cross reference(s)**: Use Case New Service Request
- **Preconditions**:
    - Expert must exist
    - Client must exist
    - requestType, expert, client and timeslot must be provided
    - Expert must be available during the provided time slot
    - Client must not have an existing request within the provided time slot
    - The requestType must be a valid type
- **Postconditions**:
    - A `serviceRequest` instance was created with a unique ID [Instance Creation]
    - The new `serviceRequest` is associated with an `Expert` [Association formed]
    - The new `serviceRequest` is associated with a `Client` [Association formed]
    - The new `serviceRequest` is associated with a `TimeSlot` [Association formed]