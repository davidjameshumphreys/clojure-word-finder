(ns ^:figwheel-load word-finder.actions.call-server
  (:require [ajax.core :refer [GET]]))

(def server "http://localhost:8080/api/words/")

;;FIXME: the string interpolation can be easily broken.
(defn find-words [input-string callback]
  (GET (str server "find" "?find=" input-string)
      {:handler       callback
       :error-handler #(println "ERROR" %)}))

(defn find-anagrams [input-string callback]
  (GET (str server "anagrams" "?find=" input-string)
      {:handler       callback
       :error-handler #(println "ERROR!" %)}))

(defn find-sub-anagrams [input-string callback]
  (GET (str server "sub-anagrams" "?find=" input-string)
      {:handler callback}))
