(ns ^:figwheel-load word-finder.schemas.word-schemas
  (:require [clojure.string :as str]
            [schema.coerce :as coerce]
            [schema.core :as s]))

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

(defn strip-bad-fn
  [s]
  (some-> s
          str/lower-case
          (str/replace #"[^a-z.]" "")))

(def strip-bad-characters
  "Rather complicated code that will ensure that all keywords in the
  map will be safe strings.

  Coercer returns a function to call."
  (coerce/coercer
   {s/Keyword s/Str}
   {s/Str (coerce/safe strip-bad-fn)}))
