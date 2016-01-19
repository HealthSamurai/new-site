(ns site.layout
  (:require [site.data :refer [data]]
            [hiccup.page :refer [include-css]]
            [garden.stylesheet :refer [at-font-face]]
            [site.font :as font]
            [site.routes :refer [url asset-path]]
            [site.navigation :refer [navigation footer]]
            [site.styles :refer [style]]))

(defn google-analytic []
  [:script {:type "text/javascript"}
   "(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)
    },i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-72369222-1', 'auto');
    ga('send', 'pageview');"])

(defn layout [cnt]
  [:html
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge"}]
    [:title "Health Samurai"]
    [:link {:rel "shortcut icon" :href "/imgs/health-samurai-icon.ico" :type "image/x-icon"}]
    [:meta {:name "desription" :content ""}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    [:title (data :title)]

    (include-css
     (asset-path "font-awesome/css/font-awesome.min.css")
     (asset-path "bootstrap.min.css")
     "https://fonts.googleapis.com/css?family=Exo+2:400,100,100italic,200,200italic,300,300italic,400italic,500,900italic,500italic,600,600italic,700,700italic,800,800italic,900")

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

    [:script {:type "text/javascript" :src (asset-path "jquery.min.js")}]
    [:script {:type "text/javascript" :src (asset-path "jquery.drawsvg.min.js")}]
    [:script {:type "text/javascript"} "$(function(){$('#main-movie').drawsvg({duration: 5000,stagger: 700,reverse: false}).drawsvg('animate');})"]
    (google-analytic)
    #_[:script {:type "text/javascript" :src (asset-path "instant.js")}]]])















