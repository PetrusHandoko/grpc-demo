###Sample Simple GRPC Server 

This provides end to end example how the Java connected to C++ server

### This example is from the published example of C++ gprc source
Github repository: https://github.com/grpc/grpc

### Installation

Install the GRPC follow the instruction from
https://github.com/grpc/grpc/tree/master/src/cpp/README.md
https://github.com/grpc/grpc/blob/master/BUILDING.md

Follow these instructions to install user info service.
In here, we added Booster logging package
Note: if booster is not installed: 
  `$ apt-get install libboost-all-dev`

Compiled the new helloworld.proto files that store the userservice definition with this command
`
$ protoc -I ../../protos/ --grpc_out=. --plugin=protoc-gen-grpc=/usr/local/bin/grpc_cpp_plugin ../../protos/helloworld.proto
$ protoc -I ../../protos/ --cpp_out=. ../../protos/helloworld.proto
$ make demo_server
`



