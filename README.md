# web3j-example
A simple android implementation of Ethereum wallet and smart contracts interaction. 

## Getting started 
First, add the following dependencies to your project:

### Maven
~~~xml
<dependency>
  <groupId>org.web3j</groupId>
  <artifactId>core-android</artifactId>
  <version>2.2.1</version>
</dependency>
~~~
### Gradle 
~~~gradle
compile ('org.web3j:core-android:2.2.1')
~~~ 
### Run geth (or another) client 
This will run a private network. You may also run it with ```--testnet``` flag.
~~~shell
$ geth --dev --rpc --rpcapi "eth,net,web3,personal"  --rpcaddr="localhost" --rpcport="8545" --rpccorsdomain="*"
~~~ 
