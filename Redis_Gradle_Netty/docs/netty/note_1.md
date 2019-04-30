# Netty Note 1

## Index

1. Intro
2. Structure
3. Module
4. HTTP Tunnel
5. Socket
6. Zip and un-zip
7. WebSocket
    1. implementation and theory
    2. Connection and Life cycle
    3. Server / Client
    4. heartbeat
8. RPC
    1. Google Protobuf
        1. Serializing structured data
    2. Apache Thrift
9. Transfer big file
10. TCP package
11. NIO implementation in netty and java
12. encoding / decoding
13. Major class and interface source code
14. Channel
15. Serialized

## Intro

1. Asynchronous. Callback. Not sure if it's success or not. With Listener.
2. Event-driven. Based on different protocol, let developer to define callback
3. Network. Many frameworks are based on netty
4. For rapid development of maintainable high performance protocol server/client
5. NIO, FTP, SMTP, HTTP, binary and text protocols
6. Key Points
    1. Unified API
    2. Flexible and extensible event model
    3. Zero-Copy-Capable Rich Byte Buffer
7. Customizable thread model including single and SEDA(Staged Event Driven Architecture)
    1. SEDA, define different thread amount for different event

