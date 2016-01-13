(ns dev
  (:require [clojure.test]
            [figwheel-sidecar.repl-api :as fig :refer [start-figwheel! cljs-repl]]))

;;FIXME: add test directory.
(defn run-all-tests []
  (clojure.test/run-all-tests #"word-finder.*test$"))
