(defproject word-finder "0.1.0-SNAPSHOT"
  :description "A Clojure & Clojurescript application to find words in a dictionary."
  :url "https://github.com/davidjameshumphreys/clojure-word-finder"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [jarohen/nomad "0.7.2"
                  :exclusions [org.clojure/tools.reader]]
                 [frankiesardo/pedestal-swagger "0.4.4"]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.jetty "0.4.1"]

                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.13"]
                 [org.slf4j/jcl-over-slf4j "1.7.13"]
                 [org.slf4j/log4j-over-slf4j "1.7.13"]
                 [org.clojure/tools.logging "0.3.1"]

                 [org.clojure/clojurescript "1.7.48" :scope "provided"
                  :exclusions [org.clojure/tools.reader]]
                 [devcards "0.2.1"
                  :exclusions [org.clojure/tools.reader
                               org.clojure/clojurescript]]
                 [sablono "0.4.0"]
                 [cljs-ajax "0.5.2"
                  :exclusions [org.clojure/clojurescript
                               com.fasterxml.jackson.core/jackson-core]]
                 [reagent "0.6.0-alpha"
                  :exclusions [org.clojure/clojurescript]]
                 [re-frame "0.7.0-alpha"
                  :exclusions [org.clojure/clojurescript]]
                 [org.omcljs/om "0.9.0"]]
  :plugins [[lein-cljsbuild "1.1.2"
             :exclusions [org.clojure/clojure]]
            ;; this version of Lein figwheel will work as a plugin, 0.5.0-3 doesn't work for me.
            [lein-figwheel "0.3.9"
             :exclusions [ring/ring-core
                          org.clojure/clojure
                          org.codehaus.plexus/plexus-utils
                          org.clojure/tools.reader]]]
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]
  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj"]
  :profiles {:dev {:source-paths ["dev"]
                   :jvm-opts ["-Dnomad.env=dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11" :scope "test"]
                                  [com.cemerick/piggieback "0.2.1"
                                   :exclusions [org.clojure/tools.reader]]
                                  ;; different version of figwheel for working within the REPL!!!
                                  [figwheel-sidecar "0.3.9"
                                   :exclusions [org.clojure/clojure]]]
                   :repl-options {:init-ns          user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  :cljsbuild {
              :builds [{:id "devcards"
                        :source-paths ["src/cljs"]
                        :figwheel { :devcards true }
                        :compiler {:main                 "word-finder.core"
                                   :asset-path           "js/compiled/devcards_out"
                                   :output-to            "resources/public/js/compiled/word_finder_devcards.js"
                                   :output-dir           "resources/public/js/compiled/devcards_out"
                                   :source-map-timestamp true }}
                       {:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel true
                        :compiler {:main                 "word-finder.core"
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
