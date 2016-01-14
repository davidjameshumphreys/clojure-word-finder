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
