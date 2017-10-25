# Data Access Objects (DAOs)

The DAO classes in this directory provide the interface between the rest of the server, and the
database. While none of these currently follow a strict format enforced by an abstract super-class,
or interface, each DAO object should have at least one `get` or `select` method, an `update` method, 
an `insert` method, a `delete` method, an `openConnection`, and a `closeConnection`.

The `openConnection` method must be called before any calls to the database can be made, 
and `closeConnection` must be called when you are finished making calls to the database.

_Note: These objects probably need to be refactored._