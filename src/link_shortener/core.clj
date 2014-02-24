(ns link-shortener.core
  (:use (compojure handler
                   [core :only (GET POST defroutes)]))
  (:require [ring.adapter.jetty :as jetty]))

(defn app [request]
  {:status 200
   :body (with-out-str (print request))})

(defn run []
  (defonce server (jetty/run-jetty #'app {:port 8080 :join? false})))

(defroutes
  (GET "/" request (homepage request))
  (GET "/:id" [id] (redirect id)))

(run)
