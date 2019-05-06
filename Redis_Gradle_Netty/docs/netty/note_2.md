# Git and ProtoBuf

Git as version control  

## Method 1

* Protobuf-java: code after protoc would be pushed into here
* ServerProject:  
    * git submodule: git repo inside of repo
* ClientProject:  
    * git submodule

* branch:
    * develop (merge into test)
    * test (merge into master) (same env as master, may be weaker machine)
    * master
* too many branch may cause problem when pushing protobuf
* edit middleware in ourter project may cause problems

## Method 2 recommend

* git subtree
    * Basically just merge two project's code together

## Method 3

* jar