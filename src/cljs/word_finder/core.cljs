(ns word-finder.core
  (:require
   [sablono.core :as sab :include-macros true]
   [devcards.core :as dc]
   [re-frame.core :as rf]
   [re-frame.db :refer [app-db]]
   [reagent.core :as reagent]
   [word-finder.components.input :as components]
   [word-finder.actions.call-server :as server-side]
   [word-finder.schemas.word-schemas :as word-schemas]
   ;; re-frame handlers & subscribers are required in to the
   ;; namespace, they are evaluated.  You will get warnings on reload
   ;; because they get re-evaluated.
   [word-finder.handlers]
   [schema.core :as s])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest defcard-rg #_defcard-om]]))

;;
(enable-console-print!)

;; this devcard just has Markdown and no extra code running.  It's
;; great for documentation.
(defcard
  "#Getting started

To get started with the server-side examples you will need to run
`lein repl` instead of `lein figwheel`.

Once the REPL has started run:
```clojure
(dev)
;; => #namespace[dev]
(reset)
;; :reloading...

;; then run:
(start-fighweel!)

;; you can jump into the ClojureScript REPL by running:
(cljs-repl)
```")

(defcard first-card
  (sab/html [:div "This is your first devcard :{"]))

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

(defn- callback-action
  "This function makes the callback chain of functions that we want. It's a private function, note the `-` after defn"
  [server-action atom-to-reset]
  (fn [input] (server-action input (fn [server-data] (reset! atom-to-reset server-data)))))

(defcard server-side-testing
  "### Connecting to the server-side code to take the input and search for it.

Search using a simple regular expression like `a.le` to find some four-letter words."
  (fn [data _]
    (reagent/as-element [(components/input-text-field (callback-action
                                                       server-side/find-words
                                                       data))]))
  {:value nil}
  {:inspect-data true :history true})

(defcard server-side-anagram
  "### Connecting to the server-side code to take the input and search for it.

Searches for exact anagrams of a word; it won't use the regular expression as above.  Try `lemon`."
  (fn [data _]
    ;; this code is bad, callbacks in callbacks!
    (reagent/as-element [(components/input-text-field (callback-action server-side/find-anagrams data))]))

  {:value nil}
  {:inspect-data true :history true})

(defcard server-side-sub-anagrams
  "### Connecting to the server-side code to find sub-anagrams

It will find all of the words one can make with the input word."
  (fn [data _]
    (reagent/as-element [(components/input-text-field (callback-action server-side/find-sub-anagrams data))]))
  {:value nil}
  {:inspect-data true :history true})

(defcard testing-combined-component
  "### Combined component.

It does the server-side lookup as usual but it will render the list of
data that is returned."
  (fn [data _]
    (reagent/as-element [components/combined-search-component server-side/find-sub-anagrams]))
  ;; all of the state is internal to the component, so we don't get
  ;; the full benefit of history from devcards.
  )

(defcard testing-combined-with-subscription
  "### Combined component using the re-frame handlers and subscriptions."
  (reagent/as-element [components/combined-search-with-subscription server-side/find-words])

  ;; app-db is the special atom that re-frame uses to keep all of its
  ;; state changes.  Any change should go through the handlers, see handlers.cljs.
  app-db
  {:inspect-data true
   :history true})

(defcard testing-combined-with-subscription-second-search
  "### Combined component using the re-frame handlers and subscriptions.

This will do a different call to the server. And both components
should update. It shows both the strength and weakness of using
re-frame: centralised state.

Notice how the component above will change when this changes and _vice
versa_."
  (reagent/as-element [components/combined-search-with-subscription server-side/find-sub-anagrams])
  app-db
  {:inspect-data true
   :history true})

(defcard reset-state
  (sab/html [:input {:type "submit"
                     :on-click #(rf/dispatch [:clear-words])} "Click to reset."]))

(defn main []
  ;; conditionally start the app based on wether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (js/React.render (sab/html [:div "This is working"]) node)))

(main)

;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html
