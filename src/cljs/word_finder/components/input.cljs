(ns word-finder.components.input
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf :refer [dispatch]]))

(defn input-text-field
  [dispatch-fn]
  (let [val (reagent/atom "")]
    (fn input-text-field-render []
      [:input {:type        "text"
               :value       @val
               :on-change   #(->> %
                                  .-target
                                  .-value
                                  (reset! val))
               :on-key-down #(case (.-which %)
                               13 (dispatch-fn @val)
                               nil)}])))

(defn show-return-values
  []
  (fn [data]
    (let [data-snapshot @data]
      (if-not (empty? data-snapshot)
        [:ul
         (doall
          (map-indexed
           (fn [idx d]
             [:li {:key (str "word." idx) :id (str "word-" idx)} d])
           data-snapshot))]))))
