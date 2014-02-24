(ns link-shortener.core
  (:use (compojure handler
                   [core :only (GET POST defroutes)]))
  (:require
   [link-shortener.layout :as layout]
   [ring.util.response :as response]
   [ring.adapter.jetty :as jetty]))

(def counter (atom 10000))

(def urls (atom {}))

(defn shorten [url]
  (let [id (swap! counter inc)
        id (Long/toString id 36)]
    (swap! urls assoc id url)))

@urls
(@urls "7pt")
@counter
(shorten "http://clojurebook.com")


(defn app [request]
  {:status 200
   :body (with-out-str (print request))})

(defn homepage [request]
  (layout/common
   [:p "hello"]))

(defn redirect [id]
  (response/redirect (@urls id)))

(defroutes app
  (GET "/" request (homepage request))
  (GET "/:id" [id] (redirect id)))

(jetty/run-jetty #'app {:port 8080 :join? false})
