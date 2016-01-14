(ns word-finder.configuration
  (:require [clojure.java.io :as io]
            [nomad :refer [defconfig]]))

(defconfig configuration
  (io/resource "config.edn"))
