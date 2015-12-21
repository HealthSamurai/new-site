(ns site.server
  (:require [clojure.string :as str]
            [hiccup.page :as hp]
            [ring.middleware.resource :as rmr]
            [ring.middleware.defaults :as rmd]
            [hiccup.core :refer [html]]
            [org.httpkit.server :as srv]
            [site.index :refer [index]]
            [site.data :as data]
            [site.products :refer [products]]
            [site.projects :refer [projects project]]
            [site.services :refer [services service]]
            [site.contacts :refer [contacts]]
            [site.trainings :refer [trainings training]]
            [site.layout :refer [layout]]
            [route-map.core :as rt]))

(defn http [hic]
  {:body    (html hic)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :status  200})

(def *routes
  {:GET #'index
   "about"    {:GET  #'index}
   "products" {:GET #'products}
   "contacts" {:GET #'contacts}
   "services" {:GET #'services}
   "trainings" {:GET #'trainings
                [:id] {:GET #'training}}
   "projects" {:GET #'projects
               [:id] {:GET #'project}}})
(def routes
  (merge *routes {[:lang] *routes}))

(defn dispatch [{params :params uri :uri meth :request-method :as req}]
  (if-let [mtch (rt/match [meth uri] routes)]
    (let [req (update req :params merge (:params mtch))
          lang (get-in mtch [:params :lang])]
      (if (contains? #{"ru" "en"} lang)
        (data/with-lang (keyword lang)
          (http (layout ((:match mtch) req))))
        (http (layout ((:match mtch) req)))))
    {:body "ups" :method 404}))

(def app (-> #'dispatch
             (rmr/wrap-resource "public")
             (rmd/wrap-defaults rmd/site-defaults)))

(defn start []
  (def stop (srv/run-server #'app {:port 8080})))

(comment
  (stop)
  (start))

