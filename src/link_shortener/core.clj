(ns link-shortener.core
  (:use (compojure handler
                   [core :only (GET POST defroutes)]))
  (:require [ring.adapter.jetty :as jetty]))

(defn app [request]
  {:status 200
   :body (with-out-str (print request))})

(defn homepage [request]
  (with-out-str (print request)))

(defn redirect [id]
  id)

(defroutes app
  (GET "/" request (homepage request))
  (GET "/:id" [id] (redirect id)))

(run)
