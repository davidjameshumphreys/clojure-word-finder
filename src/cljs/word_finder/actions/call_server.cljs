(ns ^:figwheel-load word-finder.actions.call-server
  (:require [ajax.core :refer [GET]]))

(def server "http://localhost:8080/api/words/")

(defn find-words
  "Call the server-side find-words, returning JSON"
  [input-string callback]
  (GET (str server "find")
      {:params        {:find input-string}
       :format        :json
       :handler       callback
       :error-handler #(println "ERROR" %)}))

(defn find-anagrams
  "Call the server-side find-anangrams, returning JSON"
  [input-string callback]
  (GET (str server "anagrams")
      {:params        {:find input-string}
       :format        :json
       :handler       callback
       :error-handler #(println "ERROR!" %)}))

(defn find-sub-anagrams
  "Call the server-side find-sub-anagrams, returning JSON"
  [input-string callback]
  (GET (str server "sub-anagrams")
      {:params        {:find input-string}
       :format        :json
       :handler       callback
       :error-handler #(println "ERROR!" %)}))
