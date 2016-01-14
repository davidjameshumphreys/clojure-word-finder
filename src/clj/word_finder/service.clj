(ns word-finder.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [pedestal.swagger.core :as swagger]
            [ring.util.response :refer [response not-found created resource-response content-type status redirect]]
            [schema.core :as s]
            [word-finder.handlers.service-handlers :as handlers]
            [word-finder.interceptors.word-db :refer [attach-word-db]]))

(s/with-fn-validation
  (swagger/defroutes api-routes
    {:info {:title        "Word finder"
            :description  "API to search for words in a dictionary."
            :externalDocs {:description "Find out more"
                           :url         "https://github.com/davidjameshumphreys/clojure-word-finder"}
            :version      "2.0"}}
    [[["/api" ^:interceptors [(swagger/body-params)
                              bootstrap/json-body
                              (swagger/coerce-request)
                              (swagger/validate-response)
                              (attach-word-db)]
       ["/status"
        {:get handlers/status}]
       ["/words" ^:interceptors []
        ["/find" {:get handlers/find-words}]
        ["/anagrams" {:get handlers/find-anagrams}]
        ["/sub-anagrams" {:get handlers/find-sub-anagrams}]]
       ["/swagger.json" {:get [(swagger/swagger-json)]}]
       ["/*resource" {:get [(swagger/swagger-ui)]}]]]]))

(defroutes app-routes
  [[ ;;["/cards.html" {:get cards-page}]
    ;; ["/*route" {:get home}]
    ]])

(def routes
  (concat api-routes app-routes))

(def service
  {:env :prod
   ::bootstrap/routes routes
   ::bootstrap/router :linear-search
   ::bootstrap/resource-path "/public"
   ::bootstrap/type :jetty})
