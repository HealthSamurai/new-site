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
   :education "Обучение"
   :projects "Проекты"
   :services "Услуги"
   :contacts "Контакты"})

(def MENU
  [{:href "/products" :text (:products strings)}
   {:href "/projects" :text (:projects strings)}
   {:href "/contancts" :text (:contacts strings)}])

(def SERVICES
  [{:href "/services" :text "Запуск и обучение"}
   {:href "/services" :text "Запуск и разработка"}
   {:href "/services" :text "Коммерческая поддержка"}])

(def PRODUCTS
  [{:href "/products/fhirbase" :text "Fhirbase"}
   {:href "/products/aidbox" :text "Aidbox"}
   {:href "/products/hl7mapper" :text "HL7 Mapper"}
   {:href "/products/formstamp" :text "FormStamp"}
   {:href "/products/foodtaster" :text "FoodTaster"}])

(def EDUCATION
  [{:href "/education" :text "Введение в HL7 FHIR"}
   {:href "/education" :text "HL7 FHIR для продвинутых"}])

(def CONTACTS
  [{:href "/" :text "USA: +1 (818) 731-12-79"}
   {:href "/" :text "Russia: +7 (812) 919-00-25"}
   {:href "mailto:hello@health-samurai.io" :text "mailto:hello@health-samurai.io"}])

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
     [:span.nav-title {:position "fixed"
                       :top 0
                       :width "80px"
                       :height "68px"
                       :background-color (s-var :color :main)
                       :text-align "center"}
      [:i {:padding-top "15px"
           :text-align "center"
           :color "white"
           :text-decoration "none"
           :font-size "32px"
           :margin-left "auto"
           :margin-right "auto"}]]
     [:ul {:display "inline-block"
           :margin-bottom 0
           :float "right"}]
     [:li {:margin-right "20px"
           :margin-top "8px"}
      [:a {;;:color "#a23835" ;; TODO: uncomment me, remove next white color
           :color "white"
           :padding "10px 20px"
           :border "1px solid transparent"
           :background-color "transparent"}
       [:&:hover {:color (s-var :color :main)
                  :text-decoration "none"
                  :border "1px solid #a23835"}]]
      [:&:last-child {:margin-right 0}]
      [:&:hover {:color (s-var :color :main)}
       [:a {:color (s-var :color :main)}]]
      ]
     ])
   [:div.container
    [:span.nav-title
     [:a {:href="#/"}
      [:i.hs-icon.icon-samurai] #_(:title strings)]]
    [:ul.list-inline
     (for [x MENU]
       [:li [:a {:href (:href x)} (:text x)]])]]])

(defn footer []
  [:div#footer
   (style
    [:div#footer {:padding-top "80px"
                  :padding-bottom "80px"
                  :background-color "rgba(46,48,58,0.96)"
                  :color "white"}
     [:a {:color "#ddd"}]])
   [:div.container.footer
    [:div.row
     [:div.col-md-3
      [:h4 (:services strings)]
      [:ul.list-unstyled
       (for [x SERVICES]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (:products strings)]
      [:ul.list-unstyled
       (for [x PRODUCTS]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (:education strings)]
      [:ul.list-unstyled
       (for [x EDUCATION]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     [:div.col-md-3
      [:h4 (:contacts strings)]
      [:ul.list-unstyled
       (for [x CONTACTS]
         [:li
          [:a {:href (:href x)} (:text x)]])]]
     ]]])

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
    [:link {:rel "stylesheet"
            :href "//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css"
            :type "text/css"}]
    (style
     [:body {:padding "0"
             :margin "0"
             :font-family "'Exo 2', sans-serif"
             :font-size "18px"
             :font-weight "300"
             :color "#333356"
             :letter-spacing "1px"}])
    (style
     ["@font-face" {:font-family "fontello"
                    :src
                    (str "url('./fonts/fontello.eot) format('embedded-opentype), "
                         "url('./fonts/fontello.woff) format('woff), "
                         "url('./fonts/fontello.ttf) format('truetype'), "
                         "url('./fonts/fontello.svg) format('svg')")}])
    (style
     [:.hs-icon {:font-family "fontello"
                 :font-style "normal"
                 :font-weight "normal"
                 :speak "none"
                 :display "inline-block"
                 :text-decoration "inherit"
                 :margin-right "0.2em"
                 :text-align "center"
                 :font-variant "normal"
                 :text-transform "none"
                 :line-height "1em"
                 :margin-left "0.2em"
                 "-webkit-font-smoothing" "antialiased"
                 "-moz-osx-font-smoothing" "grayscale"}])
    (style
     [:.icon-samurai
      [:&:before {:content "'\\e805'"}]])
    (style
     [:.col-md-4 {:padding-left 0
                  :padding-right 0}])

    #_(style
       )
    (style
     [:.sub-title {:padding-top "160px"
                   :padding-bottom "100px"
                   :border-bottom "1px solid #ddd"
                   :background-color "#a23835"
                   :color "white"}
      [:span.sub-moto {:font-size "41px"
                       :line-height "1.3em"
                       :display "block"}]
      [:span.sub-desc {:font-size "18px"
                       :padding-top "24px"
                       :max-width "600px"
                       :margin-left "auto"
                       :margin-right "auto"
                       :line-height "1.5em"
                       :display "block"}]])
    [:link {:rel "stylesheet"
            :href "https://fonts.googleapis.com/css?family=Exo+2:400,100,100italic,200,200italic,300,300italic,400italic,500,900italic,500italic,600,600italic,700,700italic,800,800italic,900"
            :type "text/css"}]
    ]

   [:body
    [:div cnt]
    (footer)]])

(defn product [data]
  [:div.product.row.list-item
   (style
    [:.product  {:padding-top "80px"
                 :padding-bottom "80px"}
     [:.custom-label {:font-weight "300"
                      :margin-right "4px"
                      :margin-bottom "4px"
                      :padding "10px"
                      :display "inline-block"
                      :border "1px solid #ddd"
                      :color "#888"
                      :background-color "#fafafa"}]
     [:i.add_links {:font-size "36px"
                    :padding-left "20px"
                    :vertical-align "middle"
                    :color "#888"}
      [:&:hover {:color (s-var :color :main)}]]
     [:a.btn.gotosite {:border "1px solid #a23835"
                       :border-radius "none !important"
                       :color (s-var :color :main)}
      [:&:hover {:background-color (s-var :color :main)
                 :color "white"}]]
     [:a.item-product {:margin-right "10px"
                       :border "1px solid transparent"
                       :text-decoration "none"
                       :color "#333356"
                       :display "block"}
      [:span.image-product {:position "relative"
                            :overflow "hidden"
                            :font-size "144px"
                            :text-align "center"
                            :widht "100%"
                            :height "200px"
                            :border "1px solid #ddd"
                            :color "#888"
                            :display "inline-block"
                            :background-size "400px"
                            :background-position "center"}]]
     [:h1 {:text-align "center" :color "white"}]])
   [:div.col-md-3
    [:i.hs-icon.logo.icon-fhirbase]
    [:br]
    (for [label (:labels data)]
      [:span
       [:br]
       [:span.label.custom-label label]])]
   [:div.col-md-8
    [:h3 (str (:title data) " ")
     (when (:open-source data) [:small "Open Source"])]
    [:span.product-slogan (:slogan data)
     [:br]]
    [:br]
    [:p (:description data)]
    [:br]
    [:strong "Особенности Fhirbase"]
    [:ul
     (for [feature (:features data)]
       [:li feature])]
    [:br]
    (when (:links data)
      (for [link (:links data)]
        (case (:type link)
          :direct [:a.btn.btn-default.gotosite {:href (:link link) :target "_blank"} "Сайт продукта"]
          :github [:a {:href (:link link) :target "_blank"}
                   [:i.fa.fa-github.add_links]]
          :google-group [:a {:href (:link link) :target "_blank"}
                         [:i.fa.fa-comments-o.add_links]]
          "")))]])

(defn products [req]
  [:div
   (navigation :inverse)

   (style
    [:#products
     [:a.item-product {:margin-right "10px"
                       :border "1px solid transparent"
                       :text-decoration "none"
                       :color "#333356"
                       :display "block"}
      [:span.image-product {:position "relative"
                            :overflow "hidden"
                            :font-size "144px"
                            :text-align "center"
                            :widht "100%"
                            :height "200px"
                            :border "1px solid #ddd"
                            :color "#888"
                            :display "inline-block"
                            :background-size "400px"
                            :background-position "center"}]]
     [:h1 {:text-align "center" :color "white"}]])

   [:div#products
    [:div.container-fluid.sub-title.text-center
     [:span.sub-moto "Наши продукты"]
     [:span.sub-desc "Мы разрабатываем технологичные продукты на основе HL7 FHIR."]]
    [:div.container
     (product {:labels ["PostgreSQL" "plv8" "HL7 FHIR"]
               :open-source true
               :title "Fhirbase"
               :slogan "Реляционное хранилище медицинских данных на основе стандарта HL7 FHIR."
               :description "Fhirbase - это реляционное хранилище с встроенной моделью данных, соответствующей международному стандарту HL7 FHIR. Fhirbase подходит для построения медицинских информационных системы разных классов: электронные карты (EHR), интеграционные ”шины”, репозитории медицинских данных (реестры), госпитальные системы (HIS), порталы для пациентов и телемедицины."
               :features ["Fhirbase построен на PostgreSQL с использованием языка plv8"
                          "Fhirbase реализует модель данных соответсвующую HL7 FHIR"
                          "Fhirbase имеет простой API для обращения к данным (like REST)"]
               :links [{:type :direct
                        :link "http://fhirbase.github.io"}
                       {:type :github
                        :link "https://github.com/fhirbase"}
                       {:type :google-group
                        :link "https://groups.google.com/forum/#!forum/fhirbase"}]})
     [:hr]
     (product {:labels ["Amazon AWS" "Fhirbase" "Clojure" "AngularJS"]
               :title "Aidbox"
               :slogan "Реляционное хранилище медицинских данных на основе стандарта HL7 FHIR."
               :description "Aidbox - это платформа \"как сервис\" (PaaS) для разработки медицинских решений на основе HL7 FHIR. Aidbox подходит для построения мобильных и web-решений оперирующих медицинскими данными: электронные карты (EHR), интеграционные ”шины”, репозитории медицинских данных (реестры), госпитальные системы (HIS), порталы для пациентов и телемедицины."
               :features ["Aidbox использует Fhirbase для хранения данных"
                          "Aidbox реализует REST API на основе стандарта HL7 FHIR"
                          "Aidbox решает вопросы Security с использование OAuth"
                          "Aidbox предоставляет хостинг для базы и несложных single page applications"
                          "Aidbox имеет SDK и вы можете интегрироваться с решениями на ваших технологиях (Java, .Net etc...)"]
               :links [{:type :direct
                        :link "https://aidbox.io/landing.html#/"}]})
     ]
    [:div [:h1 (:products strings)]]
    #_[:div.projects [:h4 "Projects"]]]])

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
