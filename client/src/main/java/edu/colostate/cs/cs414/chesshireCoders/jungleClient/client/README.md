# jungleClient.client

## JungleClient.java
This is a wrapper class for the KryoNet Client class. 
It contains a few key methods for starting and stopping the client,
as well as sending/receiving messages to the server.

###  `start()`
This one is simple enough, it starts the client. Or rather, 
it starts the thread that sends and receives messages. Call this
before calling `connect()`

### `connect(int timeoutMillis, String hostname, int tcpPort)`
This method will connect the client to the given server. If the connection is
not established after `timeoutMillis` milliseconds, the client will give up.

### `stop()`
Stops the client. The client will no longer send or receive messages.

### `reconnect()`
Attempts to reconnect to the server using the arguments used in the previous call to
`connect()`.

### `sendMessageExpectsResponse(Object, Listener)`
This method will send the `Object` to the server, and register the `Listener` with the client. 
The specified listener should listen for the appropriate response the object sent.

For example, if an object of type `LoginRequest` is sent, the listener should listen for
an object of type `LoginResponse`

The recommended usage of this method looks something like this:
```java
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import edu.colostate.cs.cs414.chesshireCoders.jungleClient.client.JungleClient;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.listeners.FilteredListener;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.requests.RegisterRequest;
import edu.colostate.cs.cs414.chesshireCoders.jungleNetwork.responses.RegisterResponse;

RegisterRequest request = new RegisterRequest(pw, email, nickname, "nameFirst", "nameLast");

registerResponseListener = new Listener.ThreadedListener(new FilteredListener<RegisterResponse>(RegisterResponse.class) {
    @Override
    public void run(Connection connection, RegisterResponse received) {
       
        client.removeListener(registerResponseListener);

        if (received.getRegistrationSuccess()) { // I'll add some getters and setters in my PR, currently everything is package private
            registerSuccess();
        } else {
            registerFailure();
        }

    }
});

client.sendMessageExpectsResponse(request, registerResponseListener);
``` 

You can see that this the defined listener will remove itself from the client when it is executed
so that you handling code is only executed once.

### `sendMessage(Object o)`
This just sends an object to the server. The object should be of a type the server is listening
for, else it will be ignored by the server.

### `addListener(Listener)`
Adds a new listener to the client. It is recommended that you use a
`FilteredListener` wrapped in a `ThreadedListener`. This way, the listener will only execute
it's handling code when it receives a specific object type, and the code will be executed in 
a seperate thread (this prevents the clients `update()` thread from being blocked).

### `removeListener(Listener)`
Removes and added listener from the client. That listener will no longer be called.

## NetworkListener.java
This class registers a number of event listeners with the client. The event listeners
should not be removed as they handle asynchronous events from the server 
(such as a new invitation).