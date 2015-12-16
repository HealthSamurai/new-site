(ns site.layout
  (:require [site.data :refer [data]]
            [hiccup.page :refer [include-css]]
            [garden.stylesheet :refer [at-font-face]]
            [site.font :as font]
            [site.navigation :refer [navigation footer]]
            [site.styles :refer [style]]))

(defn layout [cnt]
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
    [:title "Health Samurai"]
    [:meta {:name "desription" :content ""}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title (data :title)]

    (include-css
       "/bootstrap.min.css"
       "https://fonts.googleapis.com/css?family=Exo+2:400,100,100italic,200,200italic,300,300italic,400italic,500,900italic,500italic,600,600italic,700,700italic,800,800italic,900"
       "/font-awesome/css/font-awesome.min.css")

    (style (at-font-face font/font-face))

    (style
     [:body {:padding "0"
             :margin "0"
             :font-family "'Exo 2', sans-serif"
             :font-size "18px"
             :font-weight "300"
             :color "#333356"
             :letter-spacing "1px"}
      font/garden-font
      [:.col-md-4 {:padding-left 0 :padding-right 0}]])]

   [:body
    [:div#main [:div#wrap cnt]]
    (footer)
    [:script {:type "text/javascript" :src "/jquery.min.js"}]
    [:script {:type "text/javascript" :src "/instant.js"}]]])















