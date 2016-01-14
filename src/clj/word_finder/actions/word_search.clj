(ns word-finder.actions.word-search)

(defn matching-words [word-db string-input]
  (let [re (re-pattern string-input)]
    (filter (partial re-matches re) word-db)))
