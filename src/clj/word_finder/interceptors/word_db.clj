(ns word-finder.interceptors.word-db
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [io.pedestal.interceptor.helpers :refer [before]]))

(def words (io/resource "words.txt"))

(defn- get-words []
  (with-open [r (io/reader words)]
    (->> r
         line-seq
         (filter (fn [w] (>= (count w) 4)))
         (map str/lower-case)
         doall)))

(defn attach-word-db []
  (let [w (get-words)]
    (before
     ::attach-word-db
     (fn [request]
       (assoc request :word-db w)))))
