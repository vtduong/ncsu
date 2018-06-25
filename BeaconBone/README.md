# BeaconBone

[Developer Documentation](docs/documentation.md)

## Install

1. `git clone git@github.ncsu.edu:vtduong/BeaconBone.git`
2. `cd BeaconBone`
3. `./setup.sh`
4. `npm install`

## Run

### Publisher

`sudo coffee publisher.coffee http://brokerIP`

### MQTT Broker

`mosca -v | bunyan`

### HTTP Server

`sudo coffee server.coffee http://brokerIP`
