# Clojure Word-finder #

A Clojure & ClojureScript application to search for words.

The backend API uses Pedestal-Swagger to serve routes.

The ClojureScript front-end UI needs to be implemented.

This project is a work-in-progress; it is some basic infrastructure intended for the January 2016 Hack the Tower event.

# Getting started #

## Clojure backend ##

To start the backend code, launch a Clojure REPL `lein repl` (or your preferred method).

Run:
```clojure
(dev)
;; => #namespace[dev
(reset)
;; :reloading...
```

Any changes to the Clojure code can be hot reloaded by running `(reset)` once again.

## ClojureScript frontend ##
Start the ClojureScript frontend by running `lein figwheel`.  If the Clojure REPL is running you may visit either `localhost:8080` or `localhost:3449` to view the page.
