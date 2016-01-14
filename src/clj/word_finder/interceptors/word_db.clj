(ns word-finder.interceptors.word-db
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [io.pedestal.interceptor.helpers :refer [on-request]]))

(def words (io/resource "words.txt"))

(defn- get-words []
  (with-open [r (io/reader words)]
    (->> r
         line-seq
         (filter (fn [w] (>= (count w) 4)))
         (map str/lower-case)
         doall)))

(defonce ^{:private true} word-list (get-words))
(defn attach-word-db
  "Add the word-db into the request."
  []
  (on-request
   ::attach-word-db
   (fn [request]
     (assoc request :word-db word-list))))
