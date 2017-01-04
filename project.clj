(defproject pandemic "0.1.0-SNAPSHOT"
  :description "Pandemic board game implementation"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha10"]
                 [org.clojure/test.check "0.9.0"]
                 [proto-repl "0.1.2"]
                 [clojure-lanterna "0.9.4"]]
  :main ^:skip-aot pandemic.main
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:source-paths ["dev" "src" "test"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]]}})
