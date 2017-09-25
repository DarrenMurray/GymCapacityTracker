# GymCapacityTracker
Gets the members currently in a pure gym Location and tracks it over time. 

## Setup

### Set Gym credentials
Add your login credentials to application.properties 

### Install PhantomJS
Run the following commands to install phantomJS

Prerequisites

`sudo apt-get install build-essential chrpath libssl-dev libxft-dev libfreetype6-dev libfreetype6 libfontconfig1-dev libfontconfig1 -y`

Download PhantomJS

`sudo wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2`

Extract the downloaded archive file to desired system location

`sudo tar xvjf phantomjs-2.1.1-linux-x86_64.tar.bz2 -C /usr/local/share/`

Create a symlink of PhantomJS binary file to systems bin dirctory:

`sudo ln -s /usr/local/share/phantomjs-2.1.1-linux-x86_64/bin/phantomjs /usr/local/bin/`

Verify PhantomJS Version
`phantomjs --version`
