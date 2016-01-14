(ns word-finder.handlers.service-handlers
  (:require [io.pedestal.interceptor.helpers :refer [handler]]
            [pedestal.swagger.doc :as sw.doc]))

(def status
  (sw.doc/annotate
   {:summary     "Service status"
    :description "Describes the state of the service"}
   (handler
    ::status
    (fn [request]
      {:status 200
       :body {:ok true}}))))
