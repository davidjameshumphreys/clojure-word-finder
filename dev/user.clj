(ns user)

;; It is a bit safer to start your Clojure REPL in this fashion just
;; in case there is some problem with your code, you can still load a
;; REPL.

(defn dev []
  (require 'dev)
  (in-ns 'dev))
