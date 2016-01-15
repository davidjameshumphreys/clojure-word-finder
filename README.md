# Clojure Word-finder #

A Clojure & ClojureScript application to search for words.

The backend API uses Pedestal-Swagger to serve routes.  A lot of code has been influenced by Oliver Hine's [SPA-skeleton](https://github.com/oliyh/spa-skeleton) example.

The ClojureScript front-end UI needs to be implemented.

This project is a work-in-progress; it is some basic infrastructure intended for the January 2016 [Hack the Tower](http://hackthetower.co.uk/) event.

# Getting started #

## Clojure backend ##

To start the backend code, launch a Clojure REPL `lein repl` (or your preferred method).

Run:
```clojure
(dev)
;; => #namespace[dev]
(reset)
;; :reloading...
```
Any changes to the Clojure code can be hot reloaded by running `(reset)` once again.

You may also run figwheel from the same REPL by running:
```clojure
(start-figwheel!)
;; to jump into the ClojureScript REPL run:
(cljs-repl)
```

## ClojureScript frontend ##
Start the ClojureScript frontend by running `lein figwheel`.  If the Clojure REPL is running you may visit either `localhost:8080` or `localhost:3449` to view the page.

The default ClojureScript build is `devcards` there is a separate `cards.html` that will execute this code.  See more at the [Devcards homepage](https://github.com/bhauman/devcards).

There are some basic Re-frame components already written.
