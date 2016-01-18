(defproject word-finder "0.1.0-SNAPSHOT"
  :description "A Clojure & Clojurescript application to find words in a dictionary."
  :url "https://github.com/davidjameshumphreys/clojure-word-finder"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [jarohen/nomad "0.7.2"
                  :exclusions [org.clojure/tools.reader]]
                 [frankiesardo/pedestal-swagger "0.4.4"
                  :exclusions [com.fasterxml.jackson.core/jackson-core]]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.jetty "0.4.1"]

                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.13"]
                 [org.slf4j/jcl-over-slf4j "1.7.13"]
                 [org.slf4j/log4j-over-slf4j "1.7.13"]
                 [org.clojure/tools.logging "0.3.1"]
                 [org.clojure/tools.reader "1.0.0-alpha2"]
                 [org.clojure/clojurescript "1.7.228"]

                 [devcards "0.2.1"
                  :exclusions [org.clojure/tools.reader
                               cljsjs/react-dom
                               cljsjs/react
                               org.clojure/clojurescript]]
                 [sablono "0.4.0"]
                 [cljs-ajax "0.5.2"
                  :exclusions [org.clojure/clojurescript
                               com.fasterxml.jackson.core/jackson-core]]
                 [re-frame "0.6.0"
                  :exclusions [cljsjs/react-dom
                               org.clojure/clojurescript]]
                 [cljsjs/react-with-addons "0.14.3-0"]
                 [cljsjs/react-dom "0.14.3-0"
                  :exclusions [cljsjs/react]]
                 [cljsjs/react-dom-server "0.14.3-0"]
                 [secretary "1.2.3"
                  :exclusions [com.cemerick/clojurescript.test]]
                 #_[org.omcljs/om "0.9.0"]]
  :plugins [[lein-cljsbuild "1.1.2"
             :exclusions [org.clojure/clojure]]
            [lein-figwheel "0.5.0-3"
             :exclusions [ring/ring-core
                          org.clojure/clojure
                          org.codehaus.plexus/plexus-utils
                          org.clojure/tools.reader]]]
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]
  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj"]
  :min-lein-version "2.5.3"
  :profiles {:dev {:source-paths ["dev"]
                   :jvm-opts ["-Dnomad.env=dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11" :scope "test"]
                                  [com.cemerick/piggieback "0.2.1"
                                   :exclusions [org.clojure/tools.reader]]
                                  [figwheel-sidecar "0.5.0-3"
                                   :exclusions [org.clojure/clojure
                                                org.clojure/clojurescript]]]
                   :repl-options {:init-ns          user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  :cljsbuild {
              :builds [{:id           "devcards"
                        :source-paths ["src/cljs" "src/clj"]
                        :figwheel     { :devcards true }
                        :compiler     {:main                 "word-finder.devcards-core"
                                       :source-paths         ["src/cljs" "src/clj"]
                                       :asset-path           "js/compiled/devcards_out"
                                       :output-to            "resources/public/js/compiled/word_finder_devcards.js"
                                       :output-dir           "resources/public/js/compiled/devcards_out"
                                       :source-map-timestamp true }}
                       {:id "dev"
                        :source-paths ["src/cljs" "src/clj"]
                        :figwheel true
                        :compiler {:main                 "word-finder.core"
                                   :source-paths         ["src/cljs" "src/clj"]
                                   :asset-path           "js/compiled/out"
                                   :output-to            "resources/public/js/compiled/word_finder.js"
                                   :output-dir           "resources/public/js/compiled/out"
                                   :source-map-timestamp true }}
                       {:id "prod"
                        :compiler {:main          "word-finder.core"
                                   :asset-path    "js/compiled/out"
                                   :output-to     "resources/public/js/compiled/word_finder.js"
                                   :optimizations :advanced}}]}
  :figwheel { :css-dirs ["resources/public/css"] })
