# jungleNetwork

_Note: This package should be renamed. `jungleUtil` maybe?_

This package contains classes that are shared between the client and the server

# jungleNetwork.requests
This package contains the Request classes. Requests are sent **from the client to the server**.

# jungleNetwork.responses
This objects are sent **by the server** in _response to_ request objects received by the server.

# jungleNetwork.types
A set of type definitions. Mostly enumerations.

Example: `PlayerColor`

# jungleNetwork.events
Event objects are sent **from the server to the client** and the client should (almost) always
be listening for these. These represent events that occurred on different clients, or the server,
that affects the receiving client.

# jungleNetwork.listeners
Some various listener classes to be used by either the server or client. 