# Thrift

* struct
* enum
* exception
* service
    * interface between C/S
* typedef
* const
* namespace [language] [path]
    * package in java
* include
    * import in java
* required / optional. optional is preferred.

## Protocol

* TBinaryProtocol   slow
* TCompactProtocol (rec) fast
* TJSONProtocol
* TSimpleJSONProtocol   no meta-data, write-only
* TDebugProtocol    txt format, easy to debug

## Transport

* TSocket   slow
* TFramedTransport (rec)    divide data into small chunk of frames
* TFileTransport
* TMemoryTransport
* TZlibTransport

## Server

* TSimpleServer     one thread server. testing
* TThreadPoolServer     multi-threaded server. Blocking I/O
* TNonblockingServer    multi-threaded non-blocking I/O. pair with TFramedTransport
* THsHaServer (rec)     Extension to TNonblockingServer. pair with TFramedTransport 
    * Half-sync-half-async. Read/Write with thread pool, half-sync with synchronizing RPC

## Attempt with Python

