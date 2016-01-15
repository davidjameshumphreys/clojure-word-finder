(ns dev
  (:require [clojure.test]
            [clojure.tools.namespace.repl :as repl]
            [figwheel-sidecar.repl-api :as fig :refer [start-figwheel! cljs-repl stop-figwheel!]]
            [io.pedestal.http :as bootstrap]
            [word-finder
             [service :as service]
             [server :as server]]))

;; Start the server-side code by running `reset` You can also start
;; the figwheel repl by running `start-figwheel!`, it can be stopped
;; with `stop-figwheel!`

;;FIXME: add test directory.
(defn run-all-tests []
  (clojure.test/run-all-tests #"word-finder.*test$"))

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
