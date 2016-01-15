(ns word-finder.handlers.service-handlers
  (:require [io.pedestal.interceptor.helpers :refer [handler]]
            [pedestal.swagger.doc :as sw.doc]
            [schema.core :as s]
            [word-finder.actions.word-search :as act]
            [word-finder.schemas.word-schemas :as word-schemas]))

(def status
  (sw.doc/annotate
   {:summary     "Service status"
    :description "Describes the state of the service"}
   (handler
    ::status
    (fn [request]
      {:status 200
       :body {:ok true}}))))

(def find-words
  (sw.doc/annotate
   {:summary "Find matching words"
    :description "Find words that match the input
    regular-expression. Any non-alphabet characters are converted to '.'"
    :parameters {:query {:find word-schemas/valid-input-regex}}}
   (handler
    ::find-words
    (fn [request]
      {:status 200
       :body   {:found (act/matching-words (:word-db request) (-> request :params :find))}}))))

(def find-anagrams
  (sw.doc/annotate
   {:summary "Find exact anagrams"
    :description "Find the words that are exact anagrams of the input
    word."
    :parameters {:query {:find word-schemas/valid-input-string}}}
   (handler
    ::find-anagrams
    (fn [request]
      {:status 200
       :body {:found (act/exact-anagrams (:word-db request) (-> request :params :find))}}))))

(def find-sub-anagrams
  (sw.doc/annotate
   {:summary "Find words that can be made from the input word."
    :description "Find the words that can be made from the input word."
    :parameters {:query {:find word-schemas/valid-input-string}}}
   (handler
    ::find-sub-anagrams
    (fn [request]
      {:status 200
       :body {:found (act/sub-anagrams (:word-db request) (-> request :params :find))}}))))
