(ns site.core
  (:require [clojure.string :as str]
            [hiccup.core :refer [html]]
            [garden.core :refer [css]]
            [garden.units :as u]
            [ring.middleware.resource :as rmr]
            [garden.color :as c]
            [garden.stylesheet :as ss]
            [org.httpkit.server :as srv]
            [route-map.core :as rt]))

(defn http [hic]
  {:body    (html hic)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :status 200})

(def strings
  {:title "Health Samurai"
   :products "Продукты"
   :projects "Projects"
   :services "Services"
   :contacts "Contacts"})

(def menu
  [{:href "/products" :text (:products strings)}
   {:href "/projects" :text (:projects strings)}
   {:href "/contancts" :text (:contacts strings)}])

(defn style [cnt]
  [:style {:type "text/css"} (css cnt)])


(def style-vars
  {:color {:main "#a23835"
           :normal "#444"}})

(defn s-var [& ks]
  (get-in style-vars ks))

(defn navigation [color]
  [:div#navigation

   (style
    [:#navigation
     {:background {:color (if (= :inverse color)  (s-var :color :main) "transparent")}
      ;; :line-height "40px"
      :padding "15px 0 20px"
      :color (if (= :inverse color) "white" (s-var :color :main))}
     [:.brand {:display "inline-block"}
      [:a {:color "inherit" :display "inline-block" :padding 5}]]
     [:.menu {:display "inline-block" :float "right"}
      [:.menu-item {:display "inline-block"}
       [:a {:color "inherit" :display "inline-block" :padding 5}]]]])

   [:div.container
    [:span.nav-title
     [:a {:href="#/"}
      [:i.hs-icon.icon-samurai] (:title strings)]]
    [:ul.list-inline
     (for [x menu]
       [:li [:a {:href (:href x)} (:text x)]])]]])

#_(defn navigation [color]
  [:div#navigation

   (style
    [:#navigation
     {:background {:color (if (= :inverse color)  (s-var :color :main) "white")}
      :line-height "40px"
      :padding "0 30px"
      :color (if (= :inverse color) "white" (s-var :color :main))}
     [:.brand {:display "inline-block"}
      [:a {:color "inherit" :display "inline-block" :padding 5}]]
     [:.menu {:display "inline-block" :float "right"}
      [:.menu-item {:display "inline-block"}
       [:a {:color "inherit" :display "inline-block" :padding 5}]]]])

   [:div.brand
    [:a {:href "/"} [:i.hs-icon.icon-samurai] "Brand"]]
   [:div.menu
    (for [x menu]
      [:div.menu-item [:a {:href (:href x)} (:text x)]])]])

(defn footer []
  [:div#footer
   [:style {:type "text/css"}]
   [:h4 (:services strings)]])

(defn layout [cnt]
  [:html
   [:head
    
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible"
            :content "IE=edge"}]
    [:title "Health Samurai"]
    [:meta {:name "desription"
            :content ""}]
    [:meta {:name "viewport"
            :content "width=device-width, initial-scale=1.0"}]
    [:title (:title strings)]
    [:link {:rel "stylesheet"
            :href "//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" 
            :type "text/css"}]
    (style
     [:body {:padding "0"
             :margin "0"
             :font-family "'Exo 2', sans-serif"
             :font-size "18px"
             :font-weight "300"
             :color "#333356"
             :letter-spacing "1px"}])
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css?family=Exo+2:400,100,100italic,200,200italic,300,300italic,400italic,500,900italic,500italic,600,600italic,700,700italic,800,800italic,900"
            :type "text/css"}]
    ]

   [:body
    [:div cnt]
    (footer)]])

(defn products [req]
  [:div
   (navigation :inverse)

   (style
    [:#products {:padding "100px 50px"
                 :background {:color (s-var :color :main)}}
     [:h1 {:text-align "center" :color "white"}]])

   [:div#products
    [:h1 "Helllllooooo11111111111111111"]
    [:div [:h1 (:products strings)]]
    [:div.projects [:h4 "Projects"]]]])

(defn index [req]
  [:div (navigation :main)])

(defn projects [req]
  [:div])

(defn page [{{id :id} :params}]
  (http [:h3 "Page" (str id)]))

(def routes
  {:GET #'index
   "about"    {:GET  #'index}
   "products" {:GET #'products}
   "projects" {:GET #'projects}
   "pages"    {:id #(range 10)
               [:id] {:GET #'page}}})

(defn dispatch [{uri :uri meth :request-method :as req}]
  (if-let [mtch (rt/match [meth uri] routes)]
    (http (layout ((:match mtch) (update req :params merge (:params mtch)))))
    {:body "ups" :method 404}))

(def app (-> #'dispatch
             (rmr/wrap-resource "public")))

(defn start []
  (def stop (srv/run-server #'app {:port 8081})))

(comment
  (stop)
  (start))

(defn generate [{path :path params :params :as req} routes]
  (println path)
  (for [[k v] routes]
    (cond
      (= :GET k) (let [uri (str/join "/" path)]
                   (println "-> " uri ":")
                   (println (dispatch {:uri uri :request-method k})))
      (vector? k) (let [k (first k)
                        gen (get routes k)]
                    (doseq [pv (gen)]
                      (generate {:path (conj path pv)
                                 :params (assoc params k pv)}
                                v)))
      (keyword? k) "nop"
      :else (generate {:path (conj path k) :params params} v))))

(generate [] routes)
