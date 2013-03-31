(defproject vmfest-smartos-host "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [vmfest/vmfest "0.3.0-SNAPSHOT"]
                 [org.clojars.tbatchelli/vboxjxpcom "4.2.4"]]
   :jvm-opts ["-Dvbox.home=/Applications/VirtualBox.app/Contents/MacOS"])
