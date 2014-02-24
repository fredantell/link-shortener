(ns link-shortener.core
  (:use (compojure handler
                   [core :only (GET POST defroutes)]))
  (:require
   [link-shortener.layout :as layout]
   [hiccup.form :as form]
   [ring.util.response :as response]
   [ring.adapter.jetty :as jetty]))

(defonce counter (atom 10000))

(defonce urls (atom {}))

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

(count {:a 1 :b 2 :c 3})

;; (for [x (keys @urls)]
;;   [x (@urls x)])

(defn show-links []
  [:ul.links
   (for [x (keys @urls)]
     [:li
      [:a {:href x} (str x " --> " (@urls x))]])])

(defn homepage [request]
  (layout/common
   (form/form-to
    [:post "/shorten"]
    (form/text-field "url")
    (form/submit-button "Shorten Link"))
   (show-links)))

(defn redirect [id]
  (response/redirect (@urls id)))

(defroutes app*
  (GET "/" request (homepage request))
  (GET "/:id" [id] (redirect id))
  #_(POST "/shorten" request (with-out-str (print request)))
  (POST "/shorten" request (let [id (shorten (get-in request [:params :url]))]
                              (response/redirect "/"))))
@urls
(def app (compojure.handler/site app*))

;; (defn home [& [name message error]]
;;   (layout/common
;;    [:h1 "Guestbook"]
;;    [:p (str "Welcome to my guestbook"
;;             (if (session/get :user)
;;               (str ", " (session/get :user))
;;               (str ".")))]
;;    [:p error]
;;    (show-guests)
;;    [:hr]
;;    (form-to [:post "/"]
;;     [:p "Name:"]
;;     (text-field "name" name)
;;     [:p "Message:"]
;;     (text-area {:rows 10 :cols 40} "message" message)
;;     [:br]
;;     (submit-button "comment"))))


;; (jetty/run-jetty #'app {:port 8080 :join? false})
