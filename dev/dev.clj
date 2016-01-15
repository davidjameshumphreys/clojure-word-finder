(ns dev
  (:require [clojure.test]
            [clojure.tools.namespace.repl :as repl]
            [figwheel-sidecar.repl-api :as fig :refer [start-figwheel! cljs-repl ;;stop-figwheel!
                                                       ]]
            [io.pedestal.http :as bootstrap]
            [word-finder
             [service :as service]
             [server :as server]]))

;;FIXME: add test directory.
(defn run-all-tests []
  (clojure.test/run-all-tests #"word-finder.*test$"))

(def figwheel-config
  {:figwheel-options { :css-dirs ["resources/public/css"] }
   :build-ids        ["devcards"]
   :all-builds
   [{:id "devcards"
     :figwheel { :devcards true }
     :compiler {:main                 "word-finder.core"
                :source-paths         ["src/cljs/"]
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
                :optimizations :advanced}}]})

(defn start [& [opts]]
  (let [new-opts (merge
                  (-> service/service ;; start with production configuration
                      (merge  {:env :dev
                               ;; do not block thread that starts web server
                               ::bootstrap/join? false
                               ;; reload routes on every request
                               ::bootstrap/routes (deref #'service/routes)
                               ;; all origins are allowed in dev mode
                               ::bootstrap/allowed-origins (constantly true)})
                      (bootstrap/default-interceptors)
                      (bootstrap/dev-interceptors)) opts)]

    (server/create-server {:pedestal-opts new-opts})
    (bootstrap/start server/instance)))

(defn stop []
  (when server/instance
    (bootstrap/stop server/instance)))

(defn reset
  "Stop, refresh, then start again"
  []
  (stop)
  (repl/refresh :after 'dev/start))
