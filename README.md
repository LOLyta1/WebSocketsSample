# Descriprion
Client for receiving Crypto order book. 
Consists of both REST Client and WebSocket client.

# Links
### Android tools
WebSocket library: https://github.com/Tinder/Scarlet

### Useful documentation
API documentation: https://binance-docs.github.io/apidocs/#change-log/ 

API Key setup: https://binance-docs.github.io/apidocs/spot/en/#introduction

How test Binance REST in Postman: https://academy.binance.com/en/articles/binance-api-series-pt-1-spot-trading-with-postman

How to manage a local order book correctly - https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream 

WebSocket request to subscribe on order book stream: https://binance-docs.github.io/apidocs/spot/en/#diff-depth-stream

# Dependency for Scarlet

implementation "com.tinder.scarlet:scarlet:0.1.11"

implementation "com.tinder.scarlet:websocket-okhttp:0.1.9"

implementation "com.tinder.scarlet:message-adapter-gson:0.1.9"

implementation "com.tinder.scarlet:stream-adapter-coroutines:0.1.9"
