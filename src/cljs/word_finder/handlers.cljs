(ns word-finder.handlers
  "This namespace contains all of the re-frame handlers."
  (:require [re-frame.core :refer [register-handler register-sub debug trim-v]]
            [reagent.ratom :refer-macros [reaction]]))

;; We will dispatch this message with the server return data.
;; Something may subscribe to this.

(register-handler
 :server/found-words
 ;; we register some middleware (we don't have to do
 ;; this, it's just nice.
 ;;
 ;; trim-v just takes the first keyword off the
 ;; message, we know it will be our message type.
 [debug trim-v debug]
 (fn server-find [db [value]]
   (assoc db :found-words value)))

;; a simple handler to reset the data.
(register-handler
 :clear-words
 [debug trim-v debug]
 (fn [db []]
   (assoc db :found-words [])))

;; We register a subscription here. It doesn't need to use the same
;; key. We could take in many data.

(register-sub :found-words
              (fn [db _]
                (reaction (:found-words @db))))
