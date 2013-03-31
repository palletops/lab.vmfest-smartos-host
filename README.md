# vmfest-smartos-host

A Clojure library designed to ... well, create SmartOS hosts on
VirtualBox.

## Usage

So far only tested on OSX, and it can work on other hosts too, but
latest Ubuntu and Debian will have to wait until the VirtualBox guys
fix a nasty bug with their java/xpcom library (there is a workaround,
ping me if you're interested)

Note: this uses the latest version of
[vmfest](https://github.com/tbatchelli/vmfest) in the `develop`
branch.

Steps:

1. Download the latest SmartOS ISO from
[here](https://download.joyent.com/pub/iso/latest.iso) and store it
somewhere, e.g. /Volumes/DATA/ISOS/smartOS-20130329.iso

2. create a SmartOS host. Wait for a couple of minutes until it
  reboots. Do not type anything in the meantime as the keystrokes are
  scripted for your convenience...
```clojure
  (use 'vmfest.manager)
  (use 'com.palletops.smartos) 
  (def smarty (smartos-instance (server)
                    "smartos-test-1" ;; vm name 
                    16384 ;; 16GB Disk 
                    4444 ;; ssh port
                    "/Volumes/DATA/ISOS/smartOS-20130329.iso"))
```

3. SSH into your spanking new SmartOS host with:
```shell
 $ ssh root@localhost:4444
```
 password is 'vmfest'

4. once you're done, destroy everything, as if it had never happened.
```clojure
  (nuke smarty)
```
__NOTE:__ The Zones disk will be stored in /tmp/ for now...


## License

Copyright © 2013 Antoni Batchelli

Distributed under the Eclipse Public License, the same as Clojure.
