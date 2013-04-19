(defproject cascalog-action "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :jvm-opts ["-XX:MaxPermSize=128M"
             "-XX:+UseConcMarkSweepGC"
             "-Xms1024M" "-Xmx1048M" "-server"]

  :dependencies [
                 [org.clojure/clojure "1.5.1"]

                 ;; For cascasding json scheme
                 [cheshire "5.0.1"]
                 [cascalog "1.10.1"]
                 ]
  :aot [adgoji.cascading.scheme]
  :profiles { :provided {:dependencies [[org.apache.hadoop/hadoop-core "0.20.2-dev"]]}})
