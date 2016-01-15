(ns ^:figwheel-load word-finder.schemas.word-schemas
  (:require [schema.core :as s]))

;; This will be loaded by both Clojure and Clojurescript.

(def valid-input-regex
  (s/named
   (s/both
    s/Str
    (s/pred (partial re-matches #"(?i)[a-z\.]+")))
   "Only allows [a-z] & . as characters, must be non-blank."))

(def valid-input-string
  (s/named
   (s/both
    s/Str
    (s/pred (partial re-matches #"(?i)[a-z]+")))
   "Only allows [a-z] as characters, must be non-blank."))
