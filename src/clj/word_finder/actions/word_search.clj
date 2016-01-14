(ns word-finder.actions.word-search
  (:require [clojure.set :as set]))

(defn word->anagram
  "Sort the incoming word "
  [s]
  (frequencies s))

(defn subset-anagram?
  "True iff keys1 subset of keys2 and all vals1 <= vals2."
  [freq1 freq2]
  (and (set/subset? (set (keys freq1)) (set (keys freq2)))
       (every? true? (map (fn [k]
                            (<= (get freq1 k 0)
                                (get freq2 k 0)))
                          (keys freq1)))))

(defn matching-words
  "Find words that match the given input regex."
  [word-db string-input]
  (let [re (re-pattern string-input)]
    (filter (partial re-matches re) word-db)))

(defn exact-anagrams
  "For the given character frequency, filter the words to find exact
  anagrams."
  [word-db string-input]
  (let [char-frequency (word->anagram string-input)
        size           (count (keys char-frequency))]
    (->> word-db
         ;; check string length first
         (filter (fn [word]
                   (= (count word) size)))
         (filter (fn [word]
                   (= (word->anagram word)
                      char-frequency))))))

(defn sub-anagrams
  "For the given character frequency, filter the words to find all
  possible words."
  [word-db string-input]
  (let [char-frequency (word->anagram string-input)
        size           (count string-input)]
    (->> word-db
         (filter (fn [word]
                   (<= (count word) size)))
         (filter (fn [word]
                   (subset-anagram? (word->anagram word) char-frequency))))))
