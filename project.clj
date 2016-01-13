(defproject word-finder "0.1.0-SNAPSHOT"
  :description "A Clojure & Clojurescript application to find words in a dictionary."
  :url "https://github.com/davidjameshumphreys"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [devcards "0.2.1"]
                 [sablono "0.4.0"]
                 #_[org.omcljs/om "0.9.0"]
                 #_[reagent "0.5.1"]]
  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-1"]]
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"]
  :source-paths ["src/clj" "src/cljs"]
  :test-paths ["test/clj"]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[org.clojure/tools.namespace "0.2.11"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.0-1"]]
                   :repl-options {:init-ns          user
                                  :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}
             }
  :cljsbuild {
              :builds [{:id "devcards"
                        :figwheel { :devcards true }
                        :compiler {:main                 "word-finder.core"
                                   :asset-path           "js/compiled/devcards_out"
                                   :output-to            "resources/public/js/compiled/word_finder_devcards.js"
                                   :output-dir           "resources/public/js/compiled/devcards_out"
                                   :source-map-timestamp true }}
                       {:id "dev"
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
