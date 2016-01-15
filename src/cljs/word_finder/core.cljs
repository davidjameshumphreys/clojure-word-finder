(ns word-finder.core
  (:require
   #_[om.core :as om :include-macros true]
   #_[om.dom :as dom]
   [sablono.core :as sab :include-macros true]
   [devcards.core :as dc]
   [re-frame.core :as rf]
   [reagent.core :as reagent]
   [word-finder.components.input :as components]
   [word-finder.actions.call-server :as server-side]
   [word-finder.schemas.word-schemas :as word-schemas]
   [schema.core :as s])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest defcard-rg #_defcard-om]]))

;;
(enable-console-print!)

(defcard first-card
  (sab/html [:div "This is your first devcard :P"]))

(defcard input-testing
  "Testing our basic input component."

  (fn [data _]
    (reagent/as-element [(components/input-text-field #(do (println "Current value:" %)
                                                           (println "Schema validated with (nil is good):" (s/check word-schemas/valid-input-regex %))
                                                           (reset! data {:value %})))]))

  {:value nil}
  {:inspect-data true :history true})

(def input-for-output (reagent/atom ["veda",
                                     "vega",
                                     "vela",
                                     "vera",
                                     "veta"]))

(defcard output-testing
  "Showing how the list of output will look on-screen."
  (fn [data _]
    (reagent/as-element [components/show-return-values data]))
  input-for-output)

(defcard server-side-testing
  "### Connecting to the server-side code to take the input and search for it."
  (fn [data _]
    ;; this code is bad, callbacks in callbacks!
    (reagent/as-element [(components/input-text-field (fn [input] (server-side/find-words input (fn [server-data] (reset! data server-data)))))]))
  {:value nil}
  {:inspect-data true :history true})

(defcard server-side-anagram
  "### Connecting to the server-side code to take the input and search for it."
  (fn [data _]
    ;; this code is bad, callbacks in callbacks!
    (reagent/as-element [(components/input-text-field (fn [input] (server-side/find-anagrams input (fn [server-data] (reset! data server-data)))))]))
  {:value nil}
  {:inspect-data true :history true})

#_(defn widget [data owner]
    (reify
      om/IRender
      (render [this]
        (dom/div nil (:text data)
                 (dom/div nil (om/get-shared owner :counter))
                 ;;(dom/a nil {:on-click #(om/transact! owner :count inc)})
                 ))))

#_(defcard-om om-widget
    "Trying to hook in an Om widget..."
    widget
    {:text "Counter:"}
    {:shared {:counter 0}}
    {:inspect-data true :history true})

(defn main []
  ;; conditionally start the app based on wether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (js/React.render (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html
