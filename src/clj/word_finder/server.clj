(ns word-finder.server
  (:require [io.pedestal.http :as bootstrap]
            [word-finder.configuration :refer [configuration]]
            [word-finder.service :as service])
  (:gen-class))

(defonce instance nil)

(defn create-server
  "Standalone dev/prod mode."
  ([] (create-server {}))
  ([{:keys [pedestal-opts]}]
   (alter-var-root #'instance
                   (constantly (bootstrap/create-server
                                (-> (merge service/service
                                           {::bootstrap/port (get-in (configuration) [:http :port])}
                                           pedestal-opts)
                                    (bootstrap/default-interceptors)))))))

(defn -main [& args]
  (create-server)
  (bootstrap/start instance))
