(ns dev
  (:require [clojure.test]))

;;FIXME: add test directory.
(defn run-all-tests []
  (clojure.test/run-all-tests #"word-finder.*test$"))
