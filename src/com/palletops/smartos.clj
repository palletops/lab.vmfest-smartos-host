(ns com.palletops.smartos
  (:use vmfest.manager
        [clojure.tools.logging :as log])
  (:import [org.virtualbox_4_2 NetworkAdapterType]))

(defn wait-sec [n]
  (Thread/sleep (* 1000 n)))

(def boot-sequence
  [500
   :enter ;; first grub option
   30000
   "Y" :enter
   200;; first dialogue
   "dhcp" :enter
   200
   "c0t1d0" :enter
   200
   "vmfest" :enter
   200
   "vmfest" :enter
   200
   "y" :enter
   200
   "n" :enter
   200
   "y" :enter])

(defn smartos-instance [server name disk-mb  port smartos-iso]
  (let [disk-location "/tmp/" ;;(str "/tmp/")
        cpu-count 2
        memory 2048
        node-spec
        {:cpu-count cpu-count
         :network [{:attachment-type :nat
                    :adapter-type :i82545em
                    :nat-rules
                    [{:name "ssh", :protocol :tcp,
                       :host-ip "", :host-port port,
                       :guest-ip "", :guest-port 22}]}]
         :storage
         [{:devices [{:device-type :dvd :location smartos-iso}
                     {:device-type :hard-disk
                      :location disk-location
                      :create? true
                      :size disk-mb
                      :attachment-type :normal}]
           :name "SATA interface"
           :bus :sata}]
         :memory-size memory}
        vm (instance* server name {} node-spec)]
    (start vm)
    (log/warn "**** VM started")
    (wait-sec 3)
    (log/warn "**** Going to send the keyboard stuff")
    (send-keyboard vm boot-sequence)
    ;; return the VM!
    vm))

(comment
  (use 'vmfest.manager)
  (use 'com.palletops.smartos)
  ;; download the latest SmartOS ISO from here:
  ;; https://download.joyent.com/pub/iso/latest.iso and store it
  ;; somewhere, e.g. /Volumes/DATA/ISOS/smartOS-20130329.iso

  ;; create a SmartOS host. Wait for a couple of minutes until it
  ;; reboots. Do not type anything in the meantime as the keystrokes
  ;; are scripted for your convenience...
  (def smarty
    (smartos-instance (server)
                      "smartos-test-1" ;; vm name
                      16384 ;; 16GB Disk
                      4444 ;; ssh port
                      "/Volumes/DATA/ISOS/smartOS-20130329.iso"))

  ;; SSH into your spanking new SmartOS host with:
  ;; $ ssh root@localhost:4444
  ;; password is 'vmfest'

  ;; NOTE: The Zones disk will be stored in /tmp/ for now...

  ;; once you're done, destroy everything, as if it had never
  ;; happened.
  (nuke smarty) )
