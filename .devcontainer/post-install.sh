#!/bin/bash
set -ex

##
## Create some aliases
##
echo 'alias ll="ls -alF"' >> $HOME/.bashrc
echo 'alias la="ls -A"' >> $HOME/.bashrc
echo 'alias l="ls -CF"' >> $HOME/.bashrc
echo 'alias python="python3"' >> $HOME/.bashrc

# Convenience workspace directory for later use
WORKSPACE_DIR=$(pwd)

##
## Print Versions, for references
##

# Change some Poetry settings to better deal with working in a container
java --version

node --version
npm --version

python3 --version

aws --version
cdk --version

docker --version 
docker-compose --version 

echo "Done!"